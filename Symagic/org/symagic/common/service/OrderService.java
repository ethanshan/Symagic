package org.symagic.common.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.symagic.common.db.bean.BeanOrder;
import org.symagic.common.db.bean.BeanOrderDetail;
import org.symagic.common.db.func.DaoBook;
import org.symagic.common.db.func.DaoOrder;
import org.symagic.common.db.func.OrderRequire;
import org.symagic.common.utilty.presentation.bean.DistrictBean;
import org.symagic.common.utilty.presentation.bean.ItemTinyBean;
import org.symagic.common.utilty.presentation.bean.OrderBean;

public class OrderService {

	public static class Address {
		public DistrictBean level1District = null;
		public DistrictBean level2District = null;
		public DistrictBean level3District = null;
		public String districtDetail = null;
	}

	public static class OrderListResult {
		public Integer totalPage;
		public List<OrderBean> orders;
	}

	private DaoOrder daoOrder;

	private DaoBook daoBook;

	public DaoBook getDaoBook() {
		return daoBook;
	}

	public void setDaoBook(DaoBook daoBook) {
		this.daoBook = daoBook;
	}

	public BeanOrder orderDetail(Integer orderID) {

		BeanOrder bean = daoOrder.getOrderDetail(orderID);

		return bean;
	}

	public OrderBean convertBeanOrderToOrderBean(BeanOrder bean) {
		OrderBean result = new OrderBean();

		result.setOrderID(Integer.toString(bean.getOrderId()));
		switch (Integer.parseInt(bean.getOrderState())) {
		case 0:
			result.setOrderStatus("已下单");
			break;
		case 1:
			result.setOrderStatus("已审核");
			break;
		case 2:
			result.setOrderStatus("交易成功");
			break;
		case 3:
			result.setOrderStatus("交易失败");
			break;

		default:
			result.setOrderStatus("无效状态");

		}
		result.setOrderTime(bean.getOrderDate());
		result.setReceiverName(bean.getReceiverName());
		result.setScore(Integer.toString(bean.getScore()));
		result.setTotalPrice(String.format("%.2f", bean.getTotalprice()));
		result.setUserName(bean.getUsername());
		return result;
	}

	/**
	 * 列出指定用户订单列表，当username为空时为列出所有订单，
	 * 
	 * @param username
	 *            用户名
	 * @param itemPerPage
	 *            每页几项
	 * @param page
	 *            想要第几页的数据
	 * @param start
	 *            起始时间
	 * @param end
	 *            终止时间
	 * @param OrderStatus
	 *            订单状态
	 * @return 用户订单列表和totalPage
	 */

	public OrderListResult orderList(String username, int itemPerPage,
			int page, Date start, Date end, Integer orderState) {
		List<OrderBean> orderList = new ArrayList<OrderBean>();
		OrderRequire require = new OrderRequire();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (start != null)
			require.setStartTime(format.format(start));
		if (end != null)
			require.setEndTime(format.format(end));
		if (orderState != null && orderState != 0)
			require.setOrderState(Integer.toString(orderState - 1));
		if(username != null)
			username = username.trim();
		require.setPage(page);
		require.setLines(itemPerPage);
		List<BeanOrder> orders = daoOrder.search(require, username);
		if (orders == null)
			return null;
		float rowNumber = daoOrder.getRowNumber(require, username);
		for (int i = 0; i < orders.size(); i++) {
			OrderBean orderBean = this.convertBeanOrderToOrderBean(orders
					.get(i));
			orderList.add(orderBean);
		}
		OrderListResult result = new OrderListResult();
		result.totalPage = (int) Math.ceil(rowNumber / itemPerPage);
		result.orders = orderList;

		return result;
	}

	public static Address deserializerAddress(String receiverAddress) {

		JSON json = JSONSerializer.toJSON(receiverAddress);
		if (!(json instanceof JSONObject))
			return null;
		JSONObject addressObject = (JSONObject) json;
		Address result = new Address();

		JSONObject level1 = addressObject.getJSONObject("level1");
		JSONObject level2 = addressObject.getJSONObject("level2");
		JSONObject level3 = addressObject.getJSONObject("level3");
		String detail = addressObject.getString("detail");

		if (detail != null) {
			result.districtDetail = detail;
		} else {
			return null;
		}

		if (!level1.isNullObject()) {
			result.level1District = new DistrictBean();
			result.level1District.setID(level1.getInt("id"));
			result.level1District.setName(level1.getString("name"));
		}

		if (!level2.isNullObject()) {
			result.level2District = new DistrictBean();
			result.level2District.setID(level2.getInt("id"));
			result.level2District.setName(level2.getString("name"));
		}

		if (!level3.isNullObject()) {
			result.level3District = new DistrictBean();
			result.level3District.setID(level3.getInt("id"));
			result.level3District.setName(level3.getString("name"));
		}

		return result;
	}

	public static String serializerAddress(Address address) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("detail", address.districtDetail);

		if (address.level1District != null) {
			JSONObject district = new JSONObject();
			district.put("id", address.level1District.getID());
			district.put("name", address.level1District.getName());
			jsonObject.put("level1", district);
		}

		if (address.level2District != null) {
			JSONObject district = new JSONObject();
			district.put("id", address.level2District.getID());
			district.put("name", address.level2District.getName());
			jsonObject.put("level2", district);
		}

		if (address.level3District != null) {
			JSONObject district = new JSONObject();
			district.put("id", address.level3District.getID());
			district.put("name", address.level3District.getName());
			jsonObject.put("level3", district);
		}

		return jsonObject.toString();

	}

	public List<BeanOrderDetail> getOrderDetail(List<ItemTinyBean> items) {
		List<BeanOrderDetail> orderDetails = new ArrayList<BeanOrderDetail>();
		for (int i = 0; i < items.size(); i++) {
			BeanOrderDetail detail = new BeanOrderDetail();
			ItemTinyBean item = items.get(i);
			detail.setAmount(item.getItemNumber());
			detail.setBookId(item.getItemID());
			// detail.set
		}
		return orderDetails;
	}

	public int orderNumber(String userName, Integer itemID) {
		int orderNum = 0;
		OrderRequire orderRequire = new OrderRequire();
		orderRequire.setOrderState("2");
		orderRequire.setLines(Integer.MAX_VALUE);
		orderRequire.setPage(1);
		List<BeanOrder> orders = daoOrder.search(orderRequire, userName);
	    if (orders != null) {
			for (int i = 0; i < orders.size(); i++) {
				BeanOrder order = daoOrder.getOrderDetail(orders.get(i).getOrderId());
				if (isOrderHasItem(itemID, orders.get(i).getList()))
					orderNum++;
			}
		}
		return orderNum;
	}
	public int getOrderNumber(String userName,Integer itemID){
		return daoOrder.getOrderNum(userName, itemID);
	}

	public DaoOrder getDaoOrder() {
		return daoOrder;
	}

	public void setDaoOrder(DaoOrder daoOrder) {
		this.daoOrder = daoOrder;
	}

	private boolean isOrderHasItem(Integer itemID, List<BeanOrderDetail> order) {
		boolean isHas = false;
		for (int i = 0; i < order.size(); i++) {
			if (order.get(i).getBookId() == itemID) {
				isHas = true;
				break;
			}
		}
		return isHas;
	}

}

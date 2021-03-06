package org.symagic.user.action.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.symagic.common.action.catalog.CatalogBase;
import org.symagic.common.db.bean.BeanBook;
import org.symagic.common.db.bean.BeanLevel;
import org.symagic.common.db.func.DaoBook;
import org.symagic.common.db.func.DaoCart;
import org.symagic.common.db.func.DaoDistrict;
import org.symagic.common.db.func.DaoLevel;
import org.symagic.common.db.func.DaoUser;
import org.symagic.common.service.AddressService;
import org.symagic.common.utilty.presentation.bean.AddressDetailBean;
import org.symagic.common.utilty.presentation.bean.DistrictBean;
import org.symagic.common.utilty.presentation.bean.ItemTinyBean;
import org.symagic.user.utilty.UserSessionUtilty;

import com.opensymphony.xwork2.ActionContext;

public class OrderEnterAction extends CatalogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4781084758132850598L;

	private List<AddressDetailBean> addressList;

	private String userName;

	private String price;

	private String payment;

	private String deliverWay;

	private boolean isValidate;

	private List<ItemTinyBean> buyItems;

	private AddressService addressService;

	private DaoBook daoBook;

	private List<ItemTinyBean> items;

	private DaoUser daoUser;

	private DaoLevel daoLevel;

	public DaoLevel getDaoLevel() {
		return daoLevel;
	}

	public void setDaoLevel(DaoLevel daoLevel) {
		this.daoLevel = daoLevel;
	}

	public DaoUser getDaoUser() {
		return daoUser;
	}

	public void setDaoUser(DaoUser daoUser) {
		this.daoUser = daoUser;
	}

	private List<DistrictBean> level1Districts;

	private Integer score;

	public List<AddressDetailBean> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<AddressDetailBean> addressList) {
		this.addressList = addressList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getDeliverWay() {
		return deliverWay;
	}

	public void setDeliverWay(String deliverWay) {
		this.deliverWay = deliverWay;
	}

	@Override
	public String execute() throws Exception {
		if (!isValidate)
			return ERROR;
		Float temp = 0.0f;
		score = daoUser.getScore(UserSessionUtilty.getUsername());
		BeanLevel level = null;
		if (score != null)
			level = daoLevel.judgeLevel(score);
		if (level == null) {
			return ERROR;
		}
		buyItems = new ArrayList<ItemTinyBean>();
		for (int i = 0; i < items.size(); i++) {
			Integer itemId = items.get(i).getItemID();
			if (itemId == null)
				continue;
			BeanBook beanBook = daoBook.getDetail(itemId);
			if (beanBook != null) {
				ItemTinyBean itemTinyBean = new ItemTinyBean();
				itemTinyBean.setItemID(beanBook.getBookId());
				itemTinyBean.setName(beanBook.getBookName());
				itemTinyBean.setItemNumber(items.get(i).getItemNumber());
				itemTinyBean.setPrice(String.format("%.2f",
						(beanBook.getMarketPrice() * beanBook.getDiscount())));
				itemTinyBean
						.setItemTotalPrice(String.format(
								"%.2f",
								(beanBook.getMarketPrice()
										* beanBook.getDiscount() * itemTinyBean
										.getItemNumber())));
				itemTinyBean
						.setScore((int) (level.getRate() * (beanBook
								.getMarketPrice() * beanBook.getDiscount() * itemTinyBean
								.getItemNumber())));
				temp += beanBook.getMarketPrice() * beanBook.getDiscount()
						* itemTinyBean.getItemNumber();
				buyItems.add(itemTinyBean);
			}
		}
		price = String.format("%.2f", temp);
		userName = UserSessionUtilty.getUsername();
		addressList = addressService.getAddressDetail(userName);
		level1Districts = addressService.getDistricts(0);
		payment = "货到付款";
		deliverWay = "快递";
		score = daoUser.getScore(UserSessionUtilty.getUsername());
		HashMap<Integer, Integer> orderHashMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < items.size(); i++) {
			orderHashMap.put(items.get(i).getItemID(), items.get(i)
					.getItemNumber());
		}
		UserSessionUtilty.setOrder(orderHashMap);
		return super.execute();
	}

	@Override
	public void validate() {
		String param = (String) ActionContext.getContext().getSession()
				.get("savedForm");
		if (param != null && items == null) {
			Map<Integer, ItemTinyBean> itemMap = new HashMap<Integer, ItemTinyBean>();
			items = new ArrayList<ItemTinyBean>();
			try {
				JSON json = JSONSerializer.toJSON(param);
				JSONObject object = (JSONObject) json;
				@SuppressWarnings("unchecked")
				Iterator<String> keys = object.keys();
				String lastKey = null;
				while (keys.hasNext()) {
					String key = keys.next();
					JSONArray array = object.getJSONArray(key);

					key = key.replaceAll("(.+)\\[(.+?)\\](.+)", "$1" + "$2"
							+ "$3");
					key = key.replace("items", "");
					String[] strs = key.split("\\.");

					if (strs.length == 2) {
						int index = Integer.parseInt(strs[0]);
						try {
							ItemTinyBean itemTinyBean = itemMap.get(index);
							if (itemTinyBean == null) {
								throw new Exception();
							}
							if (strs[1].equals("itemNumber")) {
								itemTinyBean.setItemNumber(Integer
										.parseInt(array.getString(0)));
							}
							if (strs[1].equals("itemID")) {
								itemTinyBean.setItemID(Integer.parseInt(array
										.getString(0)));
							}
							itemMap.put(index, itemTinyBean);
						} catch (Exception ex) {
							ItemTinyBean itemTinyBean = new ItemTinyBean();
							if (strs[1].equals("itemNumber")) {
								itemTinyBean.setItemNumber(Integer
										.parseInt(array.getString(0)));
							}
							if (strs[1].equals("itemID")) {
								itemTinyBean.setItemID(Integer.parseInt(array
										.getString(0)));
							}
							itemMap.put(index, itemTinyBean);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (itemMap != null) {
				Iterator<Map.Entry<Integer, ItemTinyBean>> iterator = itemMap
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<Integer, ItemTinyBean> entry = iterator.next();
					items.add(entry.getValue());
				}
			}
		}
		if (items == null) {
			isValidate = false;
			return;
		}
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getItemID() == null)
				items.remove(i);
		}
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) != null) {
				if (items.get(i).getItemID() == null) {
					items.remove(i);
				}
				if (items.get(i).getItemNumber() == null
						|| items.get(i).getItemNumber() <= 0) {
					isValidate = false;
					return;
				}
			} else {
				isValidate = false;
				return;
			}
		}
		isValidate = true;
		super.validate();
	}

	public List<ItemTinyBean> getBuyItems() {
		return buyItems;
	}

	public void setBuyItems(List<ItemTinyBean> buyItems) {
		this.buyItems = buyItems;
	}

	public DaoBook getDaoBook() {
		return daoBook;
	}

	public void setDaoBook(DaoBook daoBook) {
		this.daoBook = daoBook;
	}

	public List<ItemTinyBean> getItems() {
		return items;
	}

	public void setItems(List<ItemTinyBean> items) {
		this.items = items;
	}

	public AddressService getAddressService() {
		return addressService;
	}

	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	public List<DistrictBean> getLevel1Districts() {
		return level1Districts;
	}

	public void setLevel1Districts(List<DistrictBean> level1Districts) {
		this.level1Districts = level1Districts;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}

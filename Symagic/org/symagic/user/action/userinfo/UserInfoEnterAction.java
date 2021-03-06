package org.symagic.user.action.userinfo;

import java.util.List;

import org.symagic.common.action.catalog.CatalogBase;
import org.symagic.common.db.bean.BeanUser;
import org.symagic.common.db.func.DaoOrder;
import org.symagic.common.db.func.DaoUser;
import org.symagic.common.db.func.OrderRequire;
import org.symagic.common.service.OrderService;
import org.symagic.common.utilty.presentation.bean.OrderBean;
import org.symagic.user.utilty.UserSessionUtilty;

public class UserInfoEnterAction extends CatalogBase{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5124921196230967305L;

	private String userName;
	
	private String nickName;
	
	private Integer totalScore;
	
	private List<OrderBean> orderList;
	
	private DaoUser daoUser;
	
	private DaoOrder daoOrder;
	
	public DaoOrder getDaoOrder() {
		return daoOrder;
	}

	public void setDaoOrder(DaoOrder daoOrder) {
		this.daoOrder = daoOrder;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public List<OrderBean> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderBean> orderList) {
		this.orderList = orderList;
	}

	public DaoUser getDaoUser() {
		return daoUser;
	}

	public void setDaoUser(DaoUser daoUser) {
		this.daoUser = daoUser;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	private OrderService orderService;
	
	public String execute() throws Exception{
		userName = UserSessionUtilty.getUsername();
		nickName = UserSessionUtilty.getNickname();
		
		BeanUser user = daoUser.getUser(userName);
		if(user != null){
			totalScore = user.getScore();
		}
		OrderRequire orderRequire = new OrderRequire();
		orderRequire.setEndTime(null);
		orderRequire.setStartTime(null);
		orderRequire.setOrderState("0");
		orderList = orderService.orderList(userName, 10000, 1, null, null, 0).orders;
		for(int i = 0; i < orderList.size(); i ++){
			if(orderList.get(i).getOrderStatus().equals("已下单"))
				orderList.get(i).setEditable(true);
			else {
				orderList.get(i).setEditable(false);
			}
		}
		return super.execute();
	}
}

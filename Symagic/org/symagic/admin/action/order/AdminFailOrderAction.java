package org.symagic.admin.action.order;

import java.util.List;

import org.symagic.common.db.bean.BeanBook;
import org.symagic.common.db.bean.BeanOrder;
import org.symagic.common.db.bean.BeanOrderDetail;
import org.symagic.common.db.func.DaoBook;
import org.symagic.common.db.func.DaoOrder;
import org.symagic.common.service.MailService;

import com.opensymphony.xwork2.ActionSupport;

public class AdminFailOrderAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3790488288537580693L;
	private List<Integer> orderIDList;
	private Boolean changeResult;

	private DaoOrder daoOrder;
	private DaoBook daoBook;

	@Override
	public String execute() throws Exception {

		changeResult = false;

		if (orderIDList == null)
			return super.execute();

		// 检查状态是否符合
		for (int i = 0; i < orderIDList.size(); i++) {
			BeanOrder order = daoOrder.getOrderDetail(orderIDList.get(i));
			if (order != null && !order.getOrderState().equals("1")) {
				return super.execute();
			}
		}

		for (int i = 0; i < orderIDList.size(); i++) {
			BeanOrder order = daoOrder.getOrderDetail(orderIDList.get(i));
			if (order != null) {
				order.setOrderState("3");
				daoOrder.updateOrder(order);
				List<BeanOrderDetail> items = order.getList();
				for (BeanOrderDetail detail : items) {
					BeanBook bookDetail = daoBook.getDetail(detail.getBookId());
					if (bookDetail == null)
						return super.execute();
					bookDetail.setInventory(bookDetail.getInventory()
							+ detail.getAmount());
					if (!daoBook.modifyBook(bookDetail))
						return super.execute();
				}

				MailService.sendFailOrder(order);
			}

			changeResult = true;

		}

		return super.execute();
	}



	public Boolean getChangeResult() {
		return changeResult;
	}

	public void setChangeResult(Boolean changeResult) {
		this.changeResult = changeResult;
	}

	public DaoOrder getDaoOrder() {
		return daoOrder;
	}

	public void setDaoOrder(DaoOrder daoOrder) {
		this.daoOrder = daoOrder;
	}

	public DaoBook getDaoBook() {
		return daoBook;
	}

	public void setDaoBook(DaoBook daoBook) {
		this.daoBook = daoBook;
	}



	public List<Integer> getOrderIDList() {
		return orderIDList;
	}



	public void setOrderIDList(List<Integer> orderIDList) {
		this.orderIDList = orderIDList;
	}

}

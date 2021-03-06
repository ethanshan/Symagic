package org.symagic.user.action.index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import org.apache.struts2.ServletActionContext;
import org.symagic.common.action.catalog.CatalogBase;
import org.symagic.common.db.bean.BeanBook;
import org.symagic.common.db.func.DaoBook;
import org.symagic.common.service.ItemService;
import org.symagic.common.service.RecommendService;
import org.symagic.common.utilty.presentation.bean.ItemTinyBean;
import org.symagic.user.utilty.UserSessionUtilty;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class IndexAction extends CatalogBase{
/**
	 * 
	 */
private static final long serialVersionUID = 5685501467658534869L;

//配置项

private ItemService itemService;//访问商品项
private Integer recommendNumber;

//传出参数
private String nickname;//昵称
private List<ItemTinyBean> recommendItem;//浏览量的商品
private List<ItemTinyBean> newBook;//新书
private List<ItemTinyBean> hotBook;//热销书

@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		//增加购物车的数量
		UserSessionUtilty.addTotalNumber(0);
		nickname=UserSessionUtilty.getNickname();
		UserSessionUtilty.getLoginErrorTime();
		
		//推荐商品
		
		recommendItem=new ArrayList<ItemTinyBean>();
		itemService.getRecommendBook(recommendNumber, UserSessionUtilty.isLogin(), recommendItem);
		
	    hotBook=new ArrayList<ItemTinyBean>();
	    itemService.getHotBook(recommendNumber, hotBook);
	    
	    //新书
	   newBook=new ArrayList<ItemTinyBean>();
	   itemService.getNewBook(newBook);
	    return super.execute();
	}
	
	
	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	

	

	


	public ItemService getItemService() {
		return itemService;
	}


	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}


	

	


	public List<ItemTinyBean> getRecommendItem() {
		return recommendItem;
	}


	public void setRecommendItem(List<ItemTinyBean> recommendItem) {
		this.recommendItem = recommendItem;
	}


	public List<ItemTinyBean> getNewBook() {
		return newBook;
	}


	public void setNewBook(List<ItemTinyBean> newBook) {
		this.newBook = newBook;
	}


	public Integer getRecommendNumber() {
		return recommendNumber;
	}


	public void setRecommendNumber(Integer recommendNumber) {
		this.recommendNumber = recommendNumber;
	}


	public List<ItemTinyBean> getHotBook() {
		return hotBook;
	}


	public void setHotBook(List<ItemTinyBean> hotBook) {
		this.hotBook = hotBook;
	}
	
  
}

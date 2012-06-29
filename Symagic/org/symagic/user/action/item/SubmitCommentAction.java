package org.symagic.user.action.item;

import org.symagic.common.db.bean.BeanComment;
import org.symagic.common.service.ItemService;
import org.symagic.user.utilty.UserSessionUtilty;

import com.opensymphony.xwork2.ActionSupport;

public class SubmitCommentAction extends ActionSupport {
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5119384687733985464L;
	
	
	
	//传入参数
	private Integer itemID;//评论的商品
	private String content;//评论内容
	 private Integer rating;//评分
	 //配置项
	 private ItemService itemService;//访问商品项
	 private Boolean submitResult=false;//评论是否成功
	
@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
        BeanComment comment=new BeanComment();
		comment.setBookID(itemID);
		comment.setContent(content);
		comment.setRating(rating+"");//?string
		comment.setUsername(UserSessionUtilty.getUsername());
		submitResult=itemService.addItemComment(comment);
       return super.execute();
	}

public Integer getItemID() {
	return itemID;
}

public void setItemID(Integer itemID) {
	this.itemID = itemID;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}

public Integer getRating() {
	return rating;
}

public void setRating(Integer rating) {
	this.rating = rating;
}

public ItemService getItemService() {
	return itemService;
}

public void setItemService(ItemService itemService) {
	this.itemService = itemService;
}

public Boolean getSubmitResult() {
	return submitResult;
}

public void setSubmitResult(Boolean submitResult) {
	this.submitResult = submitResult;
}
 

 
 
}

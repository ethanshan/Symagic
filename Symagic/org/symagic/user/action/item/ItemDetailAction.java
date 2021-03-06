package org.symagic.user.action.item;

import java.util.ArrayList;
import java.util.List;

import org.symagic.common.action.catalog.CatalogBase;
import org.symagic.common.db.bean.BeanComment;
import org.symagic.common.service.ItemService;
import org.symagic.common.service.RecommendService;
import org.symagic.common.utilty.presentation.bean.CommentBean;
import org.symagic.common.utilty.presentation.bean.ItemDetailBean;
import org.symagic.common.utilty.presentation.bean.ItemTinyBean;
import org.symagic.user.utilty.UserSessionUtilty;

public class ItemDetailAction extends CatalogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9054557857853861076L;
	//传入
    private Integer itemID;// 商品id
	//配置项
	private Integer lines ;
	private String errorHeader;
	private String errorSpecification ;
	private ItemService itemService;// 访问服务层
	private Integer page=1; 
	private Integer recommendNumber;
	//传出
	private ItemDetailBean book;// 书籍详细信息
	private Integer totalPage;// 评论有多少页
	private List<CommentBean> commentList;// 评论列表
	private ArrayList<ItemTinyBean> recommendView;
	private ArrayList<ItemTinyBean> recommendBought;

	
	
 
    
	

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		//商品项为空返回error
		
		if(itemID==null)return "error";
		book = new ItemDetailBean();
		//无此商品
		if(!itemService.fillDetailBean(itemID, book))return "error";
		book.setBookDesc(book.getBookDesc().replaceAll("\n", "<br>"));
		book.setSize(book.getSize()+"开");
		int commentNumber = itemService.getCommentNumber(itemID);
		if(commentNumber==-1)return "error";
		totalPage = (commentNumber + lines - 1) / lines;
		//显示第一页的评论显示
		commentList = itemService.getComments(itemID,page, lines);
        //推荐
		recommendView = new ArrayList<ItemTinyBean>();
		recommendBought = new ArrayList<ItemTinyBean>();
		itemService.getBoughtFromItem(recommendNumber, itemID, recommendBought);
		itemService.getViewFromItem(recommendNumber, itemID, recommendView);
		itemService.viewForRecommend(itemID, book.getBookDesc());
		return super.execute();
	}
	

	public Integer getPage() {
		return page;
	}


	public void setPage(Integer page) {
		this.page = page;
	}


	public Integer getLines() {
		return lines;
	}

	public void setLines(Integer lines) {
		this.lines = lines;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getItemID() {
		return itemID;
	}

	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}

	public ItemDetailBean getBook() {
		return book;
	}

	public void setBook(ItemDetailBean book) {
		this.book = book;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	

	

	public List<CommentBean> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommentBean> commentList) {
		this.commentList = commentList;
	}

	public ArrayList<ItemTinyBean> getRecommendView() {
		return recommendView;
	}

	public void setRecommendView(ArrayList<ItemTinyBean> recommendView) {
		this.recommendView = recommendView;
	}

	public ArrayList<ItemTinyBean> getRecommendBought() {
		return recommendBought;
	}

	public void setRecommendBought(ArrayList<ItemTinyBean> recommendBought) {
		this.recommendBought = recommendBought;
	}
	
	

	public String getErrorHeader() {
		return errorHeader;
	}

	public void setErrorHeader(String errorHeader) {
		this.errorHeader = errorHeader;
	}

	public String getErrorSpecification() {
		return errorSpecification;
	}

	public void setErrorSpecification(String errorSpecification) {
		this.errorSpecification = errorSpecification;
	}

	public Integer getRecommendNumber() {
		return recommendNumber;
	}

	public void setRecommendNumber(Integer recommendNumber) {
		this.recommendNumber = recommendNumber;
	}

	

	
}

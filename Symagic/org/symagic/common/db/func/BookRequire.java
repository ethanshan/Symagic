package org.symagic.common.db.func;

import java.util.List;

/**
 * 封装所有书籍搜索条件的类
 * @author wanran
 *
 */
public class BookRequire {
	
	/**
	 * 作者
	 */
	private String author	= "%";
	
	// 
	private String itemName = "%";
	
	/**
	 * 出版社
	 */
	private String publisher = "%";
	
	/**
	 * 分类ID
	 */
	private List<Integer> catalogIDList = null;
	
	/**
	 * 出版年
	 */
	private String year	= null;
	
	/**
	 * 表示year域搜索是之前的订单还是当前的。(true表示搜索之前的)
	 */
	private Boolean before = false;
	
	/**
	 * 版本
	 */
	private Integer version = null;
	
	/**
	 * 页数上线
	 */
	private Integer upPage	= null;
	
	/**
	 * 页数下线
	 */
	private Integer lowPage	= null;

	/**
	 * 书籍装帧 
	 * 选择（"精装"或"平装"）
	 */
	private String binding	= "%";
	
	/**
	 * 书籍开本
	 */
	private String folio	= "%";
	
	
//	up_price;
	/**
	 * 价格上线
	 */
	private Float upPrice	= null;
	
	/**
	 * 价格下线
	 */
	private Float lowPrice	= null;
	
	/**
	 * 折扣(discount(0:0`0.1,1:0.1`0.3,2:0.3`0.5,3:0.5`1,4:>100））
	 */
	private Integer discount	= null;
	
	private Float upDiscount	= null;
	
	private Float lowDiscount	= null;
	
	/**
	 * 第几页
	 */
	private Integer page	= null;
	
	/**
	 * 每页显示
	 */
	private Integer lines	= null;
	
	

	 
	
	
	/**
	 * 书籍名
	 */
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getUpPage() {
		return upPage;
	}

	public void setUpPage(Integer upPage) {
		this.upPage = upPage;
	}

	public Integer getLowPage() {
		return lowPage;
	}

	public void setLowPage(Integer lowPage) {
		this.lowPage = lowPage;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Float getUpPrice() {
		return upPrice;
	}

	public void setUpPrice(Float upPrice) {
		this.upPrice = upPrice;
	}

	public Float getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Float lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
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



 

	public Boolean getBefore() {
		return before;
	}

	public void setBefore(Boolean before) {
		this.before = before;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public Float getUpDiscount() {
		return upDiscount;
	}


	public void setUpDiscount(Float upDiscount) {
		this.upDiscount = upDiscount;
	}


	public Float getLowDiscount() {
		return lowDiscount;
	}


	public void setLowDiscount(Float lowDiscount) {
		this.lowDiscount = lowDiscount;
	}

	public List<Integer> getCatalogIDList() {
		return catalogIDList;
	}

	public void setCatalogIDList(List<Integer> catalogIDList) {
		this.catalogIDList = catalogIDList;
	}

 





	
	
	
}

package org.symagic.admin.action.item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.symagic.common.action.catalog.CatalogBase;
import org.symagic.common.db.bean.BeanBookStatistics;
import org.symagic.common.db.func.BookStatisticsRequire;
import org.symagic.common.db.func.DaoBook;
import org.symagic.common.service.ItemService;
import org.symagic.common.utilty.presentation.bean.StatisticBean;
import org.symagic.common.utilty.presentation.bean.TimeBean;

public class AdminItemStatisticsAction extends CatalogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7238512637976603546L;

	private DaoBook daoBook;

	private TimeBean startTime;// :开始索引时间(year:年，month:月;day:日）;
	private TimeBean endTime;// :结束索引时间（year：年，month:月，day:日);
	private Integer catalogID;// :目录ID,
	private Integer limit;// (销售量下限）,
	private Integer page;// (请求的页面）

	private List<StatisticBean> items;

	private Integer totalPage;
	private Integer lines;
	
	private Integer searchStartYear;
	private Integer searchEndYear;
	private Integer searchYearRange;
	
	private String totalSalesRevenue;
	
	private ItemService itemService;

	@Override
	public String execute() throws Exception {

		if (page == null)
			page = 1;
		
		GregorianCalendar calendar = new GregorianCalendar();
		searchEndYear = calendar.get(Calendar.YEAR);
		searchStartYear = calendar.get(Calendar.YEAR) - searchYearRange;

		BookStatisticsRequire require = new BookStatisticsRequire();

		// 建立require
		if (catalogID != null && catalogID != 0)
			require.setCatalogidList(itemService.getCatalogList(catalogID));
		require.setLines(lines);
		if (limit != null){
			if( limit < 0 )
				limit = 0;
			require.setLowlimit(limit);
		}
		require.setPage(page);
		// 编码时间
		GregorianCalendar startCalendar = null;
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		if (startTime != null) {
			startCalendar = new GregorianCalendar(startTime.getYear(),
					startTime.getMonth() - 1, startTime.getDay());
			require.setStartTime(formater.format(startCalendar.getTime()));
		} 
		if (endTime != null) {
			GregorianCalendar endCalendar = new GregorianCalendar(
					endTime.getYear(), endTime.getMonth() - 1, endTime.getDay());

			if (startCalendar != null
					&& endCalendar.getTime().before(startCalendar.getTime())) {
				return ERROR;
			}

			require.setEndTime(formater.format(endCalendar.getTime()));
		} 
		List<BeanBookStatistics> statistics = daoBook
				.getBookStatistics(require);
		
		Float revenue = 0f;

		if (statistics != null) {

			items = new ArrayList<StatisticBean>();

			for (BeanBookStatistics statistic : statistics) {
				StatisticBean bean = new StatisticBean();
				bean.setItemID(statistic.getBookid());
				bean.setItemName(statistic.getBookname());
				bean.setSales(statistic.getTotalSaleAmount());
				bean.setTotalPrice(String.format("%.2f",statistic.getTotalSaleRevenue()));
				bean.setPrice(String.format("%.2f",statistic.getTotalSaleRevenue() / bean.getSales()));
				revenue += statistic.getTotalSaleRevenue();
				items.add(bean);
			}

			float rowNumber = daoBook.getStatisticsNum(require);

			if (rowNumber != -1) {
				totalPage = (int) Math.ceil(rowNumber / lines);
			}

		}
		
		totalSalesRevenue = String.format("%.2f",revenue);

		return super.execute();
	}

	public DaoBook getDaoBook() {
		return daoBook;
	}

	public void setDaoBook(DaoBook daoBook) {
		this.daoBook = daoBook;
	}

	public TimeBean getStartTime() {
		return startTime;
	}

	public void setStartTime(TimeBean startTime) {
		this.startTime = startTime;
	}

	public TimeBean getEndTime() {
		return endTime;
	}

	public void setEndTime(TimeBean endTime) {
		this.endTime = endTime;
	}

	public Integer getCatalogID() {
		return catalogID;
	}

	public void setCatalogID(Integer catalogID) {
		this.catalogID = catalogID;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public List<StatisticBean> getItems() {
		return items;
	}

	public void setItems(List<StatisticBean> items) {
		this.items = items;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getLines() {
		return lines;
	}

	public void setLines(Integer lines) {
		this.lines = lines;
	}

	public Integer getSearchStartYear() {
		return searchStartYear;
	}

	public void setSearchStartYear(Integer searchStartYear) {
		this.searchStartYear = searchStartYear;
	}

	public Integer getSearchEndYear() {
		return searchEndYear;
	}

	public void setSearchEndYear(Integer searchEndYear) {
		this.searchEndYear = searchEndYear;
	}

	public Integer getSearchYearRange() {
		return searchYearRange;
	}

	public void setSearchYearRange(Integer searchYearRange) {
		this.searchYearRange = searchYearRange;
	}


	public String getTotalSalesRevenue() {
		return totalSalesRevenue;
	}

	public void setTotalSalesRevenue(String totalSalesRevenue) {
		this.totalSalesRevenue = totalSalesRevenue;
	}
	
	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

}

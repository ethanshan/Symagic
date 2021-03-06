package org.symagic.user.action.cart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.symagic.common.db.bean.BeanBook;
import org.symagic.common.db.func.DaoBook;
import org.symagic.common.db.func.DaoCart;
import org.symagic.common.service.ItemService;
import org.symagic.common.utilty.presentation.bean.ItemTinyBean;
import org.symagic.user.utilty.UserSessionUtilty;

import com.opensymphony.xwork2.ActionSupport;

public class CartAddItemAction extends ActionSupport {
    
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -8688962449531199326L;
	//传入
   
   private List<ItemTinyBean>items ;
  

	//配置项
	private DaoCart daoCart;//对于会员来说，更新到数据库中
    private DaoBook daoBook;//
	//传出	
	private Boolean addResult=false;//添加结果
	private String resultInfo;//操作的结果信息
	//内部
	private Boolean validateResult=true;//对参数有效性的验证结果

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if(!validateResult){
			addResult=false;
			resultInfo="数据有误";
			return SUCCESS;
		}
		boolean login=UserSessionUtilty.isLogin();
		boolean result=true;
		addResult=true;
		StringBuilder builder=new StringBuilder();
		Iterator<ItemTinyBean> index=items.iterator();
		ItemTinyBean item;
	  while(index.hasNext()){
		  item=index.next();
		  //数据不符合规则，忽略掉,有非法请求
		  if(item==null||item.getItemID()==null||item.getItemNumber()==null){
			  addResult=false;
			  resultInfo="非法请求";
			  break;
		  }
		 
		  result=addOneToCart(item.getItemID(),item.getItemNumber(),login,builder);
		  if(!result){
			  addResult=false;
		  }
	  }
		  //添加到后台数据库失败
		  
	  if(addResult){
		   resultInfo="成功添加到购物车";
	   }
	  else{
		  resultInfo=builder.toString();
	  }
		 return SUCCESS;
		
	}
	private boolean addOneToCart(Integer itemID,Integer itemNumber,boolean login,StringBuilder info){
		//首先判断商品是否下架
		BeanBook book=daoBook.getDetail(itemID);
		if(book==null){
			info.append("商城没有编号为"+itemID+"的商品\n");
			return false;
		}
		if(book.getOffline().trim().equals("下架"))
		{
			info.append("书名为"+book.getBookName()+"已下架\n");
			return false;
		}
		//itemID不存在于数据库中或者是库存不足
				if(book==null||itemNumber<=0||itemNumber>999){
					info.append("请输入正确 的数量");   
					return false;
					 }
				
		//通过其数量判断是否有这商品
		 Integer  number=UserSessionUtilty.getCart().get(itemID);
		 
		int compare=(number==null?0:number);
		
		if(compare==0&&book.getInventory()<(itemNumber+compare)){
			info.append("添加商品数量不能超过库存");
			return false;
		}
		if(compare!=0&&book.getInventory()<(itemNumber+compare)){
			info.append("购物车已有此商品，以当前数量加到购物车后，数量超过库存,无法加入购物车");
			return false;
		}
		boolean addResult=true;
		 if(login){
		    	if(number==null)
		    		addResult=daoCart.addBook(UserSessionUtilty.getUsername(), itemID, itemNumber);
		    	else
		    		addResult=daoCart.modifyBook(UserSessionUtilty.getUsername(), itemID, itemNumber+number);
		    }
		 
		
		if(addResult){
		   addResult=UserSessionUtilty.addToCart(itemID, itemNumber);
		   info.append("书名为《"+book.getBookName()+"》成功添加到购物车\n");
		  }
		else{
			info.append("书名为《"+book.getBookName()+"》添加到购物车（数据库）中失败\n"+UserSessionUtilty.getUsername());
		}
		    return addResult;
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		if(items==null){
			validateResult=false;
		}
		
	}
	public DaoCart getDaoCart() {
		return daoCart;
	}
	public void setDaoCart(DaoCart daoCart) {
		this.daoCart = daoCart;
	}
	public DaoBook getDaoBook() {
		return daoBook;
	}
	public void setDaoBook(DaoBook daoBook) {
		this.daoBook = daoBook;
	}
	
	
	
	public Boolean getAddResult() {
		return addResult;
	}
	public void setAddResult(Boolean addResult) {
		this.addResult = addResult;
	}
	public Boolean getValidateResult() {
		return validateResult;
	}
	public void setValidateResult(Boolean validateResult) {
		this.validateResult = validateResult;
	}

	public List<ItemTinyBean> getItems() {
		return items;
	}
	public void setItems(List<ItemTinyBean> items) {
		this.items = items;
	}
	public String getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

}

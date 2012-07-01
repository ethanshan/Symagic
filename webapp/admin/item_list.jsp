<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Symagic网上书城</title>
<link href="css/frame.css" rel="stylesheet" type="text/css" />
<link href="css/gz.css" rel="stylesheet" type="text/css" />
<script src="js/checkform.js" type="text/javascript"
	language="javascript"></script>
<script src="js/gz.js" type="text/javascript" language="javascript"></script>
<script src="js/jquery.js" type="text/javascript" language="javascript"></script>

</head>

<body>
	<div id="container">
		<div id="logalleft">
			<div id="logalright">
				<a href="" target="_parent">管理员 已登录|</a> <a href="index.html"
					target="_parent">退出</a>
			</div>
		</div>
		<a name="0"></a>
		<div id="globallink">
			<ul>
				<li><a href="index.html">首页</a>
				</li>
				<li><a href="category_list.html">目录管理</a>
				</li>
				<li><a href="item_list.html">商品管理</a>
				</li>
				<li><a href="order_admin.html">订单管理</a>
				</li>
				<li><a href="salesdata_admin.html">销售量统计</a>
				</li>
				<li><a href="user_admin.html">会员管理</a>
				</li>
				<li><a href="comment_list.html">评论管理</a>
				</li>
				<li><a href="" class="nouseful">&nbsp;</a>
				</li>
			</ul>
		</div>



		<div id="main">
			<div id="search2">
				<div id="searchleft">
					<img src="../image/ico_site.jpg" id="ico_site" /> 网站路径：<a
						href="category_list.html">商品管理</a>&gt;
				</div>

				<div id="" style="float:right">
					<form action="quick_search" method="post">
						<select name="catalogID">
							<option value="0">所有类别</option>
							<s:iterator value="catalog" var='outer'>
								<option value="<s:property value='#outer.ID'/>">
									<s:property value='#outer.name' />
								</option>
								<s:iterator value="#outer.childCatalog" var="inner">
									<option value="<s:property value='#inner.ID'/>">
										&nbsp;&nbsp;&nbsp;
										<s:property value='#inner.name' />
									</option>
								</s:iterator>
							</s:iterator>
						</select> <input type="text" name="keyword" id="keyword" class="gray"
							value="" /> <input type="submit" value="搜索" onclick="" />
					</form>
				</div>
				<div class="user_note" id="myfont">
					<a href="#1"><img src="image/add_product.png" /> </a> <a href="#2"><img
						src="image/item_list.png" /> </a> <a href="#3"><img
						onclick="expanse(document.getElementById('search_banner'));"
						src="image/item_search.png" /> </a>
				</div>


			</div>

			<div class="user_note">
				<a name="3"></a>
				<div id="doublehead1">
					<strong>高级搜索</strong> <a id="header_right" href="#0"><input
						type="button" value="返回顶部" /> </a>
				</div>
				<div class="fliter">
					<span id="search_banner" class="collapse"
						onclick="show_item_search(this);"></span>
				</div>
				<div id="item_search1" class="user_note_content hide">
					<form action="item_manager" method="post">
						<table>
							<tr>
								<th>书&nbsp;&nbsp;&nbsp;名：</th>
								<td><input type="text" name="name" /></td>
								<th>作&nbsp;&nbsp;&nbsp;者：</th>
								<td><input type="text" name="author" /></td>
							</tr>
							<tr>
								<th>出版社：</th>
								<td><input type="text" name="publisher" /></td>
								<th>书籍描述：</th>
								<td><input type="text" name="detail" /></td>
							</tr>
							<tr>
								<th>书籍类别：</th>
								<td><select name="catalogID" class="midselect">
										<option value="0">所有类别</option>
										<s:iterator value="catalog" var='outer'>
											<option value="<s:property value='#outer.ID'/>">
												<s:property value='#outer.name' />
											</option>
											<s:iterator value="#outer.childCatalog" var="inner">
												<option value="<s:property value='#inner.ID'/>">
													&nbsp;&nbsp;&nbsp;
													<s:property value='#inner.name' />
												</option>
											</s:iterator>
										</s:iterator>
								</select></td>
								<th>出版时间：</th>
								<td><select name="publishTime" class="midselect">
										<option value="0">不论时间</option>
										<option value="1">今年</option>
										<option value="2">去年</option>
										<option value="3">2年前</option>
										<option value="4">3年前</option>
										<option value="5">3年以上</option>
								</select></td>
							</tr>

							<tr>
								<th>页数范围：</th>
								<td><select name="searchPage" class="midselect">
										<option value="0">不限版次</option>
										<option value="1">0-200</option>
										<option value="2">200-400</option>
										<option value="3">400-600</option>
										<option value="4">600以上</option>
								</select></td>
								<th>版&nbsp;&nbsp;&nbsp;次：</th>
								<td><select name="edition" class="midselect">
										<option value="0">全部版次</option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
								</select></td>
							</tr>
							<tr>

								<th>装&nbsp;&nbsp;&nbsp;帧：</th>
								<td><select name="binding" class="midselect">
										<option value="0">不论</option>
										<option value="1">平装</option>
										<option value="2">精装</option>
								</select></td>
								<th>开&nbsp;&nbsp;&nbsp;本：</th>
								<td><select name="booksize" class="midselect">
										<option value="0">不论</option>
										<option value="1">32</option>
										<option value="2">16</option>
										<option value="3">8</option>
								</select></td>
							</tr>
							<tr>

								<th>价&nbsp;&nbsp;&nbsp;格：</th>
								<td><select name="price" class="midselect">
										<option value="0">不指定</option>
										<option value="1">0-10元</option>
										<option value="2">10-30元</option>
										<option value="3">30-50元</option>
										<option value="4">50-100元</option>
										<option value="5">100元以上</option>
								</select></td>
								<th>商品折扣：</th>
								<td><select name="discount" class="midselect">
										<option value="0">所有折扣</option>
										<option value="1">3折以下</option>
										<option value="2">3-5折</option>
										<option value="3">5-7折</option>
										<option value="4">7折及以上</option>
								</select></td>
							</tr>
							<tr>
								<th></th>
								<td id="button_right"><input type="submit" name="button2"
									value="搜索" /> <!-- onClick="javascript:window.location.href='item_search_list.html'"-->

								</td>

							</tr>
						</table>
					</form>

				</div>
			</div>
			<div id="double1">
				<div id="doublehead1">
					<strong>商品列表</strong> <a id="header_right" href="#0"><input
						type="button" value="返回顶部" /> </a>
				</div>

				<div id="doublecontent1">

					<table id="xialabiao">

						<s:iterator value="items" var="bookItems" status="st">
							<tr id="<s:property value = '#bookItems.itemID'/>">
								<td width="7%" rowspan="1" align="center"><span class="red"><s:property
											value="#st.index" /> </span>
								</td>
								<td class="checkBoxWidth" align="center"><input
									type="checkbox"
									value="<s:property value = '#bookItems.itemID'/>" />
								</td>
								<td width="16%" rowspan="1" align="right" class="inputHeader">
									<a
									href="item_detail?itemID=<s:property value = '#bookItems.itemID'/>"><img
										src="<s:property value="#request.get('javax.servlet.forward.context_path')"/><s:property value="#bookItems.picturePath"/>"
										alt="<s:property value = '#bookItems.name'/>" id="img_format" />
								</a></td>
								<td width="77%" align="left" class="inputHeader"><span
									class="red" id="item_font20"><s:property
											value="#bookItems.name" /> </span></font><br /> <font id="item_font17">作者：<s:property
											value="#bookItems.author" />&nbsp; &nbsp; </font><br /> <font
									id="item_font17">出版社：<s:property
											value="#bookItems.publisher" />&nbsp; &nbsp;出版时间：<s:property
											value="#bookItems.publishTime" /> </font>&nbsp; <br /> <font
									id="item_font17"> 市场价:<s:property
											value="#bookItems.marketPrice" />&nbsp;</font><font
									id="item_font17" class="font_right">商城价：<s:property
											value="#bookItems.price" /> </font></td>
								<td align="left" class="inputHeader"><a
									href="item_modify?itemID=<s:property value = '#bookItems.itemID'/>"><input
										type="button" value="修改" /> </a>&nbsp;&nbsp; <s:if
										test="%{#bookItems.offline}">
										<input type="button"
											onclick="ajax_item_up(this,
											<s:property value = '#bookItems.itemID'/>);"
											value="上架" />
									</s:if> <s:else>
										<input type="button"
											onclick="ajax_item_off(this,
											<s:property value = '#bookItems.itemID'/>);"
											value="下架" />
									</s:else> &nbsp;&nbsp; <a
									onclick="ajax_delete_tag(<s:property value = '#bookItems.itemID'/>);"><input
										type="button" value="删除" /> </a>&nbsp;&nbsp;</td>
							</tr>
						</s:iterator>
					</table>
				</div>
			</div>
			<!--        <a href="item_list_refresh.html" style="display:none" onClick="javascript:if(window.confirm('确定删除？')) window.location.href='item_list_refresh.html';else return false;"><img src="../image/bt_delete.gif" alt="删除" width="37" height="19" border="0"></a>-->
			<!--  </td>
          </tr>
		  
          <tr>
            <td width="7%" rowspan="4" align="center"><span class="red">2</span></td>
             <td width="16%" rowspan="3" align="center" class="inputHeader"><img src="../image/dxwl2.jpg" alt="大学物理第2册" width="70" height="100"></td>
			<td align="left" rowspan="5	"class="inputHeader"><span class="red">大学物理学.第二册：热学（第2版）</span></font>
            <br/>作者：霍金&nbsp;出版社：机械工业出版社<br/>
            出版时间：2010-05-14<br />
            市场价：￥9.50&nbsp;&nbsp;会员价：￥8.08
            </td>
			</tr>
          <tr>
            <td align="left" class="inputHeader">&nbsp;&nbsp;
            <input type="button" value="修改"/>&nbsp;&nbsp;
            <input type="button" value="下架"/>&nbsp;&nbsp;
		    <input type="button" value="删除"/>&nbsp;&nbsp;
            </td>
          </tr>
           <tr>
            <td width="7%" rowspan="4" align="center"><span class="red">3</span></td>
             <td width="16%" rowspan="4" align="center" class="inputHeader"><img src="../image/dxwl1.jpg" alt="大学物理第1册" width="68" height="100"></td>
			 <td align="left" class="inputHeader"><span class="red">大学物理学.第一册：力学（第2版）</span>
             <br />作者：郝少年&nbsp;出版社：华南理工大学出版社<br />
             出版时间：2011-05-18
             </td>
           </tr>
           <tr>
             <td align="left" class="inputHeader">市场价：￥16.00</td>
             </tr>
           <tr>
             <td align="left" class="inputHeader">会员价：￥13.60</td>
             </tr>
           <tr>
             <td align="left" class="inputHeader"> &nbsp;&nbsp;
             <input type="button" value="修改"/>&nbsp;&nbsp;
            <input type="button" value="下架"/>&nbsp;&nbsp;
		<input type="button" value="删除"/>&nbsp;&nbsp;
             </td>-->



			<a name="1"></a>
			<div id="double2">
				<div id="doublehead2">
					<strong>添加商品</strong> <a id="header_right" href="#0"><input
						type="button" value="返回顶部" /> </a>
				</div>
				<div id="doublecontent2">
					<form action="item_add_submit" method="post"
						enctype="multipart/form-data">
						<table id="tradequery">
							<tr>
								<th width="181">图书名称：</th>
								<td width="390"><input type="text" class="inputttextlarge"
									name="name" onFocus="nextfield='name'" maxlength="25" />
								</td>

								<td width="211"><span class="red">*必填项</span>
								</td>
							</tr>
							<tr>
								<th>商品描述：</th>
								<td><textarea name="description" class="textAreaStyle"></textarea>
								</td>
								<td></td>
							</tr>
							<tr>
								<th><span class="inputHeader">商品图片：</span>
								</th>
								<td><input type="file" name="picture" />
								</td>
								<td></td>
							</tr>
							<tr>
								<th>作者：</th>
								<td><input type="text" name="author" value="" />
								</td>
								<td><span class="red">*必填项</span>
								</td>
							</tr>

							<tr>
								<th>出版社：</th>
								<td><input type="text" name="publisher" value="" />
								</td>
								<td><span class="red">*必填项</span>
								</td>
							</tr>


							<tr>
								<th>出版时间：</th>
								<td><select name="publishTime.year" id="sYear">

								</select> &nbsp;年&nbsp; <select name="publishTime.month" id="sMonth">

								</select> &nbsp;月&nbsp; <select name="publishTime.day" id="sDay">

								</select> &nbsp;日</td>
								<script type="text/javascript">
var selYear = window.document.getElementById("sYear");
var selMonth = window.document.getElementById("sMonth");
var selDay = window.document.getElementById("sDay");

// 新建一个DateSelector类的实例，将三个select对象传进去
//new DateSelector(selYear, selMonth ,selDay, 2004, 2, 29);
// 也可以试试下边的代码
var dt = new Date(2012, 0, 1);
new DateSelector(sYear, sMonth ,sDay, dt);
</script>
								<td>#选填项</td>
							</tr>
							<tr>
								<th>ISBN：</th>
								<td><input type="text" name="ISBN" value="" />
								</td>
								<td><span class="red">*必填项</span>
								</td>
							</tr>
							<th>版次：</th>
							<td><input type="text" name="edition" class="smallinputext"
								value="" />
							</td>
							<td>#选填项</td>

							</tr>
							</tr>
							<th>开本：</th>
							<td><input type="text" name="size" class="smallinputext"
								value="" />
							</td>
							<td>#选填项</td>

							<tr>
								<th>页数：</th>
								<td><input type="text" name="page" class="smallinputtext"
									value="" /></td>
								<td>#选填项</td>
							</tr>

							</tr>
							<tr>
								<th>装帧：</th>
								<td><select name="binding">
										<option>精装</option>
										<option>平装</option>
								</select>
								</td>
								<td><span class="red">*必填项</span>
								</td>
							</tr>
							<tr>
								<th>分类标签：</th>
								<td><select name="bookClassify">
										<option value="0">不分类</option>
										<s:iterator value="catalog" var='outer'>
											<s:iterator value="#outer.childCatalog" var="inner">
												<option value="<s:property value='#inner.ID'/>">
													<s:property value='#outer.name' />
													--
													<s:property value='#inner.name' />
												</option>
											</s:iterator>
										</s:iterator>
								</select>
								</td>
								<td>#选填项</td>
							</tr>
							<th>市场价：</th>
							<td><input type="text" name="marketPrice"
								class="smallinputext" value="" /></td>
							<td><span class="red">*必填项</span>
							</td>
							</tr>
							<tr>
								<th>折扣：</th>
								<td><input type="text" name="discount"
									class="smallinputext" value="" /></td>
								<td><span class="red">*必填项</span>
								</td>
							</tr>
							<tr>
								<th>库存量：</th>
								<td><input type="text" name="inventory"
									class="smallinputext" value="" /></td>
								<td><span class="red">*必填项</span>
								</td>

							</tr>
							<tr>
								<th></th>
								<td><input type="submit" name="button2" value="添加"
									onclick="checkitemform()" /> <input type="reset"
									name="button1" value="重填" onclick="clear()" /></td>
								<td></td>
							</tr>
						</table>
					</form>
				</div>

			</div>
			<div id="footer">
				<span id="footerleft"> &nbsp;隐私权 | 版权 | 法律声明 |
					电子邮件：Symagics@gmail.com </span> <span id="footerright"> Symagic网上书城
					Power by Symagic &nbsp;</span>
			</div>
		</div>
	</div>
</body>
</html>

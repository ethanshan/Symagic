﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Symagic网上书城</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="css/gz.css" rel="stylesheet" type="text/css" />
<link href="css/frame.css" rel="stylesheet" type="text/css" />
<link href="css/home.css" rel="stylesheet" type="text/css" />

<script language="javascript" src="js/jquery.js"></script>
<script language="javascript" src="js/gz.js"></script>
<script language="javascript" src="js/checkform.js"></script>
<script language="javascript" src="js/catalog.js"></script>

</head>

<body>
	<div id="container">
		<div id="logalleft">
			<div id="logalright">
				<a href="" target="_parent">管理员 已登录|</a> <a href="logout"
					target="_parent">退出</a>
			</div>
		</div>
		<div id="globallink">
			<ul>
				<li><a href="index">首页</a></li>
				<li><a href="catalog_manager">目录管理</a></li>
				<li><a href="javascript:document.getElementById('clear_search').submit()">商品管理</a></li>
                <!--清空详细搜索的隐藏表单-->
            	<form action="item_manager" method="post" id="clear_search" style="display:none">
           	 	<input name="page" value="1"/>
            	<input name="name" />
            	<input name="author"/>
            	<input name="publisher"/>
            	<input name="catalogID" value="0"/>
            	<input name="publishTime" value="0"/>
            	<input name="searchPage" value="0"/>
            	<input name="edition" value="0"/>
            	<input name="binding" value="0"/>
            	<input name="booksize" value="0"/>
            	<input name="price" value="0"/>
            	<input name="discount" value="0"/>
            	<input />
            	</form>
            	<!--清空详细搜索的隐藏表单-->
				<li><a href="javascript:document.getElementById('clear_order_search').submit()">订单管理</a>
				</li>
                <!--清空订单精确查询的隐藏表单-->
            	<form action="order_list?page=1" method="post" id="clear_order_search" enctype="multipart/form-data" style="display:none">
           	 	<input type="text" name="userName" />
            	<select name="orderState">
                <option value="0" selected="selected"></option>
                </select>
            	<select name="startTime.year" >
                <option value="2007" selected="selected"></option>
                </select>
                <select name="startTime.month" >
                <option value="1" selected="selected"></option>
                </select>
                <select name="startTime.day" >
                <option value="1" selected="selected"></option>
                </select>
                <select name="endTime.year" >
                <option value="2012" selected="selected"></option>
                </select>
                <select name="endTime.month" >
                <option value="12" selected="selected"></option>
                </select>
                <select name="endTime.day" >
                <option value="31" selected="selected"></option>
                </select>
            	</form>
            	<!--清空订单精确查询的隐藏表单-->
				<li><a href="javascript:document.getElementById('clear_static_search').submit()">销售量统计</a></li>
                <!--清空销售量查询的隐藏表单-->
            	<form action="order_statistics?page=1" method="post" enctype="multipart/form-data"  id="clear_static_search" style="display:none">
           	 	<select name="startTime.year" >
                <option value="2007" selected="selected"></option>
                </select>
                <select name="startTime.month" >
                <option value="1" selected="selected"></option>
                </select>
                <select name="startTime.day" >
                <option value="1" selected="selected"></option>
                </select>
                <select name="endTime.year" >
                <option value="2012" selected="selected"></option>
                </select>
                <select name="endTime.month" >
                <option value="12" selected="selected"></option>
                </select>
                <select name="endTime.day" >
                <option value="31" selected="selected"></option>
                </select>
                <select name="catalogID">
				<option value="0" selected="selected"></option>
                </select>
            	<input type="text" name="limit"
										value="0" />
            	</form>
            	<!--清空销售量查询的隐藏表单-->
				<li><a href="user_level">会员管理</a></li>
				<li><a href="" class="nouseful">&nbsp;</a></li>
				<li><a href="" class="nouseful">&nbsp;</a></li>
			</ul>
		</div>
		<div id="left">
			<div id="catelog_admin_left2">
				<ul>
					<li><a href="catalog_manager">查看目录</a>
					</li>
					<li><a href="catalog_manager">添加目录</a>
					</li>
				</ul>
			</div>
		</div>

		<div id="main">
			<div id="search1">
				<div id="mysearchleft">
					<img src="../image/ico_site.jpg" id="ico_site" /> 网站路径：<a
						href="index">首页</a>&gt;&gt;<a href=""> 目录管理</a>
				</div>

			</div>
            
            
            
            
            
            
            
            
            
			<div id="mydouble">
				<div id="mydoublehead1">
					<strong>现有目录列表</strong>
                    </div>
					<div id="doublecontent1">
						<table id="catalog_list">
                        <!--父目录迭代开始-->
                        <s:iterator value="catalog" var="outer">
                        
                        <div id="<s:property value='#outer.name'/>">
                        <tr>
                        <td width="5%"></td>
                        <td width="35%">
          <img src="image/plus.gif" onclick="click_show('<s:property value='#outer.name'/>',6);" />

                        <s:property value="#outer.name" /></td>
                        <td width="45%"></td>
                        <td >&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="ajax_catalog_delete_tag_level1(<s:property value='#outer.ID'/>,'<s:property value='#outer.name'/>')">删除</a> &nbsp;
                        <a href="catalog_edit?catalogID=<s:property value='#outer.ID'/>"
												>修改</a></td>
                        </tr>
                        <!--子目录迭代开始-->
                        <s:iterator value="#outer.childCatalog" var="inner" status="st">

                        
                        <tr id="<s:property value='#outer.name'/><s:property value='#st.index'/>" style="display:none" >
                        <td width="5%"></td>
                         <td >&nbsp;&nbsp;&nbsp;&nbsp;<s:property value='#inner.name' /></td>
                        <td width="35%"></td>
                        <td><a href="javascript:void(0)" onclick="ajax_catalog_delete_tag_level2(<s:property value='#inner.ID'/>,'<s:property value='#outer.name'/>'+'<s:property value='#st.index'/>');">删除</a> &nbsp;
                        <a href="catalog_edit?catalogID=<s:property value='#inner.ID'/>">修改</a></td>
                        </tr>

                        
                        </s:iterator>	
                         <!--子目录迭代结束-->
                        </div>
                        </s:iterator>	
                        <!--父目录迭代结束-->
                        
                        </table>
                        
					
				</div>
			</div>


			<div id="mydouble">
				<div id="mydoublehead1">
					<strong>添加目录</strong>
				</div>
				<div id="doublecontent2">
					<form action="catalog_add_submit" method="post">
						<table id="catalog_itemsearch">
							<tr>
								<th class="itemsearchth">目录名：</th>
								<td class="itemsearchtd1"><input type="text"
									class="inputtext" name="catalogName"
									onfocus="nextfield='password'" /></td>
								<td class="itemsearchtd2"><span class="red">*&nbsp;必填项</span>
								</td>
							</tr>
							<tr>
								<th class="itemsearchth">父目录：</th>
								<td class="itemsearchtd1"><select name="upID">
										<option value="0" selected="selected">根目录</option>
										<s:iterator value="catalog" var="outer">
											<option value="<s:property value='#outer.ID'/>">
												<s:property value="#outer.name" />
											</option>
										</s:iterator>
								</select></td>
								<td class="itemsearchtd2">&nbsp;</td>
							</tr>
							<tr>
								<th class="itemsearchth"><span class="inputHeader">目录描述：</span>
								</th>
								<td class="itemsearchtd1"><textarea name="catalogDesc"
										class="textAreaStyle"></textarea></td>
								<td class="itemsearchtd2"><span class="red">*&nbsp;必填项</span>

							</tr>
							<tr>
								<th></th>
								<td><input type="submit" name="button2" value="添加"
									onclick="checkcategoryform()" /> &nbsp;<input type="reset"
									name="button1" value="重填" onclick="clear()" /></td>
							</tr>
						</table>
					</form>
				</div>
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

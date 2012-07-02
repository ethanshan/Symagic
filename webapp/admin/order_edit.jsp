﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>Symagic商城</title>
<link href="css/frame.css" rel="stylesheet" type="text/css"/>
<link href="css/gz.css" rel="stylesheet" type="text/css"/>
<script language="javascript" src="js/gz.js"></script>
<script language="javascript" src="js/jquery.js"></script>
</head>

<body>
<div id="container">
	<div id="logalleft">
		<div id="logalright">
		<a href="" target="_parent">管理员 已登录|</a>
	 	<a href="index.html" target="_parent">退出</a>
		</div>
	</div>
	<div id="globallink">
		<ul>
			<li><a href="index.html">首页</a></li>
                        <li><a href="category_list.html">目录管理</a></li>
			<li><a href="item_list.html">商品管理</a></li>
			<li><a href="order_admin.html">订单管理</a></li>
			<li><a href="salesdata_admin.html">销售量统计</a></li>
			<li><a href="user_admin.html">会员管理</a></li>
			<li><a href="comment_list.html">评论管理</a></li>
			<li><a href="" class="nouseful">&nbsp;</a></li>
		</ul>
	</div>
<!--	<div id="banner"></div>-->
	<div id="main">
		<div id="search2">
			<div id="searchleft">
				<img src="../image/ico_site.jpg"  id="ico_site"/>
				网站路径：订单修改
			</div>
		</div>
	<form action="order_edit_submit" method="post" enctype="multipart/form-data" name="form1">	
		<div id="double1">
		
			<div id="doublehead1"><strong>配送信息</strong></div>
				
			<div id="doublecontent1">
	
				<table id="doublecontenttable3">
				 <tr>
					<td>订单号</td>
					<td><input type="text" id="inputGray" readonly="readonly" value="<s:property value='orderID'/>" /></td>
            		<td>用户名</td>
					<td><s:property value="userName"/>edsfgdsgdfeeertertewgdf</td>
					<td>下单时间</td>
					<td><s:property value="orderTime"/>sdfadsfdsfdsfdsaf</td>
         		 </tr>
		  		 <tr>
           			<td>订单金额</td>
					<td><s:property value="price"/></td>
					<td>支付方式</td>
					<td><s:property value="payment"/></td>
            		<td>送货方式</td>
					<td><s:property value="deliverWay"/></td>
			</tr>
			 <tr>
            	<td>收货人</td>
			    <td>
               <s:if test="orderStatus!='交易成功'||'交易失败'">
                <input type="text" value="<s:property value='receiver'/>"/>
                </s:if>
                <s:else>
                <s:property value="receiver"/>
                </s:else>
                </td>
			    <td>收货人地址</td>
            <td colspan="3">
           <select onchange="get_district(this)" id="level1ID" name="level1ID">
                                	<option value="s1">请选择</option>
                                <s:iterator value="level1Districts" var='iter'>
                                	<option value="<s:property value='#iter.ID'/>"><s:property value='#iter.name'/></option>
                                </s:iterator>
                                </select>
                                <select onchange="get_district(this)" id="level2ID" name="level2ID">
                                	<option value="s2">请选择</option>
                                </select>
                                <select id="level3ID" name="level3ID">
                                	<option value="">请选择</option>
                                </select>
                                <input type="text" value="<s:property value='addressDetail'/>"/>
            </td>	
			</tr>
            
			 <tr>
           <td>邮政编码</td>
           <td>
             <s:if test="orderStatus!='交易成功'||'交易失败'">
                <input type="text" value="<s:property value='zipcode'/>"/>
                </s:if>
                <s:else>
                <s:property value="zipcode"/>
                </s:else>
           </td>
            
			<td>收货人手机</td>
			<td> 
                <s:if test="orderStatus!='交易成功'||'交易失败'">
                <input type="text" name="mobileNumber" value="<s:property value='mobileNumber'/>"/>
                </s:if>
                <s:else>
                <s:property value="mobileNumber"/>
                </s:else>
                </td>
            <td>收货人电话</td>
            <td>
                <s:if test="orderStatus!='交易成功'||'交易失败'">
                <input type="text" value="<s:property value='phoneNumber'/>"/>
                </s:if>
                <s:else>
                <s:property value="phoneNumber"/>
                </s:else>
            </td>
            </tr>
            <tr>
            
		    <td>订单状态</td>
			<td><s:property value="orderStatus"/></td>
									
				 </tr>
            </table>
			</div></div>
			<div id="double2">
			<div id="doublehead2"><strong>商品信息</strong></div>
			<div id="doublecontent2">

				<table id="favorite">
				<thead>
					<tr>
						<th>商品编号</th>
						<th>商品名称</th>
						<th>商城价</th>
						<th>数量</th>
                        <th>商品小计</th>
					</tr>
				</thead>
				<tbody>
                <!--开始对客户购买的商品条项进行迭代-->
                 <s:iterator value = "items" var = "itemArray">
				  <tr>
            		<td><span><s:property value = "#itemArray.itemID"/></span></td>
					<td><a href="item_detail?itemID=<s:property value = '#itemArray.itemID'/>">
                    </a><s:property value = "#itemArray.itemName"/>
                    </td>
					<td>￥<s:property value = "#itemArray.price"/></td>
					<td> 
                    <s:if test="orderStatus!='交易成功'||'交易失败'">
                  <span><a class="reduce" onclick="reduce(<s:property value = '#itemArray.itemID'/>);" href="javascript:void(0)">-</a>
        <input type="text" class="amount" value="<s:property value = '#itemArray.itemNumber'/>" id="<s:property value = '#itemArray.itemID'/>" onkeyup="amount_modify(this)" />
        <a class="reduce" onclick="add(<s:property value = '#itemArray.itemID'/>);" href="javascript:void(0)">+</a>
        </span>
                   </s:if>
                   <s:else>
                   <s:property value="itemNumber"/>
                   </s:else>
                    </td>
					<td>￥<s:property value = "#itemArray.itemTotalPrice"/></td>
          		  </tr>
                  </s:iterator>
                  <!--商品条项迭代结束-->
                  <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td><s:if test="orderStatus=='已下单'">
                <input onclick="" type="submit" value="审核"/>
                </s:if><input type="submit" value = "提交修改"/></td>
		
                  
                  </tr>
				 </tbody>
                 
            </table>
            
   </div>     
  </div>
  </form>
</div>
<div id="footer">
		<span id="footerleft"> &nbsp;隐私权 | 版权 | 法律声明 | 电子邮件：Symagics@gmail.com </span>
		<span id="footerright"> Symagic网上书城  Power by Symagic	 &nbsp;</span>
	</div>
	</div>
</body>
</html>

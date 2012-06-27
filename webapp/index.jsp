<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>BC2商城</title>
<link href="css/home.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/yf_ADS.js"></script>
</head>
<body>
<div id="container">
<div id="cart">
	<span id="cart_loading"></span>
	<div id="cart_none">您的购物车中还没有商品，请选购！</div>
	<div id="cart_container"></div>
</div>
	<div id="logalleft">
    <div id="top-overlay"></div>
    <!----吊顶栏------>
	<div class="top">
    <div class="top_right">
    <ul>
    <s:if test="nickname!=null">
    <li><s:property value="nickname"/>！&nbsp;&nbsp;欢迎回到Symagic！</li>
    <li id="logout_top" onclick="logout();"><a>安全退出</a></li>
    </s:if>
    <s:else>
    <li>欢迎来到Symagic！</li>
    <li id="login_top" onclick="load_login();"><a>登录</a></li>
    <li id="regist_top" onclick="load_regist();"><a>免费注册</a></li>
    </s:else>
    <li class="division">|</li><li id="mymall"><a href="user.html"><span id="mymall_icon"></span>我的商城</a></li><li class="division">|</li>
    <li id="cart_top"><a id="cart_a" href="cart.html">
    <span id="cart_icon"></span>购物车 <strong id="cart_num"><s:property value='totalNumber'/></strong> 件</a>
    </li>
    </ul>
    </div>
    </div>
  <!------吊顶结束----->
	</div>
	<div id="globallink">
		<ul>
			<li><a href="index">首页</a></li>
			<li><a href="item_list.html">商品列表</a></li>
			<li><a href="favorite.html">收藏夹</a></li>
			<li><a href="address.html">地址簿</a></li>
			<li><a href="tradequery.html">交易查询</a></li>
			<li><a href="send_notes.html">送货说明</a></li>
            <li><a class="nouseful">&nbsp;</a></li>
			<li><a class="nouseful">&nbsp;</a></li>
		</ul>
	</div>
    <div class="clear"></div>
	<div id="left">
		<div id="category">
		<s:iterator value="catalog" >
			<a href="item_list&catalogID=<s:property value='ID'/>"><h4 ><span><s:property value='name'/></span></h4></a>
			<ul>
			<s:iterator value="top" id="inner">
				<li><a href="item_list&catalogID=<s:property value='#inner.ID'/>"/><s:property value='#inner.name'/></a></li>
			</s:iterator>
			</ul>
		</s:iterator>
      </div>
	</div>
	<div id="main">
		<div id="search1">
			<div id="searchleft">
				<img src="image/ico_site.jpg" id="ico_site"/>
				网站路径：<a href="index">首页</a>			</div>
			<div id="searchright">
            <form action="quick_search" >
			  <select name="catalog" >
				<option value="0">所有类别</option>
              </select>
			  <input type="text" name="keyword" id="keyword" class="gray" value="快速搜索...."onFocus="onfocus_check(this,'快速搜索....')" onblur="onblur_check(this,'快速搜索....')" />
			  <input type="button" name="Submit" value="搜索" onClick="">
			</form>
            </div>
		</div>
		<div id="recommend" >
			<ul>
            <!--迭代开始-->
            <s:iterator value='newBook' id='iter'>
				<li><a href="<s:property value='#iter.itemID'/>"><img src="<s:property value='#iter.picturePath'/>">
                <br>
                <div class="inputHeader"><s:property value='#iter.name'/></div>
	     		<div class="price">商城价：<strong>￥<s:property value='#iter.price'/></strong></div>
         		</a>
         		</li>
         	</s:iterator>
            <!--迭代结束-->
			</ul>
			<br>&nbsp;
	    </div>
		<div id="new">
			<ul>
		 		<!--迭代开始-->
            <s:iterator value='hotBook' id='iter'>
				<li><a href="<s:property value='#iter.itemID'/>"><img src="<s:property value='#iter.picturePath'/>">
                <br>
                <div class="inputHeader"><s:property value='#iter.name'/></div>
	     		<div class="price">商城价：<strong>￥<s:property value='#iter.price'/></strong></div>
         		</a>
         		</li>
         	</s:iterator>
            <!--迭代结束-->
			<br>&nbsp;
		</div>
		<div id="life">
			<ul>
			<!--迭代开始-->
            <s:iterator value='recommendItem' id='iter'>
				<li><a href="<s:property value='#iter.itemID'/>"><img src="<s:property value='#iter.picturePath'/>">
                <br>
                <div class="inputHeader"><s:property value='#iter.name'/></div>
	     		<div class="price">商城价：<strong>￥<s:property value='#iter.price'/></strong></div>
         		</a>
         		</li>
         	</s:iterator>
            <!--迭代结束-->
			</ul>
			<br>&nbsp;
		</div>
	</div>
	<div id="footer">
		<span id="footerleft"> &nbsp;隐私权 | 版权 | 法律声明 | 电子邮件：admin@163.com </span>
		<span id="footerright"> B2C商城  Power by IBM &nbsp;</span>
	</div>
</div>

</body>
</html>

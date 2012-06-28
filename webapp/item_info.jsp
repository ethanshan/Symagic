<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>BC2商城</title>
<link href="css/frame.css" rel="stylesheet" type="text/css">
<link href="css/item_info.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/yf_ADS.js"></script>
<script type="text/javascript" src="js/item_info.js"></script>
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
    <li>欢迎来到Symagic！</li>
    <s:property value="#session.username"/><s:property value="#session.nickname"/>
    <li id="login_top" onclick="load_login();"><a>登录</a></li>
    <li id="regist_top" onclick="load_regist();"><a>免费注册</a></li>
    <li class="division">|</li><li id="mymall"><a href="user.html"><span id="mymall_icon"></span>我的商城</a></li><li class="division">|</li>
    <li id="cart_top"><a id="cart_a" href="cart.html">
    <span id="cart_icon"></span>购物车 <strong id="cart_num">0</strong> 件</a>
    </li>
    </ul>
    </div>
    </div>
  <!------吊顶结束----->
	</div>
	<div id="globallink">
		<ul>
			<li><a href="index.html">首页</a></li>
			<li><a href="item_list.html">商品列表</a></li>
			<li><a href="favorite.html">收藏夹</a></li>
			<li><a href="address.html">地址簿</a></li>
			<li><a href="tradequery.html">交易查询</a></li>
			<li><a href="send_notes.html">送货说明</a></li>
            <li><a class="nouseful">&nbsp;</a></li>
			<li><a class="nouseful">&nbsp;</a></li>
		</ul>
	</div>
<!--	<div id="banner"></div>-->
	<div id="main">
		<div id="search2">
			<div id="searchleft">
				<img src="image/ico_site.jpg"  id="ico_site"/>
				网站路径：<a href="index.html">首页</a>&gt;&gt;<a href="#">商品详细信息</a>
			</div>
			<div id="searchright2">
			  <input type="text" name="product" id="textInput"/>
			  <input type="button" name="Submit" value="搜索" id="searchbutton" onClick="javascript:window.open('item_search_list.html','_parent','')">
			</div>
			<div id="searchright1">
			  <select name="category" id="searchrightcategory">
				<option value="5">所有类别</option>
                <option value="1">图书音像</option>
                <option value="2">时尚生活</option>
                <option value="3">饰品配件</option>
                <option value="4">数码产品</option>
              </select>
		  </div>
		</div>
        <div class="clear"></div>
        <!--购买推荐左边栏-->
      <div class="recommend">
        <div class="head"><h3>购买本书的顾客还购买了</h3></div>
        <!----推荐迭代li---->
       <li><div class="p-img"><a href="" title="数学之美" target="_blank"><img width="50" height="50" src="upload/linux.jpg"></a></div>						 		<div class="p-name"><a href="" >高性能Linux服务器构建实战</a></div>
       <div class="p-price"><strong>￥30.00</strong></div></li>
       <!----推荐迭代li结束---->
        <!----推荐迭代li---->
       <li><div class="p-img"><a href="" title="数学之美" target="_blank"><img width="50" height="50" src="upload/linux.jpg"></a></div>						 		<div class="p-name"><a href="" >高性能Linux服务器构建实战</a></div>
       <div class="p-price"><strong>￥30.00</strong></div></li>
       <!----推荐迭代li结束---->
        <!----推荐迭代li---->
       <li><div class="p-img"><a href="" title="数学之美" target="_blank"><img width="50" height="50" src="upload/linux.jpg"></a></div>						 		<div class="p-name"><a href="" >高性能Linux服务器构建实战</a></div>
       <div class="p-price"><strong>￥30.00</strong></div></li>
       <!----推荐迭代li结束---->
        <!----推荐迭代li---->
       <li><div class="p-img"><a href="" title="数学之美" target="_blank"><img width="50" height="50" src="upload/linux.jpg"></a></div>						 		<div class="p-name"><a href="" >高性能Linux服务器构建实战</a></div>
       <div class="p-price"><strong>￥30.00</strong></div></li>
       <!----推荐迭代li结束---->
        <!----推荐迭代li---->
       <li><div class="p-img"><a href="" title="数学之美" target="_blank"><img width="50" height="50" src="upload/linux.jpg"></a></div>						 		<div class="p-name"><a href="" >高性能Linux服务器构建实战</a></div>
       <div class="p-price"><strong>￥30.00</strong></div></li>
       <!----推荐迭代li结束---->
        <!----推荐迭代li---->
       <li><div class="p-img"><a href="" title="数学之美" target="_blank"><img width="50" height="50" src="upload/linux.jpg"></a></div>						 		<div class="p-name"><a href="" >高性能Linux服务器构建实战</a></div>
       <div class="p-price"><strong>￥30.00</strong></div></li>
       <!----推荐迭代li结束---->
        </div>
        <!--购买推荐左边栏结束-->
        <!--商品详细信息-->
        <div class="item_info">
        <div class="fliter"></div>
        <div class="name"><h2>大学物理学.第四册：波动与光学（第2版）</h2></div>
        <div id="preview">
        <div id="spec-n1"><img src="upload/linux.jpg"/></div>
        <ul>
        <li><span>总评分：</span><span class="star"><span class="sa45"></span></span></li>
        </ul>
        </div>
        <ul id="summary">
        <li><span>作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：</span>张嗣音</li>
        <li><span>出&nbsp;&nbsp;版&nbsp;&nbsp;社：</span>清华大学出版社</li>
        <li><span>I&nbsp;&nbsp;S&nbsp;&nbsp;B&nbsp;&nbsp;N：</span>34516661722838</li>
        <li><span>出版日期：</span>2011-11-11</li>
        <li><span>版&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次：</span>2</li>
        <li><span>开&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本：</span>16开</li>
        <li><span>装&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;帧：</span>平装</li>
        <li><span>纸&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;张：</span>胶印纸</li>
        <li><span>语&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;种：</span>中文</li>
        </ul>
        <ul id="book_price">
        <li><span>定&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：</span><del>￥9.50</del></li>
        <li><span>商&nbsp;&nbsp;城&nbsp;&nbsp;价：</span><strong>￥8.08</strong></li>
        <li><span>为您节省：</span><strong>￥1.42</strong></li>
        <li><span>库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存：</span>10</li>
        </ul>
        <div id="add_to_cart">
        <div id="item_amount">
        <span id="buy_num">购买数量：</span><span><a class="reduce" onclick="reduce();" href="javascript:void(0)">-</a>
        <input type="text" value="1" id="amount" onkeyup="amount_modify(this)">
        <a class="reduce" onclick="add();" href="javascript:void(0)">+</a>
        </span></div>
        <div class="btns">
					<a onclick="add_to_cart()" href="javascript:void(0)" class="append" ></a><a onclick="favorite()" href="javascript:void(0)" class="favorite"></a>
					<div class="clear"></div>
				</div>
        </div>
        <div class="clear"></div>
        <div class="item_desc">
        <div class="banner"><li>内容简介</li></div>
        <div class="item_desc_con">&nbsp;&nbsp;&nbsp;&nbsp;本书是清华大学教材《大学物理学》的第四册，讲述振动与波的一般基本规律和波动光学的基本原理，包括光的干涉、衍射和偏振。除了基本内容外，还专题介绍了全息照相、光学信息处理、液晶等今日物理趣闻和着名物理学家托马斯·杨和菲涅耳的传略。<br/>&nbsp;&nbsp;&nbsp;&nbsp;基本内容扼要，附加内容通俗易懂。本书可作为高等院校的大学物理教材，也可以作为中学物理教师教学或其他读者自学的参考书。</div>
        </div>
        <div id="comment">
        <div class="banner"><li>用户评价</li></div>
        <div class="comment_item">
        <div class="buyer">
        <span class="buyer_name">测试用户</span>
        <span class="sa45"></span>
        <span class="date-comment">2012-06-21 08:41:32</span>
        </div>
        <div class="comment-content">厚厚的一本，由浅入深，知识比较全面，适合初学者学习使用</div>
        </div>
        <div class="comment_item">
        <div class="buyer">
        <span class="buyer_name">测试用户2</span>
        <span class="sa4"></span>
        <span class="date-comment">2012-06-21 08:41:32</span>
        </div>
        <div class="comment-content">很不错的书，测试测试测试测试测试测试测试测试</div>
        </div>
        </div>
        </div>
      <div id="footer">
		<span id="footerleft"> &nbsp;隐私权 | 版权 | 法律声明 | 电子邮件：admin@163.com </span>
		<span id="footerright"> B2C商城  Power by IBM &nbsp;</span>
	</div>
</div>

</body>
</html>

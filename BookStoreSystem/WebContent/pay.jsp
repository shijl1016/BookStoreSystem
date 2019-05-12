<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="bean.*"%>
<%@ page import="bookStore.*" %>
<%@ page import="java.util.*" %>
<title>支付</title>
<%	ShoppingCart cart=(ShoppingCart)session.getAttribute("cart");
	userBean userLogin=(userBean)session.getAttribute("userLogin");
	if(cart!=null&&userLogin!=null){
		if(cart.getTotal()>userLogin.getBalance()){
			out.println("对不起,您的余额不足<br/>");		
		}else{
			DBUtil dbUtil=DBUtil.getInstance();
			userLogin.setBalance(userLogin.getBalance()-cart.getTotal());
			dbUtil.updateUser(userLogin);//更新用户余额
			dbUtil.updateBooks(cart);//更新图书数量。内含，清空购物车操作
			out.println("支付成功<br/>");
		}
	}else{
		request.getRequestDispatcher("/homePage").forward(request,response);
	}
%>
<a href="/BookStoreSystem/homePage">返回主界面</a>
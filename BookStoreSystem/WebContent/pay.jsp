<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="bean.*"%>
<%@ page import="bookStore.*" %>
<%@ page import="java.util.*" %>
<title>֧��</title>
<%	ShoppingCart cart=(ShoppingCart)session.getAttribute("cart");
	userBean userLogin=(userBean)session.getAttribute("userLogin");
	if(cart!=null&&userLogin!=null){
		if(cart.getTotal()>userLogin.getBalance()){
			out.println("�Բ���,��������<br/>");		
		}else{
			DBUtil dbUtil=DBUtil.getInstance();
			userLogin.setBalance(userLogin.getBalance()-cart.getTotal());
			dbUtil.updateUser(userLogin);//�����û����
			dbUtil.updateBooks(cart);//����ͼ���������ں�����չ��ﳵ����
			out.println("֧���ɹ�<br/>");
		}
	}else{
		request.getRequestDispatcher("/homePage").forward(request,response);
	}
%>
<a href="/BookStoreSystem/homePage">����������</a>
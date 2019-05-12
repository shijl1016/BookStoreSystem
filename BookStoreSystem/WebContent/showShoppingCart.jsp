<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<%@ page import="bookStore.*" %>
<%@ page import="bean.*" %>
<%@ page import="java.util.*" %>
<title>���ﳵ</title>
<%	userBean userLogin=(userBean)session.getAttribute("userLogin");
if(userLogin!=null){//�ѵ�¼
	ShoppingCart cart=null;
	session.setAttribute("error", "");
	if(session.getAttribute("cart")==null){
		cart=new ShoppingCart();
	}else{
		cart=(ShoppingCart)session.getAttribute("cart");
	}
	if(request.getParameter("submit")!=null){
		DBUtil dbUtil=DBUtil.getInstance();
		String s_id=request.getParameter("ID");
		int id=0;
		try{
			id=Integer.parseInt(s_id);
		}catch(NumberFormatException e){
			session.setAttribute("error", "�������ID����");
			%>
			<jsp:forward page="/homePage"/>
			<%
		}
		CartItem cartItem=dbUtil.getBookInfo(id);
		if(cartItem==null){
			session.setAttribute("error", "��ѡ���鲻����");
			%>
			<jsp:forward page="/homePage"/>
			<%
		}
		CartItem mcartItem=cart.getCartItem(id);//�ж����Ƿ���
		if((cartItem.getAmount()==0)){
			session.setAttribute("error", "��ѡ����������Ѵ�����");
			%>
			<jsp:forward page="/homePage"/>
			<%
		} 
		if(mcartItem!=null&&mcartItem.getAmount()==cartItem.getAmount()){
			session.setAttribute("error", "��ѡ����������Ѵ�����");
			%>
			<jsp:forward page="/homePage"/>
			<%
		}
		//������
		CartItem item=new CartItem(cartItem.getId(),cartItem.getName(),1,cartItem.getPrice());
		cart.addCartItem(item);
		session.setAttribute("cart", cart);
	}
	if(request.getParameter("isEmpty")!=null){//�Ƿ���չ��ﳵ
		cart.getCart().removeAll(cart.getCart());
	}
	out.println("���:"+userLogin.getBalance()+"Ԫ<br/>");
	if(!cart.getCart().isEmpty()){//��ʾ���ﳵ
		ArrayList<CartItem> mcart=cart.getCart();
		Iterator<CartItem> it=mcart.iterator();
		CartItem mItem = null;
		out.print("<table>");
		out.println("<tr><th align='left'>ID</th><th align='left'>����</th><th align='left'>����</th><th align='left'>�۸�</th><tr/>");
		while (it.hasNext()) {
			mItem = it.next();
			out.print("<tr><td align='left'>"+mItem.getId()+"</td>");
			out.print("<td align='left'>"+mItem.getName()+"</td>");
			out.print("<td align='left'>"+mItem.getAmount()+"</td>");
			out.print("<td align='left'>"+mItem.getPrice()+"</td></tr>");
		}
		out.println("<tr><td>�ܼ�:</td><td>"+cart.getTotal()+"</td></tr></table><br/>");
		out.println("<a href='/BookStoreSystem/pay.jsp'>֧��</a><br/>");
		out.println("<a href='/BookStoreSystem/showShoppingCart.jsp?isEmpty=1'>��տչ��ﳵ</a><br/>");
	}else{
		out.println("����û���κ���<br/>");
	}
%>
<a href="/BookStoreSystem/homePage">��������</a>
<%
}else{//δ��¼
	request.getRequestDispatcher("/homePage").forward(request,response);
}
%>
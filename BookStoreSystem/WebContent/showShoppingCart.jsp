<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<%@ page import="bookStore.*" %>
<%@ page import="bean.*" %>
<%@ page import="java.util.*" %>
<title>购物车</title>
<%	userBean userLogin=(userBean)session.getAttribute("userLogin");
if(userLogin!=null){//已登录
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
			session.setAttribute("error", "您输入的ID有误");
			%>
			<jsp:forward page="/homePage"/>
			<%
		}
		CartItem cartItem=dbUtil.getBookInfo(id);
		if(cartItem==null){
			session.setAttribute("error", "您选的书不存在");
			%>
			<jsp:forward page="/homePage"/>
			<%
		}
		CartItem mcartItem=cart.getCartItem(id);//判断书是否还有
		if((cartItem.getAmount()==0)){
			session.setAttribute("error", "您选的书的数量已达上限");
			%>
			<jsp:forward page="/homePage"/>
			<%
		} 
		if(mcartItem!=null&&mcartItem.getAmount()==cartItem.getAmount()){
			session.setAttribute("error", "您选的书的数量已达上限");
			%>
			<jsp:forward page="/homePage"/>
			<%
		}
		//加入书
		CartItem item=new CartItem(cartItem.getId(),cartItem.getName(),1,cartItem.getPrice());
		cart.addCartItem(item);
		session.setAttribute("cart", cart);
	}
	if(request.getParameter("isEmpty")!=null){//是否清空购物车
		cart.getCart().removeAll(cart.getCart());
	}
	out.println("余额:"+userLogin.getBalance()+"元<br/>");
	if(!cart.getCart().isEmpty()){//显示购物车
		ArrayList<CartItem> mcart=cart.getCart();
		Iterator<CartItem> it=mcart.iterator();
		CartItem mItem = null;
		out.print("<table>");
		out.println("<tr><th align='left'>ID</th><th align='left'>书名</th><th align='left'>数量</th><th align='left'>价格</th><tr/>");
		while (it.hasNext()) {
			mItem = it.next();
			out.print("<tr><td align='left'>"+mItem.getId()+"</td>");
			out.print("<td align='left'>"+mItem.getName()+"</td>");
			out.print("<td align='left'>"+mItem.getAmount()+"</td>");
			out.print("<td align='left'>"+mItem.getPrice()+"</td></tr>");
		}
		out.println("<tr><td>总计:</td><td>"+cart.getTotal()+"</td></tr></table><br/>");
		out.println("<a href='/BookStoreSystem/pay.jsp'>支付</a><br/>");
		out.println("<a href='/BookStoreSystem/showShoppingCart.jsp?isEmpty=1'>清空空购物车</a><br/>");
	}else{
		out.println("您还没买任何书<br/>");
	}
%>
<a href="/BookStoreSystem/homePage">继续购书</a>
<%
}else{//未登录
	request.getRequestDispatcher("/homePage").forward(request,response);
}
%>
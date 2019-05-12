<%@ page contentType="text/html;charset=gb2312" pageEncoding="GBK" %>
<%@ page import='bean.*' %>
<jsp:useBean id="newBook" class="bean.bookBean" scope="page"/>
<jsp:setProperty property="*" name="newBook"/>
<title>添加图书</title>
<%	if(session.getAttribute("userLogin")!=null){
	if(request.getParameter("submit")!=null){
		newBook.setName(new String(newBook.getName().getBytes("ISO-8859-1"),"gb2312"));
		if(newBook.validate()){//数据检查
			DBUtil dbUtil=DBUtil.getInstance();
			if(dbUtil.addBooks(newBook)>0)
				request.getRequestDispatcher("/homePage").forward(request,response);
			else
				out.println("添加失败");
		}
	}
}else{
	request.getRequestDispatcher("/homePage").forward(request,response);
}
%>
<form action="addBooks.jsp" method='post'>
	<table>
	<tr><td>书名:</td>
		<td><input type="text" name="name" value='<%=newBook.getName()%>'></td>
		<td><font color="red"><%=newBook.getErrorMsg("name")%></font></td>
	</tr>
	<tr><td>数量:</td>
		<td><input type="text" name="amount" value='<%=newBook.getAmount()%>'></td>
		<td><font color="red"><%=newBook.getErrorMsg("amount")%></font></td>
	</tr>
	<tr><td>价格:</td>
		<td><input type="text" name="price" value='<%=newBook.getPrice()%>'></td>
		<td><font color="red"><%=newBook.getErrorMsg("price")%></font></td>
	</tr>
	<tr><td><input type="submit" name="submit" value="添加"></td>
	</tr>
	</table>
</form>
<a href="/BookStoreSystem/homePage">返回</a>
<%@ page contentType="text/html;charset=gb2312" pageEncoding="GBK" %>
<%@ page import='bean.*' %>
<jsp:useBean id="newBook" class="bean.bookBean" scope="page"/>
<jsp:setProperty property="*" name="newBook"/>
<title>���ͼ��</title>
<%	if(session.getAttribute("userLogin")!=null){
	if(request.getParameter("submit")!=null){
		newBook.setName(new String(newBook.getName().getBytes("ISO-8859-1"),"gb2312"));
		if(newBook.validate()){//���ݼ��
			DBUtil dbUtil=DBUtil.getInstance();
			if(dbUtil.addBooks(newBook)>0)
				request.getRequestDispatcher("/homePage").forward(request,response);
			else
				out.println("���ʧ��");
		}
	}
}else{
	request.getRequestDispatcher("/homePage").forward(request,response);
}
%>
<form action="addBooks.jsp" method='post'>
	<table>
	<tr><td>����:</td>
		<td><input type="text" name="name" value='<%=newBook.getName()%>'></td>
		<td><font color="red"><%=newBook.getErrorMsg("name")%></font></td>
	</tr>
	<tr><td>����:</td>
		<td><input type="text" name="amount" value='<%=newBook.getAmount()%>'></td>
		<td><font color="red"><%=newBook.getErrorMsg("amount")%></font></td>
	</tr>
	<tr><td>�۸�:</td>
		<td><input type="text" name="price" value='<%=newBook.getPrice()%>'></td>
		<td><font color="red"><%=newBook.getErrorMsg("price")%></font></td>
	</tr>
	<tr><td><input type="submit" name="submit" value="���"></td>
	</tr>
	</table>
</form>
<a href="/BookStoreSystem/homePage">����</a>
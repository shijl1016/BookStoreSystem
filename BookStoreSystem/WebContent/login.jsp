<%@ page contentType="text/html;charset=GB2312" pageEncoding="GB2312" %>
<%@ page import="bean.*" %>
<%@ page import="java.io.*" %>
<title>��¼</title>
<jsp:useBean id="loginForm"  class="bean.userBeanLogin" scope="page"/>
<jsp:setProperty name="loginForm" property="*" />
<jsp:useBean id="userLogin" class="bean.userBean" scope="session"/>
<jsp:setProperty name="userLogin" property="*"/>
<%
if(request.getParameter("submit") != null){
	if(loginForm.validate()){//���ݼ��
		DBUtil dbUtil=DBUtil.getInstance();
		switch(dbUtil.getUser(userLogin)){
			case 0://��֤�ɹ�
				request.getRequestDispatcher("homePage").forward(request, response);
				break;
			case 1://�û�������
				loginForm.setErrorMsg("name","there is no such a user");
				break;
			case 2://�������
				loginForm.setErrorMsg("password", "error password");
				break;
		}
	}
}
%>
<form action="login.jsp" method="post">
������<input type="text" name="name" value='<%=loginForm.getName()%>'>
	<font color="red"><%=loginForm.getErrorMsg("name")%></font><br>
���룺<input type="password" name="password" value='<%=loginForm.getPassword()%>'>
	<font color="red"><%=loginForm.getErrorMsg("password")%></font><br>
<input type="submit" name="submit" value="��¼">
</form>
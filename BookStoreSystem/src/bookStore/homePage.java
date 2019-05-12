package bookStore;
import bean.*;
import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
public class homePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public homePage() {super(); }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println("<title>��ӭ�����������</title>");
		DBUtil dbUtil=DBUtil.getInstance();
		dbUtil.displayBooks(out);//��ʾͼ����Ϣ
		HttpSession sess=request.getSession();
		userBean userLogin=(userBean)sess.getAttribute("userLogin");
		if(userLogin!=null&&userLogin.getName()!=null) {//�û��ѵ�¼
			out.println("��,����,"+userLogin.getName()+"<br/>");
			if(userLogin.getSign()==1){//����Ա
				out.println("<a href='/BookStoreSystem/addBooks.jsp'>���ͼ��</a><br/>");
			}else {//��ͨ����
				String error=(String)sess.getAttribute("error");
				if(error!=null&&error!="") {
					out.println(sess.getAttribute("error")+"<br/>");
				}
				out.println("<form action='showShoppingCart.jsp' method='post'>"
						+ "<table><td>ID:</td><td><input type='text' name='ID'></td>"
						+ "<td><input type='submit' name='submit' value='���빺�ﳵ'></td></table><form><br/>");
				out.println("<a href='showShoppingCart.jsp'>�鿴���ﳵ</a><br/>");
			}
			out.println("<a href='/BookStoreSystem/loginOut.jsp'>�˳�</a>");
		}else {//�û�δ��¼
			out.println("<a href='/BookStoreSystem/login.jsp'>��¼</a>");
		}	
	}
}

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
		out.println("<title>欢迎光临网上书店</title>");
		DBUtil dbUtil=DBUtil.getInstance();
		dbUtil.displayBooks(out);//显示图书信息
		HttpSession sess=request.getSession();
		userBean userLogin=(userBean)sess.getAttribute("userLogin");
		if(userLogin!=null&&userLogin.getName()!=null) {//用户已登录
			out.println("嗨,您好,"+userLogin.getName()+"<br/>");
			if(userLogin.getSign()==1){//管理员
				out.println("<a href='/BookStoreSystem/addBooks.jsp'>添加图书</a><br/>");
			}else {//普通读者
				String error=(String)sess.getAttribute("error");
				if(error!=null&&error!="") {
					out.println(sess.getAttribute("error")+"<br/>");
				}
				out.println("<form action='showShoppingCart.jsp' method='post'>"
						+ "<table><td>ID:</td><td><input type='text' name='ID'></td>"
						+ "<td><input type='submit' name='submit' value='加入购物车'></td></table><form><br/>");
				out.println("<a href='showShoppingCart.jsp'>查看购物车</a><br/>");
			}
			out.println("<a href='/BookStoreSystem/loginOut.jsp'>退出</a>");
		}else {//用户未登录
			out.println("<a href='/BookStoreSystem/login.jsp'>登录</a>");
		}	
	}
}

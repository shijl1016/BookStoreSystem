package bean;
import bean.userBean;
import bookStore.*;
import java.sql.*;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;
import java.util.*;
public class DBUtil {
	static private DBUtil instance=new DBUtil();
	private String username="root";
	private String password="971016";
	private String driver="com.mysql.jdbc.Driver";
	private String DBURL="jdbc:mysql://localhost:3306/bookstoresystem?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private Connection conn=null;
	private Statement stat=null;
	private ResultSet rs=null;
	private String sql="";
	public static DBUtil getInstance() {
		return instance;
	}
	public DBUtil() {
		try {
			Class.forName(driver);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public int getUser(userBean userLogin) {/*0：正常      1：没有该用户    2：密码错误*/
		int tempResult=1;
		try {
			conn=DriverManager.getConnection( this.DBURL, username, password);
			stat=conn.createStatement();
			sql="select name,password,balance from reader";
			rs=stat.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString("name").equals(userLogin.getName())) {
					if(rs.getString("password").equals(userLogin.getPassword())) {
						tempResult=0;
						userLogin.setBalance(rs.getDouble("balance"));
						userLogin.setSign(2);
					}else {
						tempResult=2;
					}
					break;
				}
			}
			if(tempResult==1) {
				sql="select name,password from manager";
				rs=stat.executeQuery(sql);
				while(rs.next()) {
					if(rs.getString("name").equals(userLogin.getName())) {
						if(rs.getString("password").equals(userLogin.getPassword())) {
							tempResult=0;
							userLogin.setSign(1);
						}else {
							tempResult=2;
						}
						break;
					}
				}
			}
			rs.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tempResult;
	}
	public int updateBooks(ShoppingCart cart) {
		int result=0;
		try {
			conn=DriverManager.getConnection( this.DBURL, username, password);
			stat=conn.createStatement();
			Iterator<CartItem> it=cart.getCart().iterator();
			CartItem Item = null;
			while (it.hasNext()) {
				Item = it.next();
				sql="update book set amount=amount-"+Item.getAmount()+" where id="+Item.getId();
				result=stat.executeUpdate(sql);
				if(result==0) {
					break;
				}
			}
			if(result!=0) {
				cart.getCart().removeAll(cart.getCart());
			}
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return result;
	}
	public CartItem getBookInfo(int id) {
		CartItem cart=null;
		try {
			conn=DriverManager.getConnection( this.DBURL, username, password);
			stat=conn.createStatement();
			sql="select * from book where id="+id;
			rs=stat.executeQuery(sql);
			if(rs.first()) {
				cart=new CartItem(rs.getInt("id"),rs.getString("name"),rs.getInt("amount"),rs.getDouble("price"));	
			}
			rs.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cart;
	}
	public int addBooks(bookBean newBook) {
		int tempResult=0;
		try {
			conn=DriverManager.getConnection( this.DBURL, username, password);
			stat=conn.createStatement();
			sql="select * from book where name='"+newBook.getName()+"'";
			rs=stat.executeQuery(sql);
			if(rs.first()) {
				newBook.setMAmount(rs.getInt("amount")+newBook.getMAmount());
				sql="update book set amount="+newBook.getMAmount()+",price="+newBook.getMPrice()+"where name='"+newBook.getName()+"'";
				rs.close();
			}else {
				sql="insert into book(name,amount,price) values('"+newBook.getName()+"','"+newBook.getMAmount()+"','"+newBook.getMPrice()+"')";
			}
			tempResult=stat.executeUpdate(sql);
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tempResult;
	}
	public int updateUser(userBean userLogin) {
		int tempResult=0;
		try {
			conn=DriverManager.getConnection( this.DBURL, username, password);
			stat=conn.createStatement();
			sql="update reader set balance="+userLogin.getBalance()+"where name='"+userLogin.getName()+"'";
			tempResult=stat.executeUpdate(sql);
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tempResult;
	}
	public void displayBooks(PrintWriter out) throws IOException {
		try {
			conn=DriverManager.getConnection( this.DBURL, username, password);
			stat=conn.createStatement();
			sql="select * from book";
			rs=stat.executeQuery(sql);
			out.print("<table>");
			out.println("<tr><th align='left'>ID</th><th align='left'>书名</th><th align='left'>数量</th><th align='left'>价格</th><tr/>");
			while(rs.next()) {
				out.print("<tr><td align='left'>"+rs.getInt("id")+"</td>");
				out.print("<td align='left'>"+rs.getString("name")+"</td>");
				out.print("<td align='left'>"+rs.getInt("amount")+"</td>");
				out.print("<td align='left'>"+rs.getFloat("price")+"</td></tr>");
			}
			out.print("</table>");
			rs.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

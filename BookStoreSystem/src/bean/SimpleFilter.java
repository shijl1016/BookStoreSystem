package bean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebFilter("/SimpleFilter")
public class SimpleFilter implements Filter {
	private String charSet; 
    public SimpleFilter() {
        // TODO Auto-generated constructor stub
    }
	public void destroy() {
		// TODO Auto-generated method stub
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request ;		// 向下转型
		HttpSession ses = req.getSession() ;			// 取得session
		String path=req.getRequestURI();
		response.getWriter().println(path);
		if(path.indexOf("homePage")!=-1) {
			request.getRequestDispatcher("homePage").forward(request, response);
		}else if (ses.getAttribute("userLogin")!= null) {		// 判断是否登陆
			chain.doFilter(request, response);		// 传递请求
		} else {// 没有登陆
			request.getRequestDispatcher("/login.jsp").forward(request, response);	// 跳转到登陆页
		}
	}
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.charSet = fConfig.getInitParameter("charSet"); 
	}
}

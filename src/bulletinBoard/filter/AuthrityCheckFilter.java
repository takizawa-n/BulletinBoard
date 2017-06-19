package bulletinBoard.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import bulletinBoard.beans.User;


@WebFilter(filterName="AuthrityCheckFilter", urlPatterns={"/manage","/signup","/settings"})
public class AuthrityCheckFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		System.out.println(((HttpServletRequest) request).getRequestURL());
		System.out.println("AuthrityCheckFilter");
		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
		try {
			List<String> messages = new ArrayList<String>();
				HttpSession session = ((HttpServletRequest) request).getSession();
				User user = (User) req.getSession().getAttribute("loginUser");
				System.out.println(user);
				if (user.getSectionId() != 1) {
					messages.add("利用制限のかかったエリアに入ろうとしました。このアカウントでは利用できません。");
					session.setAttribute("errorMessages", messages);
					((HttpServletResponse) response).sendRedirect("./");
					return;
				}
			chain.doFilter(request, response);

		} catch (ServletException se) {
		} catch (IOException e) {
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}
}
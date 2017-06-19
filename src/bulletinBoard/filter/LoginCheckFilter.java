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
import bulletinBoard.service.UserService;


@WebFilter(filterName="LoginCheckFilter", urlPatterns="/*")
public class LoginCheckFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		System.out.println(((HttpServletRequest) request).getRequestURL());
		try {

			System.out.println("LoginCheckFilterのdoFilterにきたよ");//■

			HttpSession session = ((HttpServletRequest) request).getSession();
			List<String> messages = new ArrayList<String>();
			User user = (User) session.getAttribute("loginUser");



			String path = ((HttpServletRequest) request).getServletPath();
			System.out.println(path + "に飛びたいな～？");//
//			String uri = ((HttpServletRequest) request).getRequestURI();
//			System.out.println(uri);


			// ログイン画面じゃない場合、以下のif内の処理
			if (!path.contains("/login") && !path.contains("/css")) {
				System.out.println("HomeServletに飛ぶ前の処理だよ。（LoginCheckFilter / doFilter）");//■

				// login画面以外にアクセスしようとした場合、以下の処理
				if (user == null) {
					System.out.println("Userがヌルヌル");
					messages.add("有効なアカウントでログインしてくださいね");
					session.setAttribute("errorMessages", messages);
					((HttpServletResponse) response).sendRedirect("login");
					return;
				}

				// ログインユーザーのuserIdをつかって、DBから最新の情報をGETし、loginUserの情報を更新する。
				User updatedLoginUser = new UserService().getUser(user.getId());

				if (updatedLoginUser == null) {
					messages.add(user.getName() + "さんのアカウントは削除されました");
					session.setAttribute("errorMessages", messages);
					((HttpServletResponse) response).sendRedirect("login");
					return;
				}

				if (updatedLoginUser.getIsWorking() != 1) {
					messages.add(updatedLoginUser.getName() + "さんのアカウントは停止されています");
					session.setAttribute("errorMessages", messages);
					((HttpServletResponse) response).sendRedirect("login");
					return;
				}
//				session.removeAttribute("loginUser");
				session.setAttribute("loginUser", updatedLoginUser);

			}

			System.out.println("doFilterのchain.doFilter前処理完了（LoginCheckFilter）");//■
			chain.doFilter(request, response);

			System.out.println("doFilterのchain.doFilter後処理完了（LoginCheckFilter）");//■


		} catch (ServletException se) {
		} catch (IOException e) {
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}
}
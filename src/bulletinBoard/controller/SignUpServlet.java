package bulletinBoard.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bulletinBoard.beans.Branches;
import bulletinBoard.beans.Sections;
import bulletinBoard.beans.Users;
import bulletinBoard.service.BranchService;
import bulletinBoard.service.SectionService;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<Branches> branches = new BranchService().getBranches();
		List<Sections> sections = new SectionService().getSections();

		System.out.println(branches.size());
		System.out.println(sections.size());

		request.setAttribute("branches", branches);
		request.setAttribute("sections", sections);

		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		Users user = new Users();
		user.setLoginId(request.getParameter("login_id"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
		user.setSectionId(Integer.parseInt(request.getParameter("section_id")));
		user.setIsWorking(1);

		if (isValid(request, messages) == true) {

			new UserService().register(user);
			response.sendRedirect("./");
		} else {
			List<Branches> branches = new BranchService().getBranches();
			List<Sections> sections = new SectionService().getSections();

			System.out.println(branches.size());
			System.out.println(sections.size());

			request.setAttribute("branches", branches);
			request.setAttribute("sections", sections);

			session.setAttribute("errorMessages", messages);
			request.setAttribute("user", user);
			request.getRequestDispatcher("signup.jsp").forward(request, response);

		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		} else if (loginId.length() < 6 || 20 < loginId.length()) {
			messages.add("ログインIDは6～20文字以内で入力してください");
		}
		if (password.length() < 6 || 255 < password.length()) {
			messages.add("パスワードは6～255文字以内で入力してください");
		}
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}
		if (StringUtils.isEmpty(password2) == true) {
			messages.add("パスワード（確認用）を入力してください");
		}
		if ( !password.equals(password2)) {
			messages.add("パスワードとパスワード（確認用）は同じものを入力してください");
		}
		if ( name.length() >= 10) {
			messages.add("名前は10文字以下で入力してください");
		}
		// TODO アカウントが既に利用されていないか、メールアドレスが既に登録されていないかなどの確認も必要
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}

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

import bulletinBoard.beans.Message;
import bulletinBoard.beans.User;
import bulletinBoard.service.MessageService;

@WebServlet(urlPatterns = { "/newPost" })
public class NewPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getRequestDispatcher("/newPost.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		User user = (User) session.getAttribute("loginUser");
		Message post = new Message();
		post.setTitle(request.getParameter("title"));
		post.setText(request.getParameter("text"));
		post.setCategory(request.getParameter("category"));
		post.setUserId(user.getId());

		if (isValid(request, messages) == true) {
			new MessageService().register(post);
			response.sendRedirect("./");

		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("post", post);
			request.getRequestDispatcher("/newPost.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String title = request.getParameter("title");
		String text = request.getParameter("text");
		String category = request.getParameter("category");

		if (StringUtils.isEmpty(title) == true) {
			messages.add("件名を入力してください");
		}
		if (50 < title.length()) {
			messages.add("50文字以下で入力してください");
		}
		if (StringUtils.isEmpty(text) == true) {
			messages.add("本文を入力してください");
		}
		if (1000 < text.length()) {
			messages.add("1000文字以下で入力してください");
		}
		if (StringUtils.isEmpty(category) == true) {
			messages.add("カテゴリーを入力してください");
		}
		if (10 < category.length()) {
			messages.add("10文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}

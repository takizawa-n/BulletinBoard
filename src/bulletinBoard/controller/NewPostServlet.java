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

import bulletinBoard.beans.Messages;
import bulletinBoard.beans.Users;
import bulletinBoard.beans.UsersComments;
import bulletinBoard.beans.UsersMessages;
import bulletinBoard.service.CommentService;
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
		List<String> errorMessages = new ArrayList<String>();
		Users loginUser = (Users) session.getAttribute("loginUser");
		Messages post = new Messages();
		post.setTitle(request.getParameter("title"));
		post.setText(request.getParameter("text"));
		post.setCategory(request.getParameter("category"));
		post.setUserId(loginUser.getId());



		if (isValid(request, errorMessages) == true) {
			new MessageService().register(post);

			List<UsersMessages> messages = new MessageService().getMessages();
			List<UsersComments> comments = new CommentService().getComments();
			request.setAttribute("messages", messages);
			request.setAttribute("comments", comments);

			request.getRequestDispatcher("/home.jsp").forward(request, response);

		} else {
			session.setAttribute("errorMessages", errorMessages);
			request.setAttribute("post", post);
			request.getRequestDispatcher("/newPost.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> errorMessages) {

		String title = request.getParameter("title");
		String text = request.getParameter("text");
		String category = request.getParameter("category");

		if (StringUtils.isEmpty(title) == true) {
			errorMessages.add("件名を入力してください");
		}
		if (50 < title.length()) {
			errorMessages.add("50文字以下で入力してください");
		}
		if (StringUtils.isEmpty(text) == true) {
			errorMessages.add("本文を入力してください");
		}
		if (1000 < text.length()) {
			errorMessages.add("1000文字以下で入力してください");
		}
		if (StringUtils.isEmpty(category) == true) {
			errorMessages.add("カテゴリーを入力してください");
		}
		if (10 < category.length()) {
			errorMessages.add("10文字以下で入力してください");
		}
		if (errorMessages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}

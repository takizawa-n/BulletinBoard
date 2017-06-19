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

import bulletinBoard.beans.Comment;
import bulletinBoard.beans.User;
import bulletinBoard.service.CommentService;

@WebServlet(urlPatterns = { "/comment" })
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {


		HttpSession session = request.getSession();
		List<String> errorMessages = new ArrayList<String>();
		User user = (User) session.getAttribute("loginUser");
		Comment comment = new Comment();
		comment.setPostId(Integer.parseInt(request.getParameter("postId")));
		comment.setText(request.getParameter("text"));
		comment.setUserId(user.getId());

		if (isValid(request, errorMessages) == true) {
			new CommentService().register(comment);
			response.sendRedirect("./");

		} else {
			//エラーメッセージがあった場合、ホームに戻す
			session.setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> errorMessages) {

		String text = request.getParameter("text");

		if (StringUtils.isBlank(text) == true) {
			errorMessages.add("コメントを入力してください");
		}
		if (500 < text.length()) {
			errorMessages.add("コメントは500文字以下で入力してください");
		}
		if (errorMessages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}

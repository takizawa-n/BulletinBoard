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

import bulletinBoard.service.CommentService;

@WebServlet(urlPatterns = { "/deleteComment" })
public class DeleteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<String> deleteMessage = new ArrayList<String>();
		HttpSession session = request.getSession();

		int id = Integer.parseInt(request.getParameter("id"));
		new CommentService().delete(id);
		deleteMessage.add("コメントを1件削除しました");
		session.setAttribute("deleteMessage", deleteMessage);
		response.sendRedirect("./");
	}

}

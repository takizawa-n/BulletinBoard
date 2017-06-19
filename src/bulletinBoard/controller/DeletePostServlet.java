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

import bulletinBoard.service.PostService;

@WebServlet(urlPatterns = { "/deletePost" })
public class DeletePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<String> deleteMessage = new ArrayList<String>();
		HttpSession session = request.getSession();

		int id = Integer.parseInt(request.getParameter("id"));
		new PostService().delete(id);
		deleteMessage.add("投稿を1件削除しました");
		session.setAttribute("deleteMessage", deleteMessage);
		response.sendRedirect("./");
	}

}

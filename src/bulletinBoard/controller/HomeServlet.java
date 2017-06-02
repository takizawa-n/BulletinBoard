package bulletinBoard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.UsersComments;
import bulletinBoard.beans.UsersMessages;
import bulletinBoard.service.CommentService;
import bulletinBoard.service.MessageService;


	@WebServlet(urlPatterns = { "/index.jsp" })
	public class HomeServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;

		@Override
		protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException {

			List<UsersMessages> messages = new MessageService().getMessages();
			List<UsersComments> comments = new CommentService().getComments();

			System.out.println(messages.size());
			System.out.println(comments.size());
			System.out.println(comments.get(1).getMessageId());

			request.setAttribute("messages", messages);
			request.setAttribute("comments", comments);

			request.getRequestDispatcher("/home.jsp").forward(request, response);

		}

	}



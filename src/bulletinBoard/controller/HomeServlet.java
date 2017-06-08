package bulletinBoard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.Categories;
import bulletinBoard.beans.UsersComments;
import bulletinBoard.beans.UsersMessages;
import bulletinBoard.service.CategoryService;
import bulletinBoard.service.CommentService;
import bulletinBoard.service.MessageService;


	@WebServlet(urlPatterns = { "/index.jsp" })
	public class HomeServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;

		@Override
		protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException {

			System.out.println("HomeServletのdoGetにきたよ");//■


			List<UsersMessages> messages = new MessageService().getMessages();
			List<UsersComments> comments = new CommentService().getComments();
			List<Categories> categories = new CategoryService().getCategories();


			System.out.println(messages.size());//■
			System.out.println(comments.size());//■
			System.out.println(categories.size());//■

			request.setAttribute("messages", messages);
			request.setAttribute("comments", comments);
			request.setAttribute("categories", categories);


			System.out.println("HomeServletからhome.jspに飛ぶよ");//■

			request.getRequestDispatcher("home.jsp").forward(request, response);

		}

	}



package bulletinBoard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.Users;
import bulletinBoard.beans.UsersMessages;
import bulletinBoard.service.MessageService;


	@WebServlet(urlPatterns = { "/index.jsp" })
	public class HomeServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;

		@Override
		protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException {

			//●この一行は必要か。
			Users user = (Users) request.getSession().getAttribute("loginUser");

			List<UsersMessages> messages = new MessageService().getMessage();

			request.setAttribute("messages", messages);

			request.getRequestDispatcher("/home.jsp").forward(request, response);

			}

	}

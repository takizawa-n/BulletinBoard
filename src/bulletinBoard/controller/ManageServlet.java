package bulletinBoard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bulletinBoard.beans.Users;
import bulletinBoard.service.UserService;


@WebServlet(urlPatterns = { "/manage" })
public class ManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<Users> users = new UserService().getUsers();

		System.out.println(users.size());

		request.setAttribute("users", users);
		request.getRequestDispatcher("/manage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		Users editUser = getEditUser(request);
			response.sendRedirect("manage");
	}


	private Users getEditUser(HttpServletRequest request)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		Users editUser = (Users) session.getAttribute("user");

		int isWorking = Integer.parseInt(request.getParameter("user.isWorking"));
		int changeWorking = 0;
		if (isWorking == 1){
			changeWorking = 0;
		}
		if (isWorking == 0){
			changeWorking = 1;
		}
			editUser.setIsWorking(changeWorking);
			return editUser;
	}

}

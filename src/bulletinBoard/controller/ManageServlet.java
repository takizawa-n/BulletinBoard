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

import bulletinBoard.beans.Users;
import bulletinBoard.exception.NoRowsUpdatedRuntimeException;
import bulletinBoard.service.UserService;


@WebServlet(urlPatterns = { "/manage" })
public class ManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<Users> users = new UserService().getUsers();

		System.out.println(users.size());//■will be deleted

		request.setAttribute("users", users);
		request.getRequestDispatcher("/manage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		int userId = (Integer.parseInt(request.getParameter("userId")));
		int isWorking = (Integer.parseInt(request.getParameter("is_woking")));
		if(isWorking ==1){
			isWorking = 0;
		}
		if(isWorking ==0){
			isWorking = 1;
		}
		//ここで、userIdを引数にデータを取り出す。
		//取り出したデータのis_workingに上のisWorkingをいれたものを、「editUser」に保管する。あとはOK
		Users editUser = new UserService().getUser(userId);
		editUser.setIsWorking(isWorking);

		try {
			new UserService().update(editUser);
		} catch (NoRowsUpdatedRuntimeException e) {
			session.removeAttribute("editUser");
			messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
			session.setAttribute("errorMessages", messages);
		}
		response.sendRedirect("manage");
	}
}


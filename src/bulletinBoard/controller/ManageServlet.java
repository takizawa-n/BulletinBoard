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

import bulletinBoard.beans.Branch;
import bulletinBoard.beans.Section;
import bulletinBoard.beans.User;
import bulletinBoard.exception.NoRowsUpdatedRuntimeException;
import bulletinBoard.service.BranchService;
import bulletinBoard.service.SectionService;
import bulletinBoard.service.UserService;


@WebServlet(urlPatterns = { "/manage" })
public class ManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<User> users = new UserService().getUsers();
		List<Branch> branches = new BranchService().getBranches();
		List<Section> sections = new SectionService().getSections();

		System.out.println(users.size());//■

		request.setAttribute("users", users);
		request.setAttribute("branches", branches);
		request.setAttribute("sections", sections);
		request.getRequestDispatcher("/manage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		//もしもログインユーザーが自分を削除したり、アカウントの停止復活しようとしていたら、エラーを出す。

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		int userId = (Integer.parseInt(request.getParameter("user_id")));
		int isWorking = (Integer.parseInt(request.getParameter("is_working")));

		try {
			new UserService().updateIsWorking(userId, isWorking);
		} catch (NoRowsUpdatedRuntimeException e) {
			session.removeAttribute("editUser");
			messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
			session.setAttribute("errorMessages", messages);
		}
		response.sendRedirect("manage");
	}
}


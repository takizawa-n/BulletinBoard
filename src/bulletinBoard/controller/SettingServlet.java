package bulletinBoard.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bulletinBoard.beans.Branch;
import bulletinBoard.beans.Section;
import bulletinBoard.beans.User;
import bulletinBoard.exception.NoRowsUpdatedRuntimeException;
import bulletinBoard.service.BranchService;
import bulletinBoard.service.SectionService;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/setting" })
@MultipartConfig(maxFileSize = 100000)
public class SettingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();


		//idが数字じゃなかったら、manage画面に戻す
		String id = request.getParameter("userId");
		System.out.println(id);
		if (id == null) {
			messages.add("編集するユーザーのログインIDを指定してください");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("manage");
			return;
		}
		if (!id.matches("^[0-9]+$")) {
			messages.add("入力されたログインIDは存在しません");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("manage");
			return;
		}

		//idがDBに存在しなかったら、manage画面に戻す
		int userId = Integer.parseInt(id);
		User editUser = new UserService().getUser(userId);
		if (editUser == null)  {
			messages.add("入力されたログインIDは存在しません");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("manage");
			return;
		}

		// editUserには、該当のuserIdのデータが入っている。
		request.setAttribute("editUser", editUser);

		List<Branch> branches = new BranchService().getBranches();
		List<Section> sections = new SectionService().getSections();
		System.out.println(branches.size());// ■
		System.out.println(sections.size());// ■

		request.setAttribute("branches", branches);
		request.setAttribute("sections", sections);

		request.getRequestDispatcher("/setting.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		//もしもログインユーザーが自分の役職や支店名を変更しようとしていたら、エラーを出す。
		//（画面上は入力できないようにしてある）

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		List<Branch> branches = new BranchService().getBranches();
		List<Section> sections = new SectionService().getSections();
		request.setAttribute("branches", branches);
		request.setAttribute("sections", sections);


		User loginUser = (User) session.getAttribute("loginUser");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");

		User editUser = getEditUser(request);

			if (isValid(request, messages) == true) {

				//パスワードが両方に入力されていた場合、新パスワードをセットする。
				if (!StringUtils.isBlank(password) && !StringUtils.isBlank(password2)) {
					editUser.setPassword(password);
				}
				try {
					new UserService().update(editUser);
				} catch (NoRowsUpdatedRuntimeException e) {
					messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
					session.setAttribute("errorMessages", messages);
					request.setAttribute("editUser", editUser);
					request.getRequestDispatcher("settings.jsp").forward(request, response);
					return;
				}

				//もしも、ログインユーザーと編集しているユーザーが同じだったら、sessionのデータも書き換える。
				if (loginUser.getId() == editUser.getId()) {
					session.removeAttribute("loginUser");
					session.setAttribute("loginUser", editUser);
				}
				messages.add(editUser.getName() + "さんのデータを編集しました");
				session.setAttribute("updateMessages", messages);

				List<User> users = new UserService().getUsers();

				request.setAttribute("users", users);
				request.getRequestDispatcher("manage.jsp").forward(request, response);

			} else {

				session.setAttribute("errorMessages", messages);
				request.setAttribute("editUser", editUser);
				request.getRequestDispatcher("setting.jsp").forward(request, response);

			}
	}

	private User getEditUser(HttpServletRequest request) throws IOException, ServletException {


		//IDは不変のものなので、ここで必ず、データの情報がでてくる。
		int userId = Integer.parseInt(request.getParameter("id"));
		UserService userService = new UserService();

		//ここで、編集前のユーザーの情報を取得
		User editUser = userService.getUser(userId);
		System.out.println("getUser内のid = " + userId);//■

		editUser.setId(userId);
		editUser.setLoginId(request.getParameter("loginId"));
		editUser.setName(request.getParameter("name"));
		editUser.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
		editUser.setSectionId(Integer.parseInt(request.getParameter("section_id")));
		return editUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		int userId = Integer.parseInt(request.getParameter("id"));
		UserService userService = new UserService();
		User userSaved = userService.getUser(userId);

		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");
		boolean isUsed = new UserService().IsUsed(loginId);


		if (StringUtils.isBlank(loginId) == true) {
			messages.add("ログインIDを入力してください");
		} else if (loginId.length() < 6 || 20 < loginId.length()) {
			messages.add("ログインIDは6～20文字以内で入力してください");

		//Idが使われていない、かつ、もともと入力したものとは違う場合、以下の処理
		} else if ((isUsed == true) && !loginId.equals(userSaved.getLoginId())) {
			messages.add("このログインIDはすでに利用されています");
		}

		if (!StringUtils.isBlank(password) || !StringUtils.isBlank(password2)){

			if (StringUtils.isBlank(password) == true) {
				messages.add("パスワードを入力してください");
			} else if (StringUtils.isBlank(password2) == true) {
				messages.add("パスワード（確認用)を入力してください");
			} else if (password.length() < 6 || 255 < password.length()) {
				messages.add("パスワードは6～255文字以内で入力してください");
			} else if (!password.equals(password2)) {
				messages.add("パスワードとパスワード（確認用）は同じものを入力してください");
			}
		}

		if (StringUtils.isBlank(name) == true) {
			messages.add("名称を入力してください");
		} else if (name.length() > 10) {
			messages.add("名称は10文字以内で入力してください");
		}
		// TODO アカウントが既に利用されていないか、メールアドレスが既に登録されていないかなどの確認も必要
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}

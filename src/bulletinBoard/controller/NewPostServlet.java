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

import bulletinBoard.beans.Categories;
import bulletinBoard.beans.Messages;
import bulletinBoard.beans.Users;
import bulletinBoard.beans.UsersComments;
import bulletinBoard.beans.UsersMessages;
import bulletinBoard.service.CategoryService;
import bulletinBoard.service.CommentService;
import bulletinBoard.service.MessageService;

@WebServlet(urlPatterns = { "/newPost" })
public class NewPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<Categories> categories = new CategoryService().getCategories();
		System.out.println(categories.size());//■

		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/newPost.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> errorMessages = new ArrayList<String>();
		Users loginUser = (Users) session.getAttribute("loginUser");

		Messages post = new Messages();
		post.setTitle(request.getParameter("title"));
		post.setText(request.getParameter("text"));
		post.setUserId(loginUser.getId());



		if (isValid(request, errorMessages) == true) {
			String category = request.getParameter("category"); //既存のカテゴリーから選択
			String newCategory = request.getParameter("newCategory");//新規のカテゴリーから選択
			System.out.println(newCategory);

			//もし、既存のカテゴリーが選択されていて、かつ新規のカテゴリーも入力されていたら以下の処理
			if(!category.equals("new") && !newCategory.isEmpty()) {
				post.setCategory(request.getParameter("newCategory"));
				List<Categories> categories = new CategoryService().getCategories();
				request.setAttribute("categories", categories);
				request.setAttribute("post", post);
				session.setAttribute("errorMessages", errorMessages);
				request.getRequestDispatcher("/newPost.jsp").forward(request, response);
				return;
			}

			if(!category.equals("new")){
				post.setCategory(category);//postに既存のカテゴリー
			}

			if(!newCategory.isEmpty()){
				post.setCategory("newCategory");//postに新規のカテゴリー
				Categories insertCategory = new Categories();
				insertCategory.setName(newCategory);
				new CategoryService().register(insertCategory);//新規カテゴリーをDBに登録
			}

			new MessageService().register(post);//postにセットした（新規投稿）情報を登録

			List<UsersMessages> messages = new MessageService().getMessages();
			List<UsersComments> comments = new CommentService().getComments();
			List<Categories> categories = new CategoryService().getCategories();
			request.setAttribute("messages", messages);
			request.setAttribute("comments", comments);
			request.setAttribute("categories", categories);

			request.getRequestDispatcher("/home.jsp").forward(request, response);

		}
	}

	private boolean isValid(HttpServletRequest request, List<String> errorMessages) {

		String title = request.getParameter("title");
		String text = request.getParameter("text");
		String category = request.getParameter("category"); //選択されたカテゴリー
		String newCategory = request.getParameter("newCategory"); //手入力された新規のカテゴリー
		Categories categoryCheck = new CategoryService().getCategory(newCategory); //newカテゴリーがあるか否かをget

		if (StringUtils.isEmpty(title) == true) {
			errorMessages.add("件名を入力してください");
		}
		if (50 < title.length()) {
			errorMessages.add("タイトルは50文字以下で入力してください");
		}
		if (StringUtils.isEmpty(text) == true) {
			errorMessages.add("本文を入力してください");
		} else if (1000 < text.length()) {
			errorMessages.add("1000文字以下で入力してください");
		}
		//もし、カテゴリーが手入力で新規作成されていたら以下の処理
		if(category.equals("new")){

			if (StringUtils.isEmpty(newCategory) == true) {
				errorMessages.add("新しく追加するカテゴリーを入力してください");
			} else if(10 < newCategory.length()) {
				errorMessages.add("カテゴリーは10文字以下で入力してください");
			} else if(categoryCheck != null) {
				errorMessages.add("入力したカテゴリーはすでに存在します");
			}

		//もし、カテゴリーが既存のものから選ばれているのに、新規のカテゴリーも入力されていたら
		} else if (!newCategory.isEmpty()){
			errorMessages.add("カテゴリーはひとつしか設定できません");
		}

		if (errorMessages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}

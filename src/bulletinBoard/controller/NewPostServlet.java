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

import bulletinBoard.beans.Category;
import bulletinBoard.beans.Post;
import bulletinBoard.beans.User;
import bulletinBoard.beans.UserComment;
import bulletinBoard.beans.UserPost;
import bulletinBoard.service.CategoryService;
import bulletinBoard.service.CommentService;
import bulletinBoard.service.PostService;

@WebServlet(urlPatterns = { "/newPost" })
public class NewPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<Category> categories = new CategoryService().getCategories();
		System.out.println(categories.size());//■

		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/newPost.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> errorMessages = new ArrayList<String>();
		User loginUser = (User) session.getAttribute("loginUser");

		Post post = new Post();
		post.setTitle(request.getParameter("title"));
		post.setText(request.getParameter("text"));
		post.setUserId(loginUser.getId());

		String category = request.getParameter("category"); //既存のカテゴリーから選択した値
		String newCategory = request.getParameter("newCategory");//新規のカテゴリーから選択した値
		System.out.println(newCategory);//■


		//以下のifで、入力内容にエラーがある場合
		if (isValid(request, errorMessages) != true) {

			post.setNewCategory(newCategory);
			post.setCategory(category);
			session.setAttribute("errorMessages", errorMessages);

			List<Category> categories = new CategoryService().getCategories();
			request.setAttribute("categories", categories);

			request.setAttribute("post", post);
			request.getRequestDispatcher("/newPost.jsp").forward(request, response);
			return;
		} else {

			//既存のカテゴリーから選んだ場合
			if(!category.equals("new")){
				post.setCategory(category);//postに既存のカテゴリー
			}

			//新規のカテゴリーを入力した場合
			if(StringUtils.isBlank(newCategory) != true){
				post.setCategory(newCategory);//postに新規のカテゴリー

				//新規カテゴリーをDBに登録
				Category insertCategory = new Category();
				insertCategory.setName(newCategory);
				new CategoryService().register(insertCategory);
			}

			new PostService().register(post);//postにセットした（新規投稿）情報を登録

			List<UserPost> posts = new PostService().getPosts();
			List<UserComment> comments = new CommentService().getComments();
			List<Category> categories = new CategoryService().getCategories();
			request.setAttribute("posts", posts);
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
		Category categoryCheck = new CategoryService().getCategory(newCategory); //newカテゴリーがあるか否かをget

		if (StringUtils.isBlank(title) == true) {
			errorMessages.add("タイトルを入力してください");
		}
		if (50 < title.length()) {
			errorMessages.add("タイトルは50文字以下で入力してください");
		}
		if (StringUtils.isBlank(text) == true) {
			errorMessages.add("本文を入力してください");
		} else if (1000 < text.length()) {
			errorMessages.add("1000文字以下で入力してください");
		}
		//もし、カテゴリーが手入力で新規作成されていたら以下の処理
		if(category.equals("new")){

			if (StringUtils.isBlank(newCategory) == true) {
				errorMessages.add("カテゴリーは、新しく追加するカテゴリーを入力するか、既存のカテゴリーから選択してください");
			} else if(10 < newCategory.length()) {
				errorMessages.add("カテゴリーは10文字以下で入力してください");
			} else if(categoryCheck != null) {
				errorMessages.add("入力したカテゴリーはすでに存在します");
			}

		//もし、カテゴリーが既存のものから選ばれているのに、新規のカテゴリーも入力されていたら
		} else if (StringUtils.isBlank(newCategory) != true) {
			errorMessages.add("カテゴリーはひとつしか設定できません");
		}

		if (errorMessages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}

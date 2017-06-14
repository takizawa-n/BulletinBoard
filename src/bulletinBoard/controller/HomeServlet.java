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

import bulletinBoard.beans.Category;
import bulletinBoard.beans.UserComment;
import bulletinBoard.beans.UserPost;
import bulletinBoard.service.CategoryService;
import bulletinBoard.service.CommentService;
import bulletinBoard.service.PostService;


	@WebServlet(urlPatterns = { "/index.jsp" })
	public class HomeServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;

		@Override
		protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException {

			System.out.println("HomeServletのdoGetにきたよ");//■


//			//以前の処理↓（ココカラ）
//
//			List<UsersMessages> messages = new MessageService().getMessages();
//			List<UsersComments> comments = new CommentService().getComments();
//			List<Categories> categories = new CategoryService().getCategories();
//
//			request.setAttribute("messages", messages);
//			request.setAttribute("comments", comments);
//			request.setAttribute("categories", categories);
//
//			System.out.println("HomeServletからhome.jspに飛ぶよ");//■
//
//			request.getRequestDispatcher("home.jsp").forward(request, response);
//
//			//以前の処理（ココマデ）
//

			HttpSession session = request.getSession();
			List<String> searchResult = new ArrayList<String>();

			//絞込み条件内容を受け取って、データベースで条件を指定して送る
			String start = request.getParameter("startDate");
			String end = request.getParameter("endDate");
			String category = request.getParameter("category");

			System.out.println(start);//■
			System.out.println(end);//■
			System.out.println(category);//■

			String startDate = null;
			String endDate = null;
			List<UserPost> posts = null;


			//未入力の場合(すべて表示)
			if(category == null && start == null && end == null) {
				posts = new PostService().getPosts();
				List<UserComment> comments = new CommentService().getComments();
				List<Category> categories = new CategoryService().getCategories();

				request.setAttribute("posts", posts);
				request.setAttribute("comments", comments);
				request.setAttribute("categories", categories);

				request.getRequestDispatcher("home.jsp").forward(request, response);
				return;

			} else {

				//しぼりこみ検索を使ってすべての投稿を取得する場合
				if(category.equals("all") && start.isEmpty() && end.isEmpty()) {

					System.out.println("絞込みALL");//■

					startDate = "1970-01-01 00:00:01";
					endDate = "2037-12-31 23:59:59";
					posts = new PostService().getSortedOnlyDate(startDate, endDate);
					searchResult.add("すべての投稿を表示しています");

				}

				//日付のみでしぼる（カテゴリ指定なしも含む）
				if (category.equals("all") && (!start.isEmpty() || !end.isEmpty()) ) {
					if (!start.isEmpty() && end.isEmpty()) {
						startDate = start;
						endDate = "2037-12-31 23:59:59";
					}
					if (!start.isEmpty() && !end.isEmpty()) {
						startDate = start;
						endDate = end + " 23:59:59";
					}
					if (start.isEmpty() && !end.isEmpty()) {
						startDate = "1970-01-01 00:00:01";
						endDate = end + " 23:59:59";
					}
					posts = new PostService().getSortedOnlyDate(startDate, endDate);
					searchResult.add("条件に一致する投稿は" + posts.size()+ "件です");
				}

				//日付とカテゴリーでしぼる（カテゴリー指定あり）
				if (!category.equals("all")) {
					if (!start.isEmpty() && end.isEmpty()) {
						startDate = start;
						endDate = "2037-12-31 23:59:59";
					}
					if (!start.isEmpty() && !end.isEmpty()) {
						startDate = start;
						endDate = end;
					}
					if (start.isEmpty() && !end.isEmpty()) {
						startDate = "1970-01-01 00:00:01";
						endDate = end + " 23:59:59";
					}
					if (start.isEmpty() && end.isEmpty()) {
						startDate = "1970-01-01 00:00:01";
						endDate = "2037-12-31 23:59:59";
					}
					posts = new PostService().getSortedWithCategory(startDate, endDate, category);
					searchResult.add("条件に一致する投稿は" + posts.size()+ "件です");
				}

				request.setAttribute("selectedCategory", category);
				request.setAttribute("startDate", start);
				request.setAttribute("endDate", end);
				request.setAttribute("posts", posts);
				session.setAttribute("resultMessages", searchResult);

				List<UserComment> comments = new CommentService().getComments();
				List<Category> categories = new CategoryService().getCategories();

				request.setAttribute("comments", comments);
				request.setAttribute("categories", categories);

				request.getRequestDispatcher("/home.jsp").forward(request, response);
			}
	}

}

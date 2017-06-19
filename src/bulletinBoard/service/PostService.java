package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.Post;
import bulletinBoard.beans.UserPost;
import bulletinBoard.dao.CommentDao;
import bulletinBoard.dao.PostDao;
import bulletinBoard.dao.UserPostDao;

public class PostService {

	public void register(Post post) {

		Connection connection = null;
		try {
			connection = getConnection();

			PostDao postDao = new PostDao();
			postDao.insert(connection, post);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	//投稿（post）を削除したら、関連するコメントも削除。
	public void delete(int id) {

		Connection connection = null;
		try {
			connection = getConnection();

			PostDao postDao = new PostDao();
			postDao.delete(connection, id);

			CommentDao commentDao = new CommentDao();
			commentDao.deleteWithPost(connection, id);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<UserPost> getSortedWithCategory(String startDate, String endDate, String category) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserPostDao userPostDao = new UserPostDao();
			List<UserPost> ret = userPostDao.getSortedWithCategory(connection, startDate, endDate, category);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<UserPost> getSortedOnlyDate(String startDate, String endDate) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserPostDao userPostDao = new UserPostDao();
			List<UserPost> ret = userPostDao.getSortedOnlyDate(connection, startDate, endDate);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}


	private static final int LIMIT_NUM = 1000;

	public List<UserPost> getPosts() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserPostDao userPostDao = new UserPostDao();
			List<UserPost> ret = userPostDao.getUserPosts(connection, LIMIT_NUM);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}


}

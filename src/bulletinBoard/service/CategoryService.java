package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.Category;
import bulletinBoard.dao.CategoryDao;

public class CategoryService {




	public void register(Category category) {

		Connection connection = null;
		try {
			connection = getConnection();

			CategoryDao categoryDao = new CategoryDao();
			categoryDao.insert(connection, category);

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


	public Category getCategory(String name) {

		Connection connection = null;
		try {
			connection = getConnection();

			CategoryDao categoryDao = new CategoryDao();
			Category category = categoryDao.getCategory(connection, name);

			commit(connection);

			return category;
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

	public List<Category> getCategories() {

		Connection connection = null;
		try {
			connection = getConnection();

			CategoryDao categoryDao = new CategoryDao();
			List<Category> ret = categoryDao.getCategories(connection, LIMIT_NUM);

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

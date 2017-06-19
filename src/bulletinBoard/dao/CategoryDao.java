package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Category;
import bulletinBoard.exception.SQLRuntimeException;

public class CategoryDao {



	public Category getCategory(Connection connection, String name) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM categories WHERE name = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();
			List<Category> categoryList = toCategoryList(rs);
			if (categoryList.isEmpty() == true) {
				return null;
			} else if (2 <= categoryList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return categoryList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}






	public List<Category> getCategories(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM categories");
			sql.append(" ORDER BY id limit " + num);

			System.out.println(sql.toString());//■will be deleted

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Category> ret = toCategoryList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}



	private List<Category> toCategoryList(ResultSet rs) throws SQLException {

		List<Category> ret = new ArrayList<Category>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Category category = new Category();
				category.setId(id);
				category.setName(name);

				ret.add(category);
			}
			return ret;
		} finally {
			close(rs);
		}
	}


	public void insert(Connection connection, Category category) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO categories ( ");
			sql.append(" name");
			sql.append(") VALUES (");
			sql.append(" ?"); // name
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, category.getName());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}





}

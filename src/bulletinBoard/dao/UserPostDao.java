package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.UserPost;
import bulletinBoard.exception.SQLRuntimeException;

public class UserPostDao {

	public List<UserPost> getUserPosts(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_posts ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());


			ResultSet rs = ps.executeQuery();
			List<UserPost> ret = toUserPostList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserPost> toUserPostList(ResultSet rs)
			throws SQLException {

		List<UserPost> ret = new ArrayList<UserPost>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id"); //postId
				String category = rs.getString("category");
				String title = rs.getString("title");
				String text = rs.getString("text");
				int userId = rs.getInt("user_id");
				int branchId = rs.getInt("branch_id");
				String name = rs.getString("name");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserPost post = new UserPost();
				post.setId(id);
				post.setCategory(category);
				post.setTitle(title);
				post.setText(text);
				post.setUserId(userId);
				post.setName(name);
				post.setBranchId(branchId);
				post.setInsertDate(insertDate);

				ret.add(post);
			}
			return ret;
		} finally {
			close(rs);
		}
	}


	//しぼりこみ機能、カテゴリーかつ日時（日時指定なしも可能）の場合
	public List<UserPost> getSortedWithCategory(Connection connection, String startDate, String endDate, String category) {


		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			//StartDate＋1日する。
			sql.append("SELECT * FROM users_posts");
			sql.append(" WHERE category = ? AND");
			sql.append(" insert_date BETWEEN ? AND ? ");
			sql.append(" ORDER BY insert_date DESC ");



			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, category);
			ps.setString(2, startDate);
			ps.setString(3, endDate);


			System.out.println(ps);//■

			ResultSet rs = ps.executeQuery();
			List<UserPost> ret = toUserPostList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//しぼりこみ機能、カテゴリーなしの場合
	public List<UserPost> getSortedOnlyDate(Connection connection, String startDate, String endDate) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			//StartDate＋1日する。
			sql.append("SELECT * FROM users_posts");
			sql.append(" WHERE insert_date BETWEEN ? AND ?");
			sql.append(" ORDER BY insert_date DESC");

			System.out.println(sql.toString());//■

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, startDate);
			ps.setString(2, endDate);

			ResultSet rs = ps.executeQuery();
			List<UserPost> ret = toUserPostList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}

package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.UsersMessages;
import bulletinBoard.exception.SQLRuntimeException;

public class UsersMessagesDao {

	public List<UsersMessages> getUsersMessages(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_messages ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());


			ResultSet rs = ps.executeQuery();
			List<UsersMessages> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UsersMessages> toUserMessageList(ResultSet rs)
			throws SQLException {

		List<UsersMessages> ret = new ArrayList<UsersMessages>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id"); //messageId
				String category = rs.getString("category");
				String title = rs.getString("title");
				String text = rs.getString("text");
				int userId = rs.getInt("user_id");
				int branchId = rs.getInt("branch_id");
				String name = rs.getString("name");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UsersMessages message = new UsersMessages();
				message.setId(id);
				message.setCategory(category);
				message.setTitle(title);
				message.setText(text);
				message.setUserId(userId);
				message.setName(name);
				message.setBranchId(branchId);
				message.setInsertDate(insertDate);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}


	//しぼりこみ機能、カテゴリーかつ日時（日時指定なしも可能）の場合
	public List<UsersMessages> getSortedWithCategory(Connection connection, String startDate, String endDate, String category) {


		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			//StartDate＋1日する。
			sql.append("SELECT * FROM users_messages");
			sql.append(" WHERE insert_date BETWEEN ? AND ? ");
			sql.append(", category = ?");
			sql.append(" ORDER BY insert_date ");



			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, category);

			System.out.println(ps);//■

			ResultSet rs = ps.executeQuery();
			List<UsersMessages> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//しぼりこみ機能、カテゴリーなしの場合
	public List<UsersMessages> getSortedOnlyDate(Connection connection, String startDate, String endDate) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			//StartDate＋1日する。
			sql.append("SELECT * FROM users_messages");
			sql.append(" WHERE insert_date BETWEEN ? AND ?");
			sql.append(" ORDER BY insert_date ");

			System.out.println(sql.toString());//■

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, startDate);
			ps.setString(2, endDate);

			ResultSet rs = ps.executeQuery();
			List<UsersMessages> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}

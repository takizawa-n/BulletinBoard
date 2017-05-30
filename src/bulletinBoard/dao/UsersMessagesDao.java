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

	public List<UsersMessages> getUserMessages(Connection connection, int num) {

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
				int id = rs.getInt("message_id");
				String title = rs.getString("title");
				String text = rs.getString("text");
				int userId = rs.getInt("user_id");
				String name = rs.getString("name");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UsersMessages message = new UsersMessages();
				message.setMessageId(id);
				message.setTitle(title);
				message.setText(text);
				message.setUserId(userId);
				message.setName(name);
				message.setInsertDate(insertDate);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}

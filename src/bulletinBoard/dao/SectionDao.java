
package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Section;
import bulletinBoard.exception.SQLRuntimeException;

public class SectionDao  {

	private List<Section> toSectionList(ResultSet rs) throws SQLException {

		List<Section> ret = new ArrayList<Section>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Section section = new Section();
				section.setId(id);
				section.setName(name);

				ret.add(section);
			}
			return ret;
		} finally {
			close(rs);
		}
	}


	public List<Section> getSections(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM sections");
			sql.append(" ORDER BY id limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Section> ret = toSectionList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}

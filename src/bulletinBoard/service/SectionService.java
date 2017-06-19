package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.Section;
import bulletinBoard.dao.SectionDao;


public class SectionService {

	private static final int LIMIT_NUM = 1000;

	public List<Section> getSections() {

		Connection connection = null;
		try {
			connection = getConnection();

			SectionDao SectionDao = new SectionDao();
			List<Section> ret = SectionDao.getSections(connection, LIMIT_NUM);

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


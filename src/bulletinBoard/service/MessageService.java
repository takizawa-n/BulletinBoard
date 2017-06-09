package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.Messages;
import bulletinBoard.beans.UsersMessages;
import bulletinBoard.dao.CommentsDao;
import bulletinBoard.dao.MessagesDao;
import bulletinBoard.dao.UsersMessagesDao;

public class MessageService {

	public void register(Messages message) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessagesDao messagesDao = new MessagesDao();
			messagesDao.insert(connection, message);

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

	//投稿（message）を削除したら、関連するコメントも削除。
	public void delete(int id) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessagesDao messageDao = new MessagesDao();
			messageDao.delete(connection, id);

			CommentsDao commentDao = new CommentsDao();
			commentDao.deleteWithMessage(connection, id);

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

	public List<UsersMessages> getSortedWithCategory(String startDate, String endDate, String category) {

		Connection connection = null;
		try {
			connection = getConnection();

			UsersMessagesDao usersMessageDao = new UsersMessagesDao();
			List<UsersMessages> ret = usersMessageDao.getSortedWithCategory(connection, startDate, endDate, category);

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

	public List<UsersMessages> getSortedOnlyDate(String startDate, String endDate) {

		Connection connection = null;
		try {
			connection = getConnection();

			UsersMessagesDao usersMessageDao = new UsersMessagesDao();
			List<UsersMessages> ret = usersMessageDao.getSortedOnlyDate(connection, startDate, endDate);

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

	public List<UsersMessages> getMessages() {

		Connection connection = null;
		try {
			connection = getConnection();

			UsersMessagesDao usersMessageDao = new UsersMessagesDao();
			List<UsersMessages> ret = usersMessageDao.getUsersMessages(connection, LIMIT_NUM);

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

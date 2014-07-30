package br.com.ufscar.dao.common;

import javax.persistence.EntityManager;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserGenericTransaction implements GenericTransaction {

	private final Logger logger = LoggerFactory.getLogger(UserGenericTransaction.class);

	private final UserTransaction transaction;
	private final EntityManager entityManager;

	public UserGenericTransaction(UserTransaction transaction, EntityManager entityManager) {
		this.entityManager = entityManager;
		this.transaction = transaction;
	}

	@Override
	public GenericTransaction begin() {
		try {
			logger.debug("Beginning transaction...");
			transaction.begin();
			entityManager.joinTransaction();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	@Override
	public void close() throws RuntimeException {
		logger.debug("Closing  transaction...");
		try {
			if (isActive()) {
				commit();
			}
		} catch (Exception e) {
			if (isActive()) {
				rollback();
			}
			throw e;
		} finally {
			if (isRollback()) {
				rollback();
			}
		}
	}

	@Override
	public void commit() {
		try {
			logger.debug("Commiting  transaction...");
			transaction.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isActive() {
		try {
			logger.debug("Status=" + transaction.getStatus());
			return transaction.getStatus() == Status.STATUS_ACTIVE;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isRollback() {
		try {
			logger.debug("Status=" + transaction.getStatus());
			return transaction.getStatus() == Status.STATUS_MARKED_ROLLBACK;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void rollback() {
		try {
			logger.debug("Rollbacking  transaction...");
			transaction.rollback();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
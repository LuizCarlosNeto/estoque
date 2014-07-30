package br.com.ufscar.dao.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

public class JPAGenericTransaction implements GenericTransaction {

	private final EntityTransaction transaction;
	private final EntityManager entityManager;

	public JPAGenericTransaction(EntityManager entityManager) {
		this.entityManager = entityManager;
		transaction = entityManager.getTransaction();
	}

	@Override
	public GenericTransaction begin() {
		transaction.begin();
		return this;
	}

	@Override
	public void close() throws RuntimeException {
		try {
			if (isActive()) {
				if (isRollback()) {
					rollback();
				} else {
					commit();
				}
			}
		} catch (RollbackException e) {
			if (isActive()) {
				rollback();
			}
			throw e;
		}
	}

	@Override
	public void commit() {
		transaction.commit();
		entityManager.clear();
	}

	@Override
	public boolean isActive() {
		return transaction.isActive();
	}

	@Override
	public boolean isRollback() {
		return transaction.getRollbackOnly();
	}

	@Override
	public void rollback() {
		transaction.rollback();
		entityManager.clear();
	}

}
package br.com.ufscar.dao.common;

public interface GenericTransaction extends AutoCloseable {

	GenericTransaction begin();

	void commit();

	void rollback();

	@Override
	public void close() throws RuntimeException;

	boolean isActive();

	boolean isRollback();

}
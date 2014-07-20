package com.nodemanager.dao;

import java.util.List;

public interface IGenericDAO<T, PK> {

	public T getById(PK pk);

	public void save(T entity);

	public void update(T entity);

	public void delete(T entity);

	public List<T> findAll();

}
package com.nodemanager.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

import com.nodemanager.dao.IGenericDAO;

@SuppressWarnings("unchecked")
public class GenericDAO<T, PK> implements IGenericDAO<T, PK> {
  private EntityManager entityManager;

  public GenericDAO(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.nodemanager.dao.impl.IGenericDAO#getById(PK)
   */
  @Override
  public T getById(PK pk) {
    return (T) entityManager.find(getTypeClass(), pk);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.nodemanager.dao.impl.IGenericDAO#save(T)
   */
  @Override
  public void save(T entity) {
    entityManager.persist(entity);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.nodemanager.dao.impl.IGenericDAO#update(T)
   */
  @Override
  public void update(T entity) {
    entityManager.merge(entity);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.nodemanager.dao.impl.IGenericDAO#delete(T)
   */
  @Override
  public void delete(T entity) {
    entityManager.remove(entity);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.nodemanager.dao.impl.IGenericDAO#findAll()
   */
  @Override
  public List<T> findAll() {
    return entityManager.createQuery(("FROM " + getTypeClass().getName())).getResultList();
  }

  private Class<?> getTypeClass() {
    Class<?> clazz =
        (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
    return clazz;
  }

  /**
   * @return the entityManager
   */
  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * @param entityManager the entityManager to set
   */
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
}

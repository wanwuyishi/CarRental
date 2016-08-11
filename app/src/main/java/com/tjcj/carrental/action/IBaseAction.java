package com.tjcj.carrental.action;

import java.util.List;

public interface IBaseAction<T> {

	public boolean add(T entity);
	public boolean delete(T entity);
	public boolean update(T entity);
	public List<T> findAll(int num);
}

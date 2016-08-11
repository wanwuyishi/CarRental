package com.tjcj.carrental.action;

import com.tjcj.carrental.model.User;

import java.util.List;

public class UserAction extends BaseAction<User> implements IBaseAction<User> {

	@Override
	public boolean add(User entity) {
		return modify("addUser",entity);
	}

	@Override
	public boolean delete(User entity) {
		return false;
	}

	@Override
	public boolean update(User entity) {
		return modify("updateUser",entity);
	}

	@Override
	public List<User> findAll(int num) {
		return null;
	}
	public User checkUser(String user){
		return getModel("checkUser",user);
	}

	public boolean authenticate(User entity){
		return modify("authentication",entity);
	}

	public boolean findUserByPhone(String phone){
		boolean flag=modify("findUserByPhone",phone);
		return flag;
	}
	public boolean modifyPwdByPhone(String pwd){
		boolean flag=modify("modifyPwdByPhone",pwd);
		return flag;
	}
}

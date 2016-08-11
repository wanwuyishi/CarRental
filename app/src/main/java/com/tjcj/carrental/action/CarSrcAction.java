package com.tjcj.carrental.action;

import java.util.List;

import com.tjcj.carrental.model.Carsrc;

public class CarSrcAction extends BaseAction<Carsrc> implements IBaseAction<Carsrc>{

	@Override
	public boolean add(Carsrc entity) {
		// TODO Auto-generated method stub
		return modify("addCarSrc", entity);
	}

	@Override
	public boolean delete(Carsrc entity) {
		// TODO Auto-generated method stub
		return modify("deleteCarSrc", entity);
	}

	@Override
	public boolean update(Carsrc entity) {
		// TODO Auto-generated method stub
		return modify("updateCarSrc", entity);
	}

	@Override
	public List<Carsrc> findAll(int num) {
		// TODO Auto-generated method stub
		return query("findAllCarSrc", num);
	}

	public List<Carsrc> findAllByUserId(int id) {
		// TODO Auto-generated method stub
		return query("findAllByUserIdCarSrc", id);
	}

	public List<Carsrc> findAllByCityCarSrc(String city) {
		// TODO Auto-generated method stub
		return query("findAllByCityCarSrc", city);
	}

}

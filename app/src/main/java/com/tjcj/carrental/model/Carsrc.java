package com.tjcj.carrental.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Carsrc entity. @author MyEclipse Persistence Tools
 */

public class Carsrc implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8593639453170548532L;
	private Integer id;
	private User user;
	private Timestamp ordertime;
	private String mobile;
	private String startCity;
	private String startArea;
	private String endCity;
	private String endArea;
	private String remark;
	private String carType;
	private Double weight;
	private Double volume;
	private String number;
	private Set orders = new HashSet(0);

	// Constructors

	/** default constructor */
	public Carsrc() {
	}

	/** full constructor */
	public Carsrc(User user, Timestamp ordertime, String mobile,
			String startCity, String startArea, String endCity, String endArea,
			String remark, String carType, Double weight, Double volume,
			String number, Set orders) {
		this.user = user;
		this.ordertime = ordertime;
		this.mobile = mobile;
		this.startCity = startCity;
		this.startArea = startArea;
		this.endCity = endCity;
		this.endArea = endArea;
		this.remark = remark;
		this.carType = carType;
		this.weight = weight;
		this.volume = volume;
		this.number = number;
		this.orders = orders;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getOrdertime() {
		return this.ordertime;
	}

	public void setOrdertime(Timestamp ordertime) {
		this.ordertime = ordertime;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStartCity() {
		return this.startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getStartArea() {
		return this.startArea;
	}

	public void setStartArea(String startArea) {
		this.startArea = startArea;
	}

	public String getEndCity() {
		return this.endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public String getEndArea() {
		return this.endArea;
	}

	public void setEndArea(String endArea) {
		this.endArea = endArea;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCarType() {
		return this.carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getVolume() {
		return this.volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Set getOrders() {
		return this.orders;
	}

	public void setOrders(Set orders) {
		this.orders = orders;
	}

}
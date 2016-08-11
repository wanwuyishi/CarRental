package com.tjcj.carrental.model;

/**
 * Car entity. @author MyEclipse Persistence Tools
 */

public class Car implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String carBrand;
	private String carType;
	private String carNumber;
	private String carBodyLevel;

	// Constructors

	/** default constructor */
	public Car() {
	}

	/** full constructor */
	public Car(User user, String carBrand, String carType, String carNumber,
			String carBodyLevel) {
		this.user = user;
		this.carBrand = carBrand;
		this.carType = carType;
		this.carNumber = carNumber;
		this.carBodyLevel = carBodyLevel;
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

	public String getCarBrand() {
		return this.carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarType() {
		return this.carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarNumber() {
		return this.carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getCarBodyLevel() {
		return this.carBodyLevel;
	}

	public void setCarBodyLevel(String carBodyLevel) {
		this.carBodyLevel = carBodyLevel;
	}

}
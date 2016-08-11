package com.tjcj.carrental.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Goodssrc entity. @author MyEclipse Persistence Tools
 */

public class Goodssrc implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8544589764662941623L;
	private Integer id;
	private User user;
	private String remark;
	private String mobile;
	private String startCity;
	private String startArea;
	private String endCity;
	private String endArea;
	private String startDetails;
	private String endDetails;
	private Double weight;
	private Double volume;
	private Double price;
	private Timestamp ordertime;
	private String goodsname;
	private String carType;
	private String goodsType;
	private String weightSel;
	private String priceSel;
	private String number;
	private Set orders = new HashSet(0);

	// Constructors

	/** default constructor */
	public Goodssrc() {
	}

	/** full constructor */
	public Goodssrc(User user, String remark, String mobile, String startCity,
			String startArea, String endCity, String endArea,
			String startDetails, String endDetails, Double weight,
			Double volume, Double price, Timestamp ordertime, String goodsname,
			String carType, String goodsType, String weightSel,
			String priceSel, String number, Set orders) {
		this.user = user;
		this.remark = remark;
		this.mobile = mobile;
		this.startCity = startCity;
		this.startArea = startArea;
		this.endCity = endCity;
		this.endArea = endArea;
		this.startDetails = startDetails;
		this.endDetails = endDetails;
		this.weight = weight;
		this.volume = volume;
		this.price = price;
		this.ordertime = ordertime;
		this.goodsname = goodsname;
		this.carType = carType;
		this.goodsType = goodsType;
		this.weightSel = weightSel;
		this.priceSel = priceSel;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getStartDetails() {
		return this.startDetails;
	}

	public void setStartDetails(String startDetails) {
		this.startDetails = startDetails;
	}

	public String getEndDetails() {
		return this.endDetails;
	}

	public void setEndDetails(String endDetails) {
		this.endDetails = endDetails;
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

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Timestamp getOrdertime() {
		return this.ordertime;
	}

	public void setOrdertime(Timestamp ordertime) {
		this.ordertime = ordertime;
	}

	public String getGoodsname() {
		return this.goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getCarType() {
		return this.carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getGoodsType() {
		return this.goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getWeightSel() {
		return this.weightSel;
	}

	public void setWeightSel(String weightSel) {
		this.weightSel = weightSel;
	}

	public String getPriceSel() {
		return this.priceSel;
	}

	public void setPriceSel(String priceSel) {
		this.priceSel = priceSel;
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
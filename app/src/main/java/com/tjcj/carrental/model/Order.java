package com.tjcj.carrental.model;

import java.sql.Timestamp;

/**
 * Order entity. @author MyEclipse Persistence Tools
 */

public class Order implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6212070431551833574L;
	private Integer id;
	private Carsrc carsrc;
	private Goodssrc goodssrc;
	private User userByRecevierid;
	private User userByUserid;
	private String number;
	private Timestamp createTime;
	private Timestamp paymentTime;
	private Timestamp deliveryTime;
	private Timestamp endTime;
	private Integer status;
	private Double biding;

	// Constructors

	/** default constructor */
	public Order() {
	}

	/** full constructor */
	public Order(Carsrc carsrc, Goodssrc goodssrc, User userByRecevierid,
			User userByUserid, String number, Timestamp createTime,
			Timestamp paymentTime, Timestamp deliveryTime, Timestamp endTime,
			Integer status, Double biding) {
		this.carsrc = carsrc;
		this.goodssrc = goodssrc;
		this.userByRecevierid = userByRecevierid;
		this.userByUserid = userByUserid;
		this.number = number;
		this.createTime = createTime;
		this.paymentTime = paymentTime;
		this.deliveryTime = deliveryTime;
		this.endTime = endTime;
		this.status = status;
		this.biding = biding;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Carsrc getCarsrc() {
		return this.carsrc;
	}

	public void setCarsrc(Carsrc carsrc) {
		this.carsrc = carsrc;
	}

	public Goodssrc getGoodssrc() {
		return this.goodssrc;
	}

	public void setGoodssrc(Goodssrc goodssrc) {
		this.goodssrc = goodssrc;
	}

	public User getUserByRecevierid() {
		return this.userByRecevierid;
	}

	public void setUserByRecevierid(User userByRecevierid) {
		this.userByRecevierid = userByRecevierid;
	}

	public User getUserByUserid() {
		return this.userByUserid;
	}

	public void setUserByUserid(User userByUserid) {
		this.userByUserid = userByUserid;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getPaymentTime() {
		return this.paymentTime;
	}

	public void setPaymentTime(Timestamp paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Timestamp getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getBiding() {
		return this.biding;
	}

	public void setBiding(Double biding) {
		this.biding = biding;
	}

}
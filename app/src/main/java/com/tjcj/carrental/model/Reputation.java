package com.tjcj.carrental.model;

/**
 * Reputation entity. @author MyEclipse Persistence Tools
 */

public class Reputation implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8178348082842057021L;
	private Integer id;
	private User user;
	private Integer count;
	private Integer star;
	private Integer sumStar;
	private Integer sum;

	// Constructors

	/** default constructor */
	public Reputation() {
	}

	/** minimal constructor */
	public Reputation(User user) {
		this.user = user;
	}

	/** full constructor */
	public Reputation(User user, Integer count, Integer star, Integer sumStar,
			Integer sum) {
		this.user = user;
		this.count = count;
		this.star = star;
		this.sumStar = sumStar;
		this.sum = sum;
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

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getStar() {
		return this.star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getSumStar() {
		return this.sumStar;
	}

	public void setSumStar(Integer sumStar) {
		this.sumStar = sumStar;
	}

	public Integer getSum() {
		return this.sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

}
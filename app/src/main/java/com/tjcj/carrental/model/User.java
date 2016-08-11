package com.tjcj.carrental.model;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1444271121637019642L;
	private Integer id;
	private String account;
	private String password;
	private String nickname;
	private String phone;
	private String icon;
	private Integer role;
	private Set goodssrcs = new HashSet(0);
	private Set carsrcs = new HashSet(0);
	private Set ordersForUserid = new HashSet(0);
	private Authentication authentication;
	private Reputation reputation;
	private Set ordersForRecevierid = new HashSet(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String account, String password, String nickname, String phone,
			String icon, Integer role, Set goodssrcs, Set carsrcs,
			Set ordersForUserid, Authentication authentication,
			Reputation reputation, Set ordersForRecevierid) {
		this.account = account;
		this.password = password;
		this.nickname = nickname;
		this.phone = phone;
		this.icon = icon;
		this.role = role;
		this.goodssrcs = goodssrcs;
		this.carsrcs = carsrcs;
		this.ordersForUserid = ordersForUserid;
		this.authentication = authentication;
		this.reputation = reputation;
		this.ordersForRecevierid = ordersForRecevierid;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getRole() {
		return this.role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Set getGoodssrcs() {
		return this.goodssrcs;
	}

	public void setGoodssrcs(Set goodssrcs) {
		this.goodssrcs = goodssrcs;
	}

	public Set getCarsrcs() {
		return this.carsrcs;
	}

	public void setCarsrcs(Set carsrcs) {
		this.carsrcs = carsrcs;
	}

	public Set getOrdersForUserid() {
		return this.ordersForUserid;
	}

	public void setOrdersForUserid(Set ordersForUserid) {
		this.ordersForUserid = ordersForUserid;
	}

	public Authentication getAuthentication() {
		return this.authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public Reputation getReputation() {
		return this.reputation;
	}

	public void setReputation(Reputation reputation) {
		this.reputation = reputation;
	}

	public Set getOrdersForRecevierid() {
		return this.ordersForRecevierid;
	}

	public void setOrdersForRecevierid(Set ordersForRecevierid) {
		this.ordersForRecevierid = ordersForRecevierid;
	}

}
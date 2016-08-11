package com.tjcj.carrental.model;

/**
 * Userdetail entity. @author MyEclipse Persistence Tools
 */

public class Userdetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String userIdentity;
	private String driversLicense;
	private String drivingLicense;
	private String businessLicense;
	private String door;
	private String qualificationCertificate;
	private String rtbl;
	private String occ;
	private Integer count;
	private Integer star;
	private Integer sum;

	// Constructors

	/** default constructor */
	public Userdetail() {
	}

	/** minimal constructor */
	public Userdetail(User user) {
		this.user = user;
	}

	/** full constructor */
	public Userdetail(User user, String userIdentity, String driversLicense,
			String drivingLicense, String businessLicense, String door,
			String qualificationCertificate, String rtbl, String occ,
			Integer count, Integer star, Integer sum) {
		this.user = user;
		this.userIdentity = userIdentity;
		this.driversLicense = driversLicense;
		this.drivingLicense = drivingLicense;
		this.businessLicense = businessLicense;
		this.door = door;
		this.qualificationCertificate = qualificationCertificate;
		this.rtbl = rtbl;
		this.occ = occ;
		this.count = count;
		this.star = star;
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

	public String getUserIdentity() {
		return this.userIdentity;
	}

	public void setUserIdentity(String userIdentity) {
		this.userIdentity = userIdentity;
	}

	public String getDriversLicense() {
		return this.driversLicense;
	}

	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	public String getDrivingLicense() {
		return this.drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public String getBusinessLicense() {
		return this.businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getDoor() {
		return this.door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public String getQualificationCertificate() {
		return this.qualificationCertificate;
	}

	public void setQualificationCertificate(String qualificationCertificate) {
		this.qualificationCertificate = qualificationCertificate;
	}

	public String getRtbl() {
		return this.rtbl;
	}

	public void setRtbl(String rtbl) {
		this.rtbl = rtbl;
	}

	public String getOcc() {
		return this.occ;
	}

	public void setOcc(String occ) {
		this.occ = occ;
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

	public Integer getSum() {
		return this.sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Userdetail [id=" + id + ", user=" + user + ", userIdentity="
				+ userIdentity + ", driversLicense=" + driversLicense
				+ ", drivingLicense=" + drivingLicense + ", businessLicense="
				+ businessLicense + ", door=" + door
				+ ", qualificationCertificate=" + qualificationCertificate
				+ ", rtbl=" + rtbl + ", occ=" + occ + ", count=" + count
				+ ", star=" + star + ", sum=" + sum + "]";
	}

	
}
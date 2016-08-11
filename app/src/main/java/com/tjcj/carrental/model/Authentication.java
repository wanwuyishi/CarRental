package com.tjcj.carrental.model;

/**
 * Authentication entity. @author MyEclipse Persistence Tools
 */

public class Authentication implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8332429356941144502L;
	private Integer id;
	private User user;
	private String name;
	private String carNumber;
	private String identity;//身份证号
	private String identityPhoto;//身份证
	private Integer identityStatus;
	private String driversLicense;//驾驶证
	private Integer driversLicenseStatus;
	private String drivingLicense;//行驶证
	private Integer drivingLicenseStatus;
	private String businessLicense;//营业执照
	private Integer businessLicenseStatus;
	private String door;//门头
	private Integer doorStatus;
	private String qfcf;//从业资格证
	private Integer qfcfStatus;
	private String rtbl;//道路运输许可证
	private Integer rtblStatus;
	private String occ;//组织机构代码证
	private Integer occStatus;

	// Constructors

	/** default constructor */
	public Authentication() {
	}

	/** minimal constructor */
	public Authentication(User user) {
		this.user = user;
	}

	/** full constructor */
	public Authentication(User user, String name, String carNumber,
			String identity, String identityPhoto, Integer identityStatus,
			String driversLicense, Integer driversLicenseStatus,
			String drivingLicense, Integer drivingLicenseStatus,
			String businessLicense, Integer businessLicenseStatus, String door,
			Integer doorStatus, String qfcf, Integer qfcfStatus, String rtbl,
			Integer rtblStatus, String occ, Integer occStatus) {
		this.user = user;
		this.name = name;
		this.carNumber = carNumber;
		this.identity = identity;
		this.identityPhoto = identityPhoto;
		this.identityStatus = identityStatus;
		this.driversLicense = driversLicense;
		this.driversLicenseStatus = driversLicenseStatus;
		this.drivingLicense = drivingLicense;
		this.drivingLicenseStatus = drivingLicenseStatus;
		this.businessLicense = businessLicense;
		this.businessLicenseStatus = businessLicenseStatus;
		this.door = door;
		this.doorStatus = doorStatus;
		this.qfcf = qfcf;
		this.qfcfStatus = qfcfStatus;
		this.rtbl = rtbl;
		this.rtblStatus = rtblStatus;
		this.occ = occ;
		this.occStatus = occStatus;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCarNumber() {
		return this.carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getIdentity() {
		return this.identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getIdentityPhoto() {
		return this.identityPhoto;
	}

	public void setIdentityPhoto(String identityPhoto) {
		this.identityPhoto = identityPhoto;
	}

	public Integer getIdentityStatus() {
		return this.identityStatus;
	}

	public void setIdentityStatus(Integer identityStatus) {
		this.identityStatus = identityStatus;
	}

	public String getDriversLicense() {
		return this.driversLicense;
	}

	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	public Integer getDriversLicenseStatus() {
		return this.driversLicenseStatus;
	}

	public void setDriversLicenseStatus(Integer driversLicenseStatus) {
		this.driversLicenseStatus = driversLicenseStatus;
	}

	public String getDrivingLicense() {
		return this.drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public Integer getDrivingLicenseStatus() {
		return this.drivingLicenseStatus;
	}

	public void setDrivingLicenseStatus(Integer drivingLicenseStatus) {
		this.drivingLicenseStatus = drivingLicenseStatus;
	}

	public String getBusinessLicense() {
		return this.businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public Integer getBusinessLicenseStatus() {
		return this.businessLicenseStatus;
	}

	public void setBusinessLicenseStatus(Integer businessLicenseStatus) {
		this.businessLicenseStatus = businessLicenseStatus;
	}

	public String getDoor() {
		return this.door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public Integer getDoorStatus() {
		return this.doorStatus;
	}

	public void setDoorStatus(Integer doorStatus) {
		this.doorStatus = doorStatus;
	}

	public String getQfcf() {
		return this.qfcf;
	}

	public void setQfcf(String qfcf) {
		this.qfcf = qfcf;
	}

	public Integer getQfcfStatus() {
		return this.qfcfStatus;
	}

	public void setQfcfStatus(Integer qfcfStatus) {
		this.qfcfStatus = qfcfStatus;
	}

	public String getRtbl() {
		return this.rtbl;
	}

	public void setRtbl(String rtbl) {
		this.rtbl = rtbl;
	}

	public Integer getRtblStatus() {
		return this.rtblStatus;
	}

	public void setRtblStatus(Integer rtblStatus) {
		this.rtblStatus = rtblStatus;
	}

	public String getOcc() {
		return this.occ;
	}

	public void setOcc(String occ) {
		this.occ = occ;
	}

	public Integer getOccStatus() {
		return this.occStatus;
	}

	public void setOccStatus(Integer occStatus) {
		this.occStatus = occStatus;
	}

}
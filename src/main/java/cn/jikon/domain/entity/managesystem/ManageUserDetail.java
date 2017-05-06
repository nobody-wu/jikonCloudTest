package cn.jikon.domain.entity.managesystem;

import java.io.Serializable;
/****
 * 
 * @author zyf
 * 管理员基本信息表
 * create by 2017.3.24 14:22
 *
 */
public class ManageUserDetail implements Serializable {

	private static final long serialVersionUID = 4093097108135693897L;
	
	private int no;
	private String id;
	private String userId;
	private String name;
	private String provinc;
	private String city;
	private String country;
	private String address;
	private String linkName;
	private String mobile;
	private String email;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvinc() {
		return provinc;
	}
	public void setProvinc(String provinc) {
		this.provinc = provinc;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

package cn.jikon.domain.entity.managesystem;

import java.io.Serializable;
/****
 * 
 * @author zyf
 * 管理员登录信息表
 * create by 2017.3.24 14:19
 *
 */
public class ManageUser implements Serializable {
	
	private static final long serialVersionUID = -9053393951806180615L;
	

	private String id;
	private String account;
	/*****md5加密***************/
	private String password;
	private String useName;
	/*********1已经激活，0未激活********/
	private boolean status;
	private String loginStatus;
	private String type;
	private String remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}

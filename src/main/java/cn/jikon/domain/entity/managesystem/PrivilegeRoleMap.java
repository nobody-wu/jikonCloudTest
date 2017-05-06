package cn.jikon.domain.entity.managesystem;

import java.io.Serializable;

/****
 * @author zyf
 * 权限与角色关系表
 * create by 2017.3.24 13:38
 */
public class PrivilegeRoleMap implements Serializable {
	
	private static final long serialVersionUID = 4704835070931096584L;
	
	private int no;
	private String id;
	private String roleId;
	private String privilegeId;
	private String describe;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	
}

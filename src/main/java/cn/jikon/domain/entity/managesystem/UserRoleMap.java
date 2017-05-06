package cn.jikon.domain.entity.managesystem;

import java.io.Serializable;

/****
 * @author zyf 
 * 角色与用户关系表 
 * create by 2017.3.24 14:31
 */
public class UserRoleMap implements Serializable {

	private static final long serialVersionUID = -8922319052272825254L;
	private int no;
	private String id;
	private String userId;
	private String roleId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}

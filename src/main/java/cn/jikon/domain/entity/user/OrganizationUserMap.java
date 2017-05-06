package cn.jikon.domain.entity.user;

import java.io.Serializable;

/****
 * @author zyf
 * 组织与用户中间表
 * create by 2017.3.24 14:15
 */
public class OrganizationUserMap implements Serializable {

	private static final long serialVersionUID = 1310462438983896326L;


	private String id;
	private String organizationId;
	private String userId;
	private String describe;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}

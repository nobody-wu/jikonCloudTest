package cn.jikon.domain.entity.managesystem;

import java.io.Serializable;

/****
 * @author zyf
 * 模块权限对应关系表
 * create by 2017.3.24 14:52
 */
public class ModulePrivilegeMap implements Serializable {

	private static final long serialVersionUID = -8950571462832092635L;
	
	private int no;
	private String id;
	private String moduleId;
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

	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
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

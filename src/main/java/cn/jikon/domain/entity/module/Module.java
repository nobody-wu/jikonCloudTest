package cn.jikon.domain.entity.module;

import java.io.Serializable;
/****
 * @author zyf
 * 模块表
 * create by 2017.3.24 14:51
 */
public class Module implements Serializable {

	private static final long serialVersionUID = -3981515667114542428L;
	
	private String id;
	private String name;
	private String describe;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	
}

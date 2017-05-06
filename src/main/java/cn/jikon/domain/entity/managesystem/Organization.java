package cn.jikon.domain.entity.managesystem;

import java.io.Serializable;

/****
 * @author zyf
 * 组织表
 * create by 2017.3.24 14:58
 */
public class Organization implements Serializable{

	private static final long serialVersionUID = 5416330990244415943L;
	

	private String id;
	private String name;
	private String level;
	private String describe;


	public Organization() {}
	public Organization(String id, String name) {
		this.id = id;
		this.name = name;

	}

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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}

}

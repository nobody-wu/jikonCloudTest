package cn.jikon.domain.entity.managesystem;

import java.io.Serializable;

/****
 * @author zyf
 * 权限
 * create by 2017.3.24 14:36
 */
public class Privilege implements Serializable{
	
	private static final long serialVersionUID = 5157157817880813908L;
	
	private int no;
	private String id;
	private String name;
	private String describe;
	
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

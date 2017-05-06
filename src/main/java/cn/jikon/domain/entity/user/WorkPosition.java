package cn.jikon.domain.entity.user;

import java.io.Serializable;

/****
 * 
 * @author zyf
 * 
 * 工作岗位
 * 
 * create by 2017.3.24 14:11
 *
 */
public class WorkPosition implements Serializable{
	
	private static final long serialVersionUID = 3124051191852746445L;
	
	private int no;
	private String id;
	private String type;
	private String code;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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

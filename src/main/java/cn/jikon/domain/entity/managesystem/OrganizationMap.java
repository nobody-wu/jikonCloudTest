package cn.jikon.domain.entity.managesystem;

import java.io.Serializable;

/****
 * @author zyf
 * 组织层次表
 * create by 2017.3.24 15:00
 */
public class OrganizationMap implements Serializable{

	private static final long serialVersionUID = -8663266995008571191L;
	
	private int no;
	private String id;
	private String fatherId;
	private String childId;
	
	public OrganizationMap() {}
	
	public OrganizationMap(String childId, String fatherId) {
		this.childId = childId;
		this.fatherId = fatherId;
	}

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
	public String getFatherId() {
		return fatherId;
	}
	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}
	public String getChildId() {
		return childId;
	}
	public void setChildId(String childId) {
		this.childId = childId;
	}
	
	
}

package cn.jikon.domain.vo;

public class OrganizationVo {

  private String id;
  //name:页面上为label
  private String label;
  private String level;
  private String type;
  private String describe;
  private String fatherId;
  private boolean checkIsEmpty;

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public boolean isCheckIsEmpty() {
    return checkIsEmpty;
  }
  public void setCheckIsEmpty(boolean checkIsEmpty) {
    this.checkIsEmpty = checkIsEmpty;
  }

  public String getFatherId() {
    return fatherId;
  }

  public void setFatherId(String fatherId) {
    this.fatherId = fatherId;
  }

  public OrganizationVo() {
  }
  public OrganizationVo(String id, String label, String level) {
    this.id = id;
    this.label = label;
    this.level = level;
  }
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
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

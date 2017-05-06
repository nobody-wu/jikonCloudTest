package cn.jikon.domain.vo;

/**
 * Created by admin on 2017/4/11.
 */
public class UserVo {
  public UserVo(){}
  public UserVo(String userId, String name, String describe, String type) {
    this.userId = userId;
    this.name = name;
    this.type = type;
    this.describe = describe;
  }
  private String userId;
  private String name;
  private String account;
  public String getAccount() {
    return account;
  }
  public void setAccount(String account) {
    this.account = account;
  }
  private String type;
  private String describe;
  private String token;

  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

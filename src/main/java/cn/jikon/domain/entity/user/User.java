package cn.jikon.domain.entity.user;

import java.io.Serializable;

/****
 * @author zyf
 * 用户登录信息
 * create by 2017.3.24 13:59
 */
public class User implements Serializable {

    private static final long serialVersionUID = -2905845465000357592L;

    private String id;
    private String account;
    /*****md5加密***************/
    private String password;
    private String useName;
    /*********1已经激活，0未激活********/
    private boolean status;
    private String loginStatus;
    private String type;
    private String remark;
    private String describe;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public User() {
    }

    public User(String id, String account, String useName, String describe) {
        this.id = id;
        this.account = account;
        this.useName = useName;
        this.describe = describe;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}

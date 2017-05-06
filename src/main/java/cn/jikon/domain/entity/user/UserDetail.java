package cn.jikon.domain.entity.user;

import java.io.Serializable;

/****
 *
 * @author zyf
 *
 * 用户基本信息
 *
 * create by 2017.3.24 14:09
 *
 */
public class UserDetail implements Serializable {

    private static final long serialVersionUID = 996197393394486021L;


    private String id;
    private String userId;
    private String mobile;
    private String email;
    private String address;
    private String imageId;
    private String positionId;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }


}

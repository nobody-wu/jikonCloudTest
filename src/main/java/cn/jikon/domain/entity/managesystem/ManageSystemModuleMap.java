package cn.jikon.domain.entity.managesystem;

import java.io.Serializable;

/**
 * Created by zl on 2017/4/27.
 */
public class ManageSystemModuleMap implements Serializable{

    private static final long serialVersionUID = 3134373949472812466L;

    private String id;
    private String manUserId;
    private String systemModuleId;
    private String manDescribe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManUserId() {
        return manUserId;
    }

    public void setManUserId(String manUserId) {
        this.manUserId = manUserId;
    }

    public String getSystemModuleId() {
        return systemModuleId;
    }

    public void setSystemModuleId(String systemModuleId) {
        this.systemModuleId = systemModuleId;
    }

    public String getManDescribe() {
        return manDescribe;
    }

    public void setManDescribe(String manDescribe) {
        this.manDescribe = manDescribe;
    }
}

package cn.jikon.domain.entity.module;

import java.io.Serializable;

/**
 * Created by wangxin on 2017/4/25.
 */
public class SubsystemModuleMap implements Serializable{

    private String id;
    private String moduleId;
    private String subsystemId;
    private String describe;

    public SubsystemModuleMap() {
    }
    public SubsystemModuleMap(String id, String moduleId, String subsystemId, String describe) {
        this.id = id;
        this.moduleId = moduleId;
        this.subsystemId = subsystemId;
        this.describe = describe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(String subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}

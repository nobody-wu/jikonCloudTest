package cn.jikon.domain.entity.managesystem;

import java.io.Serializable;

/**
 * 子系统信息
 * Created by wujiapeng on 17/4/25.
 */
public class SubSystem implements Serializable {

    /**
     * 机器内码
     */
    private int no;

    /**
     * 子系统ID
     */
    private String id;

    /**
     * 子系统组织名称
     */
    private String name;

    /**
     * 系统编码
     */
    private String code;

    /**
     * 描述
     */
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String descripe) {
        this.describe = descripe;
    }

    @Override
    public String toString() {
        return "SubSystem{" +
                "no=" + no +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", descripe='" + describe + '\'' +
                '}';
    }
}

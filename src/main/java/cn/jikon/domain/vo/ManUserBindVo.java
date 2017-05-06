package cn.jikon.domain.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2017/4/27.
 */
public class ManUserBindVo {

    private String subSystemId;

    private String getSubSystemName;

    List<SubSystemModuleVo> moduleList = new ArrayList<SubSystemModuleVo>();

    public String getSubSystemId() {
        return subSystemId;
    }

    public void setSubSystemId(String subSystemId) {
        this.subSystemId = subSystemId;
    }

    public String getGetSubSystemName() {
        return getSubSystemName;
    }

    public void setGetSubSystemName(String getSubSystemName) {
        this.getSubSystemName = getSubSystemName;
    }

    public List<SubSystemModuleVo> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<SubSystemModuleVo> moduleList) {
        this.moduleList = moduleList;
    }
}

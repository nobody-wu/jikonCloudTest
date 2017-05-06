package cn.jikon.logic.module;

import cn.jikon.domain.dao.module.SubsystemModuleMapDao;
import cn.jikon.domain.entity.module.SubsystemModuleMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangxin on 2017/4/25.
 */
@Service
public class SubsystemModuleMapLogic {
    @Autowired
    SubsystemModuleMapDao subsystemModuleMapDao;

    public List<String> getModuleIdList(String subsystemId) {
        return subsystemModuleMapDao.getModuleIdList(subsystemId);
    }

    public String getSubIdByModuleId(String moduleId) {
        return subsystemModuleMapDao.getSubIdByModuleId(moduleId);
    }
    public List<SubsystemModuleMap> getListModulePrivilegeMapBySubId(String subId){
        return subsystemModuleMapDao.getListModulePrivilegeMapBySubId(subId);
    }

    public void deleteModulePriMapInfoBySubId(String subId){
        subsystemModuleMapDao.deleteModulePriMapInfoBySubId(subId);
    }

    public SubsystemModuleMap getSubsystemModuleMapById(String id){
        return subsystemModuleMapDao.getSubsystemModuleMapById(id);
    }
}

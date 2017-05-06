package cn.jikon.logic.module;

import cn.jikon.common.util.UUID;
import cn.jikon.domain.dao.module.ModuleDao;
import cn.jikon.domain.dao.module.SubsystemModuleMapDao;
import cn.jikon.domain.entity.module.Module;
import cn.jikon.domain.entity.module.SubsystemModuleMap;
import cn.jikon.domain.vo.ModuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangxin on 2017/4/25.
 */
@Service
public class ModuleLogic {

    @Autowired
    ModuleDao moduleDao;
    @Autowired
    SubsystemModuleMapDao subsystemModuleMapDao;

    @Transactional
    public ModuleVo addModule(String name, String describe, String subsystemId) {
        String moduleId = UUID.getIdString();
        moduleDao.addModule(moduleId, name, describe);

        String moduleSubsystemMapId = UUID.getIdString();
        SubsystemModuleMap subsystemModuleMap = new SubsystemModuleMap(moduleSubsystemMapId, moduleId, subsystemId, describe);
        subsystemModuleMapDao.addSubsystemModuleMap(subsystemModuleMap);

        ModuleVo moduleVo = new ModuleVo();
        moduleVo.setModuleId(moduleId);
        return moduleVo;
    }

    @Transactional
    public void delModule(String moduleId) {
        moduleDao.delModuleById(moduleId);
        subsystemModuleMapDao.delSubsystemModuleMapById(moduleId);
    }

    @Transactional
    public void updateModule(String moduleId, String name, String subsystemId, String describe) {
        moduleDao.updateModule(moduleId, name, describe);
        subsystemModuleMapDao.updateSubsystemModuleMap(moduleId, subsystemId, describe);
    }

    public Module getModule(String moduleId) {
        return moduleDao.getModule(moduleId);
    }

    public Module getModuleByName(String name) {
        return moduleDao.getModuleByName(name);
    }

    public List<Module> getModuleList(int start, int pageCount) {
        return moduleDao.getModuleList(start, pageCount);
    }

    public int getModuleCount() {
        return moduleDao.getModuleCount();
    }

    public List<Module> getListAllModule(){
        return moduleDao.getListAllModule();
    }

    public List<Module> getListModuleIdList(List<String> moduleIdList){
        return moduleDao.getListModuleIdList(moduleIdList);
    }
}

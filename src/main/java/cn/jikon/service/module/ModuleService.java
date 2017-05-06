package cn.jikon.service.module;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.entity.managesystem.SubSystem;
import cn.jikon.domain.entity.module.Module;
import cn.jikon.domain.vo.ModuleVo;
import cn.jikon.logic.module.ModuleLogic;
import cn.jikon.logic.module.SubsystemModuleMapLogic;
import cn.jikon.logic.supermanage.SubSystemLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxin on 2017/4/25.
 */
@Service
public class ModuleService {
    @Autowired
    ModuleLogic moduleLogic;

    @Autowired
    SubSystemLogic subSystemLogic;
    @Autowired
    SubsystemModuleMapLogic subsystemModuleMapLogic;

    public ModuleVo addModule(String name, String describe, String subsystemId) throws BizException {
        checkModuleName(name);
        SubSystem subSystem = subSystemLogic.getSubSystemBySubId(subsystemId);
        if( null == subSystem){
            throw new BizException(EduErrors.CODE_100015, "子系统不存在");
        }
        ModuleVo moduleVo = moduleLogic.addModule(name, describe, subsystemId);
        return moduleVo;
    }

    public void delModule(List<String> moduleIdList) {
        for(String moduleId : moduleIdList){
            moduleLogic.delModule(moduleId);
        }
    }

    public void updateModule(String moduleId, String name, String subsystemId, String describe) throws BizException {
        Module module = checkModule(moduleId);
        if (!name.equals(module.getName())) {
            checkModuleName(name);
        }
        moduleLogic.updateModule(moduleId, name, subsystemId, describe);
    }

    public List<ModuleVo> getModuleList(int start, int pageCount) {
        List<Module> moduleList = moduleLogic.getModuleList(start, pageCount);
        List<ModuleVo> moduleVoList = new ArrayList<ModuleVo>();
        for (Module module : moduleList) {
            ModuleVo moduleVo = new ModuleVo();
            moduleVo.setModuleId(module.getId());
            moduleVo.setName(module.getName());
            moduleVo.setDescribe(module.getDescribe());

            String subId = subsystemModuleMapLogic.getSubIdByModuleId(module.getId());
            if (null != subId) {
                SubSystem subSystem = subSystemLogic.getSubSystemBySubId(subId);
                String subName = subSystem.getName();
                moduleVo.setSubSystemId(subId);
                moduleVo.setSubSystemName(subName);
            }else{
                moduleVo.setSubSystemId("");
                moduleVo.setSubSystemName("");
            }
            moduleVoList.add(moduleVo);
        }
        return moduleVoList;
    }

    public int getModuleCount() {
        return moduleLogic.getModuleCount();
    }

    private void checkModuleName(String name) throws BizException {
        Module module = moduleLogic.getModuleByName(name);
        if(null != module){
            throw new BizException(EduErrors.CODE_100013, "模块名重复");
        }
    }

    private Module checkModule(String moduleId) throws BizException {
        Module module = moduleLogic.getModule(moduleId);
        if(null == module){
            throw new BizException(EduErrors.CODE_100008, "模块不存在");
        }
        return module;
    }

}

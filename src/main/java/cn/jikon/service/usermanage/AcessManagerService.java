package cn.jikon.service.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.entity.managesystem.ManageSystemModuleMap;
import cn.jikon.domain.entity.managesystem.SubSystem;
import cn.jikon.domain.entity.module.Module;
import cn.jikon.domain.entity.module.SubsystemModuleMap;
import cn.jikon.domain.vo.ManUserBindVo;
import cn.jikon.domain.vo.ModuleVo;
import cn.jikon.domain.vo.SubSystemModuleVo;
import cn.jikon.logic.module.ModuleLogic;
import cn.jikon.logic.module.SubsystemModuleMapLogic;
import cn.jikon.logic.supermanage.SubSystemLogic;
import cn.jikon.logic.usermanage.ManageSystemModuleMapLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2017/4/27.
 */
@Service
public class AcessManagerService {

    @Autowired
    private SubSystemLogic subSystemLogic;
    @Autowired
    private SubsystemModuleMapLogic subsystemModuleMapLogic;
    @Autowired
    private ManageSystemModuleMapLogic manageSystemModuleMapLogic;
    @Autowired
    private ModuleLogic moduleLogic;

    public List<ManUserBindVo> getManagerUserBindList(String manageUserId){
        List<ManUserBindVo> manUserBindVoList = new ArrayList<ManUserBindVo>();
        List<SubSystem> systemList = subSystemLogic.getListSubSystem();
        for(SubSystem subSystem : systemList){
            List<SubsystemModuleMap> moduleSubsystemMapList  = subsystemModuleMapLogic.getListModulePrivilegeMapBySubId(subSystem.getId());
            if(moduleSubsystemMapList.size() == 0){
                continue;
            }
            List<SubSystemModuleVo> moduleVoList = new ArrayList<SubSystemModuleVo>();
            for(SubsystemModuleMap moduleSubsystemMap : moduleSubsystemMapList){
                Module module = moduleLogic.getModule(moduleSubsystemMap.getModuleId());
                if(null==module){
                    continue;
                }
                ManageSystemModuleMap manageSystemModuleMap = manageSystemModuleMapLogic.getManageSystemModuleMapByids(manageUserId,moduleSubsystemMap.getId());
                SubSystemModuleVo subSystemModuleVo = new SubSystemModuleVo();
                subSystemModuleVo.setSubsystemModuleId(moduleSubsystemMap.getId());
                subSystemModuleVo.setModeuleName(module.getName());
                if(null != manageSystemModuleMap){
                    subSystemModuleVo.setChecked(true);
                }
                moduleVoList.add(subSystemModuleVo);
            }

            ManUserBindVo manUserBindVo = new ManUserBindVo();
            manUserBindVo.setSubSystemId(subSystem.getId());
            manUserBindVo.setGetSubSystemName(subSystem.getName());
            manUserBindVo.setModuleList(moduleVoList);
            manUserBindVoList.add(manUserBindVo);
        }
        return manUserBindVoList;
    }

    public void bindSubsystemModule(String manageUserId,String subsystemModuleId) throws BizException{
        SubsystemModuleMap moduleSubsystemMap = subsystemModuleMapLogic.getSubsystemModuleMapById(subsystemModuleId);
        if(null == moduleSubsystemMap){
            throw new BizException(EduErrors.CODE_100008,"模块不存在");
        }
        manageSystemModuleMapLogic.bindSubsystemModule(manageUserId, subsystemModuleId);
    }

    public void unbindSubsystemModule(String manageUserId,String subsystemModuleId){
        manageSystemModuleMapLogic.unbindSubsystemModule(manageUserId, subsystemModuleId);
    }
}

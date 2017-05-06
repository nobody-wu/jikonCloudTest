package cn.jikon.logic.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.domain.dao.usermanage.ModulePrivilegeMapDao;
import cn.jikon.domain.entity.managesystem.ModulePrivilegeMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wujiapeng on 17/4/5.
 */
@Service
public class ModulePrivilegeMapLogic {

    @Resource
    private ModulePrivilegeMapDao modulePrivilegeMapDao;

    public List<ModulePrivilegeMap> getsByPriId(String priId) throws BizException {
        return modulePrivilegeMapDao.getsByPriId(priId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMolPriMapInfoByPriIdList(String priId) throws BizException{
        modulePrivilegeMapDao.deleteMolPriMapInfoByPriIdList(priId);
    }

    public List<String> getModuleIdListByPriId(String privilegeId){
        return modulePrivilegeMapDao.getModuleIdListByPriId(privilegeId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addModulePrivilegeMap(List<Map<String, Object>> params){
        modulePrivilegeMapDao.addModulePrivilegeMap(params);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteModulePrivilegeMapByPriId(List<String> moduleIdList, String privilegeId){
        modulePrivilegeMapDao.deleteModulePrivilegeMapByPriId(moduleIdList, privilegeId);
    }
}

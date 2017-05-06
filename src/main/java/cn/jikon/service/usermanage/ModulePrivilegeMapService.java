package cn.jikon.service.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.domain.entity.managesystem.ModulePrivilegeMap;
import cn.jikon.logic.usermanage.ModulePrivilegeMapLogic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wujiapeng on 17/4/5.
 */
@Service
public class ModulePrivilegeMapService {

    @Resource
    private ModulePrivilegeMapLogic modulePrivilegeMapLogic;

    public Boolean validModulePriMapByPriId(String priId) throws BizException {
        List<ModulePrivilegeMap> modulePrivilegeMapList =
                modulePrivilegeMapLogic.getsByPriId(priId);
        if(null == modulePrivilegeMapList || modulePrivilegeMapList.isEmpty()){
            //todo 只需要打印日志
            //throw new BizException(EduErrors.CODE_100004, "权限Id:"+priId+",查无模块与权限关系信息");
            return false;
        }
        return true;
    }

    public void deleteMolPriMapInfoByPriIdList(String priId) throws BizException{
        modulePrivilegeMapLogic.deleteMolPriMapInfoByPriIdList(priId);
    }
}

package cn.jikon.service.supermanager;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.common.util.UKID;
import cn.jikon.domain.entity.managesystem.SubSystem;
import cn.jikon.domain.entity.module.SubsystemModuleMap;
import cn.jikon.logic.module.SubsystemModuleMapLogic;
import cn.jikon.logic.supermanage.SubSystemLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wujiapeng on 17/4/25.
 */
@Service
public class SuperManageUserService {

    @Autowired
    private SubSystemLogic subSystemLogic;
    @Autowired
    private SubsystemModuleMapLogic subsystemModuleMapLogic;

    /**
     * 查询子系统信息
     * @return
     * @throws BizException
     */
    public List<SubSystem> getListSubSystem() throws Exception{
        return subSystemLogic.getListSubSystem();
    }

    /**
     * 添加子系统信息
     * @param subName
     * @param subCode
     * @param subDescribe
     * @throws Exception
     */
    public void addSubSystem(String subName, String subCode, String subDescribe) throws Exception{
        validSubSystemBySubCode(subCode);
        String subId = UKID.getGeneratorID();
        subSystemLogic.addSubSystem(subId, subName, subCode, subDescribe);

    }

    /**
     * 修改子系统信息
     * @param subId
     * @param subName
     * @param subDescribe
     * @param subCode
     * @throws BizException
     */
    public void modifySubSystemBySubId(String subId, String subName, String subCode, String subDescribe) throws Exception{
        validSubSystemBySubId(subId);
        subSystemLogic.modifySubSystemBySubId(subId, subName, subCode, subDescribe);
    }

    /**
     * 批量删除子系统信息
     * @param subIdList
     * @throws Exception
     */
    public void deleteSubSystemBySubIdList(List<String> subIdList) throws Exception{
        subSystemLogic.deleteSubSystemBySubIdList(subIdList);
    }


    /**
     * 校验当前子系统是否存在
     * @param subId
     * @throws BizException
     */
    private void validSubSystemBySubId(String subId) throws Exception{
        SubSystem subSystem = subSystemLogic.getSubSystemBySubId(subId);
        if (null == subSystem){
            throw new BizException(1, "未查询到当前子系统subId:"+subId+"的信息");
        }
    }

    /**
     * 校验当前子系统是否存在
     * @param subCode
     * @throws BizException
     */
    private void validSubSystemBySubCode(String subCode) throws Exception{
        SubSystem subSystem = subSystemLogic.getSubSystemBySubCode(subCode);
        if (null != subSystem){
            throw new BizException(1, "当前子系统编码subCode:"+subCode+"已存在,不可重复添加");
        }
    }

    /**
     * 校验子系统信息以及删除关系表信息
     * @param subId
     * @throws Exception
     */
    public void validSubSystemAndDeleteMap(String subId) throws Exception{
        //校验子系统ID是否存在信息
        vaildSubSystemById(subId);
        //校验子系统是否拥有模块信息，只有存在再进行删除，不会浪费资源
        List<SubsystemModuleMap> moduleSubsystemMapList = subsystemModuleMapLogic.getListModulePrivilegeMapBySubId(subId);
        Boolean flag = (null == moduleSubsystemMapList || moduleSubsystemMapList.isEmpty()) ? Boolean.FALSE:Boolean.TRUE;
        if (flag) {
            subsystemModuleMapLogic.deleteModulePriMapInfoBySubId(subId);
        }
    }

    private void vaildSubSystemById(String subId) throws Exception{
        SubSystem subSystem = subSystemLogic.getSubSystemBySubId(subId);
        if (null == subSystem){
            throw new BizException(EduErrors.CODE_100008, "查无子系统信息");
        }
    }

}

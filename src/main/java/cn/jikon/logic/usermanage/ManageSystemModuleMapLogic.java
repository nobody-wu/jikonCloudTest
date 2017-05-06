package cn.jikon.logic.usermanage;

import cn.jikon.common.util.UKID;
import cn.jikon.domain.dao.usermanage.ManageSystemModuleMapDao;
import cn.jikon.domain.entity.managesystem.ManageSystemModuleMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zl on 2017/4/27.
 */
@Service
public class ManageSystemModuleMapLogic {

    @Autowired
    ManageSystemModuleMapDao manageSystemModuleMapDao;

    public ManageSystemModuleMap getManageSystemModuleMapByids(String manUserId,String subsystemModuleId){
        return manageSystemModuleMapDao.getManageSystemModuleMapByids(manUserId,subsystemModuleId);
    }

    @Transactional
    public void bindSubsystemModule(String manageUserId,String subsystemModuleId){
        manageSystemModuleMapDao.unbindSubsystemModule(manageUserId,subsystemModuleId);
        String id = UKID.getGeneratorID();
        manageSystemModuleMapDao.bindSubsystemModule(id,manageUserId,subsystemModuleId);
    }

    public void unbindSubsystemModule(String manageUserId,String subsystemModuleId){
        manageSystemModuleMapDao.unbindSubsystemModule(manageUserId,subsystemModuleId);
    }
}

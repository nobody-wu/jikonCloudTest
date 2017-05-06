package cn.jikon.domain.dao.usermanage;

import cn.jikon.domain.entity.managesystem.ManageSystemModuleMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by zl on 2017/4/27.
 */
@Repository
public interface ManageSystemModuleMapDao {

    ManageSystemModuleMap getManageSystemModuleMapByids(@Param("manageUserId") String manageUserId, @Param("subsystemModuleId") String subsystemModuleId);

    void bindSubsystemModule(@Param("id") String id, @Param("manageUserId") String manageUserId, @Param("subsystemModuleId") String subsystemModuleId);

    void unbindSubsystemModule(@Param("manageUserId") String manageUserId, @Param("subsystemModuleId") String subsystemModuleId);
}

package cn.jikon.domain.dao.module;

import cn.jikon.domain.entity.module.SubsystemModuleMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxin on 2017/4/25.
 */
@Repository
public interface SubsystemModuleMapDao {
    void addSubsystemModuleMap(SubsystemModuleMap subsystemModuleMap);

    void delSubsystemModuleMapById(@Param("moduleId") String moduleId);

    void updateSubsystemModuleMap(@Param("moduleId") String moduleId,@Param("subsystemId")String subsystemId,@Param("describe")String describe);

    List<String> getModuleIdList(@Param("subsystemId") String subsystemId);

    String getSubIdByModuleId(@Param("moduleId") String moduleId);

    List<SubsystemModuleMap> getListModulePrivilegeMapBySubId(@Param("subId")String subId);

    void deleteModulePriMapInfoBySubId(@Param("subId")String subId);

    SubsystemModuleMap getSubsystemModuleMapById(String id);
}

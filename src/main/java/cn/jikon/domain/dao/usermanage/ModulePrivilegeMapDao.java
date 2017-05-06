package cn.jikon.domain.dao.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.domain.entity.managesystem.ModulePrivilegeMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wujiapeng on 17/4/5.
 */
@Repository
public interface ModulePrivilegeMapDao {

    /**
     * 根据权限ID查询权限－模块关系表信息
     * @param priId
     * @return
     * @throws BizException
     */
    public List<ModulePrivilegeMap> getsByPriId(@Param("privilegeId")String priId) throws BizException;

    /**
     * 批量删除权限和角色关系表信息
     * @param priId
     * @return
     * @throws BizException
     */
    public void deleteMolPriMapInfoByPriIdList(@Param("priId")String priId) throws BizException;

    public List<String> getModuleIdListByPriId(@Param("modPrivilegeId")String modPrivilegeId);

    public void addModulePrivilegeMap(@Param("params") List<Map<String, Object>> params);

    public void deleteModulePrivilegeMapByPriId(@Param("moduleIdList") List<String> moduleIdList, @Param("privilegeId") String privilegeId);

}


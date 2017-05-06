package cn.jikon.domain.dao.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.domain.entity.managesystem.PrivilegeRoleMap;
import cn.jikon.domain.vo.PageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wujiapeng on 17/4/5.
 */
@Repository
public interface PrivilegeRoleMapDao {

    /**
     * 分页查询权限角色关系表信息
     * @param pageVo
     * @return
     * @throws BizException
     */
    List<PrivilegeRoleMap> getListPrivilegeRoleMap(PageVo pageVo) throws BizException;

    /**
     * 根据权限ID查询关系表信息
     * @param priId
     * @return
     * @throws BizException
     */
    List<PrivilegeRoleMap> getPrivilegeRoleMapByPriId(@Param("priId")String priId) throws BizException;

    /**
     * 根据角色ID查询关系表信息
     * @param priRoleId
     * @return
     * @throws BizException
     */
    List<PrivilegeRoleMap> getPrivilegeRoleMapByRolId(@Param("priRoleId")String priRoleId) throws BizException;

    /**
     * 删除当前用户的权限－角色关系信息
     * @param priId
     * @return
     * @throws BizException
     */
    void deletePriRoleMapInfoById(@Param("priId")String priId,
                                  @Param("roleId")String roleId) throws BizException;

    /**
     * 获取权限角色关系表列表总条数
     * @return
     * @throws BizException
     */
    int getPrivilegeRoleMapCount() throws BizException;

    /**
     * 根据关系表ID删除权限角色关系信息
     * @param privilegeIdList
     * @param priRoleId
     * @return
     * @throws BizException
     */
    void deletePrivilegeRoleMapByPriId(@Param("privilegeIdList") List<String> privilegeIdList, @Param("priRoleId") String priRoleId) throws BizException;

    /**
     * 绑定权限和角色关系信息
     * @return
     * @throws BizException
     */
    void addPrivilegeRoleMap(@Param("params") List<Map<String, Object>> params) throws BizException;

    /**
     * 更新权限角色关系信息
     * @param priId
     * @param priPrivilegeId
     * @param priRoleId
     * @param priDescribe
     * @return
     * @throws BizException
     */
    void updatePrivilegeRoleMapByPriId(@Param("priId") String priId,
                                      @Param("priPrivilegeId") String priPrivilegeId,
                                      @Param("priRoleId") String priRoleId,
                                      @Param("priDescribe") String priDescribe) throws BizException;

    /**
     * 根据权限ID／角色ID查询权限角色的关系表信息
     * @param priId
     * @param roleId
     * @return
     * @throws BizException
     */
    PrivilegeRoleMap getByPriIdAndRolId(@Param("priId")String priId,
                                        @Param("roleId")String roleId) throws BizException;

    /**
     * 根据角色ID查询所拥有的权限ID
     * @param priRoleId
     * @return
     * @throws BizException
     */
    List<String> getListPriIdByRoleId(@Param("priRoleId")String priRoleId) throws BizException;
}

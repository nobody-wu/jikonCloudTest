package cn.jikon.domain.dao.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.domain.entity.managesystem.Role;
import cn.jikon.domain.vo.PageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wujiapeng on 17/4/3.
 */
@Repository
public interface RoleDao {

    /**
     * 查询角色列表
     * @return
     * @throws BizException
     */
    List<Role> getListAllRole() throws BizException;

    /**
     * 分页查询角色列表
     * @param pageIndex
     * @return
     * @throws BizException
     */
    List<Role> getsByParams(PageVo pageIndex) throws BizException;

    /**
     * 获取当前角色列表总数目
     * @return
     * @throws BizException
     */
    int getRoleCount() throws BizException;

    /**
     * 根据角色ID查询角色
     * @param rolId
     * @return
     * @throws BizException
     */
    Role getById(@Param("rolId") String rolId) throws BizException;

    /**
     * 修改角色信息
     * @param rolId
     * @param rolName
     * @param rolDescribe
     * @return
     * @throws BizException
     */
    void modifyRoleByRolId(@Param("rolId") String rolId,
                   @Param("rolName") String rolName,
                   @Param("rolDescribe") String rolDescribe) throws BizException;

    /**
     * 添加角色信息
     * @param rolId
     * @param rolName
     * @param rolDescribe
     * @return
     * @throws BizException
     */
    void addRole(@Param("rolId") String rolId,
                @Param("rolName") String rolName,
                @Param("rolDescribe") String rolDescribe) throws BizException;

    /**
     * 批量删除角色信息
     * @param rolIdList
     * @return
     * @throws BizException
     */
    void deleteRoleByRolIdList(@Param("rolIdList") List<String> rolIdList) throws BizException;

    /**
     * 根据角色ID列表批量查询角色信息
     * @param roleIdList
     * @return
     * @throws BizException
     */
    List<Role> getListByRoleIdList(@Param("roleIdList")List<String> roleIdList) throws BizException;
}

package cn.jikon.domain.dao.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.domain.entity.managesystem.Privilege;
import cn.jikon.domain.entity.managesystem.PrivilegeRoleMap;
import cn.jikon.domain.vo.PageVo;
import cn.jikon.domain.vo.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zl on 2017/3/29. 权限管理
 */
@Repository
public interface PrivilegeDao {

    /**
     * 根据Id查询权限
     * @param priId
     * @return
     */
    Privilege getById(@Param("priId") String priId);

    /**
     * 获取当前权限列表总数目
     * @return
     * @throws BizException
     */
    int getPrivilegeCount() throws BizException;

    /**
     * 分页查询权限列表
     * @param pageIndex
     * @return
     * @throws BizException
     */
    List<Privilege> getsByParams(PageVo pageIndex) throws BizException;

    /**
     * 查询权限列表
     * @return
     * @throws BizException
     */
    List<Privilege> getListAllPrivilege() throws BizException;

    /**
     * 根据权限ID集合查询权限信息
     * @param priIdList
     * @return
     * @throws BizException
     */
    List<Privilege> getListByPriIdList(@Param("priIdList")List<String> priIdList) throws BizException;

    /**
     * 修改权限信息
     * @param priId
     * @param priName
     * @param priDescribe
     * @return
     * @throws BizException
     */
    void modifyPrivilegeByPriId(@Param("priId")String priId, @Param("priName")String priName,
                        @Param("priDescribe")String priDescribe) throws BizException;

    /**
     * 批量删除权限信息
     * @param priId
     * @return
     * @throws BizException
     */
    void deletePrivilegeByPriIdList(@Param("priId")List priId) throws BizException;

    /**
     * 增加权限信息
     * @param priId
     * @param priName
     * @param priDescribe
     * @return
     * @throws BizException
     */
    void addPrivilege(@Param("priId") String priId,
                     @Param("priName") String priName,
                     @Param("priDescribe") String priDescribe)throws BizException;

    /**
     * 获取权限列表
     */
    List<UserRole> getUserRoleList();

    /**
     * 添加角色接口
     */
    @Transactional(rollbackFor=Exception.class)
    void addRole(PrivilegeRoleMap privilegeRoleMap);

    /**
     * 修改角色权限接口
     * @param id
     * @param privilegeId
     */
    void updateRoleAndPrivilege(String id,String privilegeId);

    /**
     * 获取权限列表
     * @return
     */
    List<Privilege> getPrivilegeList();

    /**
     * 添加权限
     * @param name
     * @param describe
     */
    void addPrivile(String name,String describe);

    /**
     * 添加权限功能接口
     * @param list
     */
    void addPrivileDetail(List<Privilege> list);

    /**
     * 获取权限详情
     * @param id
     */
    void getPrivileDetail(String id);

}

package cn.jikon.domain.dao.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.domain.entity.managesystem.UserRoleMap;
import cn.jikon.domain.vo.PageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wujiapeng on 17/4/5.
 */
@Repository
public interface UserRoleMapDao {

    /**
     * 绑定用户和角色的关系信息
     * @param useId
     * @param useUserId
     * @return
     * @throws BizException
     */
    void addUserRoleMap(@Param("useId") String useId,
                        @Param("useUserId") String useUserId,
                        @Param("roleIdList") List<String> roleIdList) throws BizException;

    /**
     * 根据角色ID和用户ID删除用户和角色的关系信息
     * @param roleIdList
     * @param useUserId
     * @return
     * @throws BizException
     */
    void deleteUserRoleMapByUseId(@Param("useRoleIdList") List<String> roleIdList,
                                  @Param("useUserId") String useUserId) throws BizException;

    /**
     * 更新用户和角色的关系信息
     * @param useId
     * @param useUserId
     * @param useRoleId
     * @param useDescribe
     * @return
     * @throws BizException
     */
    void updateUserRoleMapByUseId(@Param("useId") String useId,
                                 @Param("useUserId") String useUserId,
                                 @Param("useRoleId") String useRoleId,
                                 @Param("useDescribe") String useDescribe) throws BizException;

    /**
     * 查询信息总条数
     * @return
     * @throws BizException
     */
    int getUserRoleMapCount() throws BizException;

    /**
     * 分页查询用户和角色的关系信息
     * @return
     * @throws BizException
     */
    List<UserRoleMap> getListUserRoleMap(PageVo pageVo) throws BizException;

    /**
     * 根据角色ID删除关系表信息
     * @param roleId
     * @return
     * @throws BizException
     */
    void deleteUserRoleMapByRolId(@Param("roleId")String roleId) throws BizException;

    /**
     * 根据角色ID查询用户与角色关系表信息
     * @param roleId
     * @return
     * @throws BizException
     */
    List<UserRoleMap> getsByRolId(@Param("roleId")String roleId) throws BizException;

    UserRoleMap getByUserIdAndRolId(@Param("userId")String userId,
                                    @Param("roleId")String roleId) throws BizException;

    List<String> getRolIdListByUserId(@Param("userId")String userId) throws BizException;

    /**
     * 根据userID查询用户的角色id
     * @param useUserId
     * @return
     * @throws BizException
     */
    List<String> getListRoleIdByUserId(@Param("useUserId")String useUserId) throws BizException;
}

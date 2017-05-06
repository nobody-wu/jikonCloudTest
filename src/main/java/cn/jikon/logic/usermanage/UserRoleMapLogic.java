package cn.jikon.logic.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.domain.dao.usermanage.UserRoleMapDao;
import cn.jikon.domain.entity.managesystem.UserRoleMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wujiapeng on 17/4/5.
 */
@Service
public class UserRoleMapLogic {

    @Resource
    private UserRoleMapDao userRoleMapDao;

    public void addUserRoleMap(String useId, String useUserId, List<String> roleIdList) throws BizException {
        userRoleMapDao.addUserRoleMap(useId, useUserId, roleIdList);
    }

    public void deleteUserRoleMapByUseId(List<String> roleIdList, String userId) throws BizException {
        userRoleMapDao.deleteUserRoleMapByUseId(roleIdList, userId);
    }

    public void updateUserRoleMapByUseId(String useId, String useUserId,
                                        String useRoleId, String useDescribe) throws BizException {
        userRoleMapDao.updateUserRoleMapByUseId(useId, useUserId, useRoleId, useDescribe);
    }

    public int getUserRoleMapCount() throws BizException{
        return userRoleMapDao.getUserRoleMapCount();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRoleMapByRolId(String roleId) throws BizException{
        userRoleMapDao.deleteUserRoleMapByRolId(roleId);
    }

    public List<UserRoleMap> getsByRolId(String roleId) throws BizException{
        return userRoleMapDao.getsByRolId(roleId);
    }

    public UserRoleMap getByUserIdAndRolId(String userId, String roleId) throws BizException{
        return userRoleMapDao.getByUserIdAndRolId(userId, roleId);
    }

    public List<String> getRolIdListByUserId(String userId) throws BizException{
        return userRoleMapDao.getRolIdListByUserId(userId);
    }

    public List<String> getListRoleIdByUserId(String userId) throws BizException{
        return userRoleMapDao.getListRoleIdByUserId(userId);
    }

}
package cn.jikon.service.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.entity.managesystem.UserRoleMap;
import cn.jikon.logic.usermanage.UserRoleMapLogic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wujiapeng on 17/4/3.
 */
@Service
public class UserRoleMapService {

    @Resource
    private UserRoleMapLogic userRoleMapLogic;

    /**
     * 根据用户ID查询角色ID
     * @return
     * @throws BizException
     */
    public List<String> getRolIdListByUserId(String userId) throws BizException {
        return userRoleMapLogic.getRolIdListByUserId(userId);
    }

//    /**
//     * 分页查询用户与角色绑定的信息
//     * @param pageVo
//     * @return
//     * @throws BizException
//     */
//    public List<UserRoleMap> getListUserRoleMap(PageVo pageVo) throws BizException{
////        return userRoleMapLogic.getListUserRoleMap(pageVo);
//    }／

    /**
     * 查询权限列表总条数
     * @return
     * @throws BizException
     */
    public int getUserRoleMapCount() throws BizException{
        return userRoleMapLogic.getUserRoleMapCount();
    }

    /**
     * 根据角色ID删除关系表信息
     * @param roleId
     * @return
     * @throws BizException
     */
    public void deleteUserRoleMapByRolId(String roleId) throws BizException{
        userRoleMapLogic.deleteUserRoleMapByRolId(roleId);
    }

    /**
     * 更新用户和角色的关系信息
     * @param useId
     * @param useUserId
     * @param useRoleId
     * @param useDescribe
     * @return
     * @throws BizException
     */
    public void updateUserRoleMapByUseId(String useId, String useUserId,
                                        String useRoleId, String useDescribe) throws BizException{
        userRoleMapLogic.updateUserRoleMapByUseId(useId, useUserId, useRoleId, useDescribe);
    }

    /**
     * 根据角色ID查询角色与用户关系表信息
     * @param roleId
     * @return
     * @throws BizException
     */
    public Boolean validUserRoleMapByRolId(String roleId) throws BizException{
        List<UserRoleMap> userRoleMapList = userRoleMapLogic.getsByRolId(roleId);
        if(null == userRoleMapList || userRoleMapList.isEmpty()){
            //todo 只需要打印日志
           //throw new BizException(EduErrors.CODE_100004, "当前角色ID:"+roleId+",查无角色与用户关系表信息");
            return false;
        }
        return true;
    }

    /**
     * 根据用户ID／角色ID，查询用户角色关系表信息
     * @param userId
     * @param roleId
     * @return
     * @throws BizException
     */
    public UserRoleMap getByUserIdAndRolId(String userId, String roleId) throws BizException{
        return userRoleMapLogic.getByUserIdAndRolId(userId, roleId);
    }

    /**
     * 校验用户当前角色信息已存在
     * @param userId
     * @param roleId
     * @throws BizException
     */
    public void vaildUserRoleMapExit(String userId, String roleId) throws BizException{
        UserRoleMap userRoleMap = getByUserIdAndRolId(userId, roleId);
        if (userRoleMap != null) {
            throw new BizException(EduErrors.CODE_100013, "当前用户与角色关系信息已存在,userId:" + userId + ",rolId" + roleId, null);
        }
    }

    /**
     * 校验用户当前角色信息不存在
     * @param userId
     * @param roleId
     * @throws BizException
     */
    public void vaildUserRoleMapNoExit(String userId, String roleId, List<String> roleIdList) throws BizException{
        UserRoleMap userRoleMap = getByUserIdAndRolId(userId, roleId);
        if (userRoleMap == null) {
            //todo 打印日志，不要抛异常
            return;
        }
        roleIdList.add(roleId);
    }

    /**
     * 根据userId查询用户的角色id
     * @param userId
     * @return
     * @throws BizException
     */
    public List<String> getListRoleIdByUserId(String userId) throws BizException{
        return userRoleMapLogic.getListRoleIdByUserId(userId);
    }
}

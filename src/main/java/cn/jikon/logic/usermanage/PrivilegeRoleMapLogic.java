package cn.jikon.logic.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.domain.dao.usermanage.PrivilegeRoleMapDao;
import cn.jikon.domain.entity.managesystem.PrivilegeRoleMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wujiapeng on 17/4/5.
 */
@Service
public class PrivilegeRoleMapLogic {

    @Resource
    private PrivilegeRoleMapDao privilegeRoleMapDao;

    public List<PrivilegeRoleMap> getPrivilegeRoleMapByPriId(String priId) throws BizException {
        return privilegeRoleMapDao.getPrivilegeRoleMapByPriId(priId);
    }

    public List<PrivilegeRoleMap> getPrivilegeRoleMapByRolId(String roleId) throws BizException{
        return privilegeRoleMapDao.getPrivilegeRoleMapByRolId(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePriRoleMapInfoById(String priId, String roleId) throws BizException{
        privilegeRoleMapDao.deletePriRoleMapInfoById(priId, roleId);
    }

    public int getPrivilegeRoleMapCount() throws BizException{
        return privilegeRoleMapDao.getPrivilegeRoleMapCount();
    }

    public void deletePrivilegeRoleMapByPriId(List<String> privilegeIdList, String roleId) throws BizException{
        privilegeRoleMapDao.deletePrivilegeRoleMapByPriId(privilegeIdList, roleId);
    }

    public void addPrivilegeRoleMap(List<Map<String, Object>> params) throws BizException{
        privilegeRoleMapDao.addPrivilegeRoleMap(params);
    }

    public void updatePrivilegeRoleMapByPriId(String priId, String priPrivilegeId,
                                             String priRoleId, String priDescribe) throws BizException{
        privilegeRoleMapDao.updatePrivilegeRoleMapByPriId(priId, priPrivilegeId, priRoleId, priDescribe);
    }

    public PrivilegeRoleMap getByPriIdAndRolId(String priId, String rolId) throws BizException{
        return privilegeRoleMapDao.getByPriIdAndRolId(priId, rolId);
    }

    public List<String> getListPriIdByRoleId(String roleId) throws BizException{
        return privilegeRoleMapDao.getListPriIdByRoleId(roleId);
    }
}

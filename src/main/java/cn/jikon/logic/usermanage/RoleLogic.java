package cn.jikon.logic.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.domain.dao.usermanage.RoleDao;
import cn.jikon.domain.entity.managesystem.Role;
import cn.jikon.domain.vo.PageVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wujiapeng on 17/4/3.
 */
@Service
public class RoleLogic {

    @Resource
    private RoleDao roleDao;

    public List<Role> getListAllRole() throws BizException{
        return roleDao.getListAllRole();
    }

    public List<Role> getsByParams(PageVo pageVo) throws BizException {
        return roleDao.getsByParams(pageVo);
    }

    public int getRoleCount() throws BizException{
        return roleDao.getRoleCount();
    }

    public Role getById(String roleId) throws BizException{
        return roleDao.getById(roleId);
    }

    public void modifyRoleByRolId(String rolId, String rolName, String rolDescribe) throws BizException{
        roleDao.modifyRoleByRolId(rolId, rolName, rolDescribe);
    }

    public void addRole(String rolId, String rolName, String rolDescribe) throws BizException{
        roleDao.addRole(rolId, rolName, rolDescribe);
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteRoleByRolIdList(List<String> rolIdList) throws BizException{
        roleDao.deleteRoleByRolIdList(rolIdList);
    }

    public List<Role> getListByRoleIdList(List<String> roleIdList) throws BizException{
        return roleDao.getListByRoleIdList(roleIdList);
    }
}

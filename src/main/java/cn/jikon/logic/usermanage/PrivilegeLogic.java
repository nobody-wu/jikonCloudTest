package cn.jikon.logic.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.dao.usermanage.PrivilegeDao;
import cn.jikon.domain.entity.managesystem.Privilege;
import cn.jikon.domain.entity.managesystem.PrivilegeRoleMap;
import cn.jikon.domain.vo.PageVo;
import cn.jikon.domain.vo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zl on 2017/3/29.
 */
@Service
public class PrivilegeLogic {

    @Autowired
    PrivilegeDao privilegeDao;

    public List<UserRole> getUserRoleList(){
        return privilegeDao.getUserRoleList();
    }

    public void addRole(String Id,String name,String descripe,String privilegeId) throws BizException {
        try {
            PrivilegeRoleMap privilegeRoleMap = new PrivilegeRoleMap();
            privilegeRoleMap.setRoleId(Id);
            privilegeRoleMap.setDescribe(descripe);
            privilegeRoleMap.setPrivilegeId(privilegeId);
            privilegeDao.addRole(privilegeRoleMap);
        }catch(Exception e){
            throw new BizException(EduErrors.CODE_100001,e.getMessage());
        }
    }

    public List<Privilege> getsByParams(PageVo pageVo) throws BizException{
        return privilegeDao.getsByParams(pageVo);
    }

    public List<Privilege> getListAllPrivilege() throws BizException{
        return privilegeDao.getListAllPrivilege();
    }

    public List<Privilege> getListByPriIdList(List<String> priIdList) throws BizException{
        return privilegeDao.getListByPriIdList(priIdList);
    }

    public int getPrivilegeCount() throws BizException{
        return privilegeDao.getPrivilegeCount();
    }

    public void modifyPrivilegeByPriId(String priId, String priName, String priDescribe) throws BizException{
        privilegeDao.modifyPrivilegeByPriId(priId, priName, priDescribe);
    }

    @Transactional(rollbackFor=Exception.class)
    public void deletePrivilegeByPriIdList(List priIdList) throws BizException{
        privilegeDao.deletePrivilegeByPriIdList(priIdList);
    }

    public void addPrivilege(String priId, String priName, String priDescribe) throws BizException{
        privilegeDao.addPrivilege(priId, priName, priDescribe);
    }

    public Privilege getById(String priId) throws BizException{
        return privilegeDao.getById(priId);
    }
}

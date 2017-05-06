package cn.jikon.service.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.common.util.UKID;
import cn.jikon.domain.entity.managesystem.Privilege;
import cn.jikon.domain.vo.PageVo;
import cn.jikon.domain.vo.UserRole;
import cn.jikon.logic.usermanage.PrivilegeLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/3/29.
 */
@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeLogic privilegeLogic;

    public List<UserRole> getUserRoleList(){
        return privilegeLogic.getUserRoleList();
    }

    public void addRole(String Id,String name,String descripe,String privilegeId) throws BizException {
        privilegeLogic.addRole(Id, name, descripe, privilegeId);
    }

    /**
     * 分页查询权限总列表
     * @param pageVo
     * @return
     * @throws BizException
     */
    public List<Privilege> getsByParams(PageVo pageVo) throws BizException {
        return privilegeLogic.getsByParams(pageVo);
    }

    /**
     * 查询权限列表
     * @return
     * @throws Exception
     */
    public List<Privilege> getListAllPrivilege() throws BizException{
        return privilegeLogic.getListAllPrivilege();
    }

    /**
     * 根据权限ID集合查询权限信息
     * @param priIdList
     * @return
     * @throws BizException
     */
    public List<Privilege> getListByPriIdList(List<String> priIdList) throws BizException{
        return privilegeLogic.getListByPriIdList(priIdList);
    }

    /**
     * 修改权限信息
     * @param priName
     * @param priDescribe
     * @return
     * @throws BizException
     */
    public void modifyPrivilegeByPriId(String priId, String priName,
                                       String priDescribe) throws BizException{
         privilegeLogic.modifyPrivilegeByPriId(priId, priName, priDescribe);
    }

    /**
     * 查询权限列表总条数
     * @return
     * @throws BizException
     */
    public int getPrivilegeCount() throws BizException{
        return privilegeLogic.getPrivilegeCount();
    }

    /**
     * 批量删除权限
     * @param priIdList
     * @return
     * @throws BizException
     */
    public void deletePrivilegeByPriIdList(List<String> priIdList) throws BizException{
        privilegeLogic.deletePrivilegeByPriIdList(priIdList);
    }

    /**
     * 增加权限
     * @param priName
     * @param priDescribe
     * @return
     * @throws BizException
     */
    public void addPrivilege(String priName, String priDescribe) throws BizException{
        String priId = UKID.getGeneratorID();
        //校验
        validPrivilegeExsitById(priId);
        privilegeLogic.addPrivilege(priId, priName, priDescribe);
    }


    /**
     * 校验当前权限已存在
     * @param priId
     * @return
     * @throws BizException
     */
    public void validPrivilegeExsitById(String priId) throws BizException{
        Privilege pri = privilegeLogic.getById(priId);
        if (pri != null) {
            throw new BizException(EduErrors.CODE_100013, "当前权限：" + priId + "已存在，不可重复添加");
        }
    }

    /**
     * 校验权限
     * @param priId
     * @throws BizException
     */
    public void vaildPrivilegeById(String priId) throws BizException{
        getPrivilegeById(priId);
    }

    /**
     * 根据权限ID查找权限
     * @param priId
     * @return
     * @throws BizException
     */
    public Privilege getPrivilegeById(String priId) throws BizException{
        Privilege privilege = privilegeLogic.getById(priId);
        if(null == privilege){
            throw new BizException(EduErrors.CODE_100004, "权限Id:"+priId+",查无权限");
        }
        return privilege;
    }

}

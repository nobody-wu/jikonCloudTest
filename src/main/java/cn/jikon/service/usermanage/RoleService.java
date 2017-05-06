package cn.jikon.service.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.common.util.UKID;
import cn.jikon.domain.entity.managesystem.Role;
import cn.jikon.domain.vo.PageVo;
import cn.jikon.logic.usermanage.RoleLogic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wujiapeng on 17/4/3.
 */
@Service
public class RoleService {

    @Resource
    private RoleLogic roleLogic;

    /**
     * 分页查询角色列表
     * @param pageVo
     * @return
     * @throws BizException
     */
    public List<Role> getsByParams(PageVo pageVo)throws BizException {
        return roleLogic.getsByParams(pageVo);
    }

    /**
     * 查询角色列表
     * @return
     * @throws BizException
     */
    public List<Role> getListAllRole() throws BizException{
        return roleLogic.getListAllRole();
    }

    /**
     * 查询角色列表总条数
     * @return
     * @throws BizException
     */
    public int getRoleCount() throws BizException{
        return roleLogic.getRoleCount();
    }

    /**
     * 修改角色信息
     * @param rolId
     * @param rolName
     * @param rolDescribe
     * @return
     * @throws BizException
     */
    public void modifyRoleByRolId(String rolId, String rolName, String rolDescribe) throws BizException{
        //校验权限是否存在
        vaildRoleById(rolId);
        roleLogic.modifyRoleByRolId(rolId, rolName, rolDescribe);
    }


    /**
     * 校验角色已存在
     * @param roleId
     * @return
     * @throws BizException
     */
    public void validRoleExsitById(String roleId) throws BizException{
        Role role =  roleLogic.getById(roleId);
        if (role != null) {
            throw new BizException(EduErrors.CODE_100013,
                    "当前角色id:" + role.getId() + "已存在，不可重复添加");
        }
    }

    /**
     * 校验角色信息
     * @param roleId
     * @throws BizException
     */
    public void vaildRoleById(String roleId) throws BizException{
        getById(roleId);
    }

    /**
     * 根据角色ID查找角色
     * @param roleId
     * @return
     * @throws BizException
     */
    public Role getById(String roleId) throws BizException{
        Role role = roleLogic.getById(roleId);
        if(null == role){
            throw new BizException(EduErrors.CODE_100004, "角色Id:"+roleId+",查无角色信息");
        }
        return role;
    }

    /**
     * 添加角色
     * @param rolName
     * @param rolDescribe
     * @return
     * @throws BizException
     */
    public void addRole(String rolName, String rolDescribe) throws BizException{
        String rolId = UKID.getGeneratorID();
        validRoleExsitById(rolId);
        roleLogic.addRole(rolId, rolName, rolDescribe);
    }

    /**
     * 删除角色
     * @param rolIdList
     * @return
     * @throws BizException
     */
    public void deleteRoleByRolIdList(List<String> rolIdList) throws BizException{
        roleLogic.deleteRoleByRolIdList(rolIdList);
    }

    /**
     * 根据ID列表批量查询角色信息
     * @param roleIdList
     * @return
     * @throws BizException
     */
    public List<Role> getListByRoleIdList(List<String> roleIdList) throws BizException{
        return roleLogic.getListByRoleIdList(roleIdList);
    }

}

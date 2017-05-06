package cn.jikon.service.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.entity.managesystem.PrivilegeRoleMap;
import cn.jikon.logic.usermanage.PrivilegeRoleMapLogic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wujiapeng on 17/4/5.
 */
@Service
public class PrivilegeRoleMapService {

    @Resource
    private PrivilegeRoleMapLogic privilegeRoleMapLogic;

    /**
     * 根据权限ID查询权限与角色关系信息
     * @param priId 权限ID
     * @return
     * @throws BizException
     */
    public Boolean validPrivilegeRoleMapByPriId(String priId) throws BizException {
        List<PrivilegeRoleMap> privilegeRoleMapList =
                privilegeRoleMapLogic.getPrivilegeRoleMapByPriId(priId);
//        if(null == privilegeRoleMapList || privilegeRoleMapList.isEmpty()){
//            //todo 只需要打印日志
//            //throw new BizException(EduErrors.CODE_100004, "当前权限："+priId+",并没有授权给任何人");
//            return false;
//        }
//        return true;
        //todo 虽然没打日志，但是换一种写法
        return null == privilegeRoleMapList || privilegeRoleMapList.isEmpty() ?
                Boolean.FALSE:Boolean.TRUE;
    }

    /**
     * 查询当前角色角色是否拥有权限
     * @param rolId 角色ID
     * @return
     * @throws BizException
     */
    public Boolean validPrivilegeRoleMapByRolId(String rolId) throws BizException{
        List<PrivilegeRoleMap> privilegeRoleMapList =
                privilegeRoleMapLogic.getPrivilegeRoleMapByRolId(rolId);
        if(null == privilegeRoleMapList || privilegeRoleMapList.isEmpty()){
            //todo 只需要打印日志
            //throw new BizException(EduErrors.CODE_100004, "当前权限："+priId+",并没有授权给任何人");
            return false;
        }
        return true;
    }

    /**
     * 删除当前用户权限－角色关系表信息
     * 这里其实需要拆分为两个方法的，但是为了节省代码，因为传到sql前所有步骤都是一致的，只是字段不同
     * 在mybatis里做sql判断好了。
     *
     * @param priId 权限ID 可为空
     * @param roleId 角色ID 可为空
     * @return
     * @throws BizException
     */
    public void deletePriRoleMapInfoById(String priId, String roleId) throws BizException{
        privilegeRoleMapLogic.deletePriRoleMapInfoById(priId, roleId);
    }


    /**
     * 分页查询用户与角色绑定的信息
     * @param pageVo
     * @return
     * @throws BizException
     */
//    public List<PrivilegeRoleMap> getListPrivilegeRoleMap(PageVo pageVo) throws BizException{
//        return privilegeRoleMapLogic.getListPrivilegeRoleMap(pageVo);
//    }

    /**
     * 查询权限－角色关系列表总条数
     * @return
     * @throws BizException
     */
    public int getPrivilegeRoleMapCount() throws BizException{
        return privilegeRoleMapLogic.getPrivilegeRoleMapCount();
    }

    /**
     * 更新权限和角色的关系信息
     * @param priId
     * @param priPrivilegeId
     * @param priRoleId
     * @param priDescribe
     * @return
     * @throws BizException
     */
    public void updatePrivilegeRoleMapByPriId(String priId, String priPrivilegeId,
                                             String priRoleId, String priDescribe) throws BizException{
        privilegeRoleMapLogic.updatePrivilegeRoleMapByPriId(priId, priPrivilegeId, priRoleId, priDescribe);
    }

    /**
     * 根据权限ID／角色ID查询权限角色关系表信息
     * @param priId
     * @param roleId
     * @return
     * @throws BizException
     */
    public PrivilegeRoleMap getByPriIdAndRolId(String priId, String roleId) throws BizException{
        return privilegeRoleMapLogic.getByPriIdAndRolId(priId, roleId);
    }

    /**
     * 校验关系表已经存在
     * @param roleId
     * @param privilegeId
     * @param privilegeIdList
     * @throws BizException
     */
    public void vaildPriRoleMapByPriIdAndRolId(String roleId, String privilegeId, List<String> privilegeIdList) throws BizException {
        PrivilegeRoleMap privilegeRoleMap = getByPriIdAndRolId(privilegeId, roleId);
        if (null != privilegeRoleMap) {
//            return;
            throw new BizException(EduErrors.CODE_100013, "当前权限与角色关系信息已存在,privilegeId:" + privilegeId + ",roleId" + roleId, null);
        }
        privilegeIdList.add(privilegeId);
    }

    /**
     * 根据角色ID查询所拥有的权限ID
     * @return
     * @throws BizException
     */
    public List<String> getListPriIdByRoleId(String roleId) throws BizException{
        return privilegeRoleMapLogic.getListPriIdByRoleId(roleId);
    }

}

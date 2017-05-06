package cn.jikon.service.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.common.util.UKID;
import cn.jikon.domain.entity.managesystem.*;
import cn.jikon.domain.entity.module.Module;
import cn.jikon.domain.vo.PageVo;
import cn.jikon.logic.module.ModuleLogic;
import cn.jikon.logic.user.UserLogic;
import cn.jikon.logic.usermanage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wujiapeng on 17/5/3.
 */
@Service
public class PrivilegeManageService {
    @Autowired
    private PrivilegeLogic privilegeLogic;
    @Autowired
    private RoleLogic roleLogic;
    @Autowired
    private UserLogic userLogic;
    @Autowired
    private UserRoleMapLogic userRoleMapLogic;
    @Autowired
    private PrivilegeRoleMapLogic privilegeRoleMapLogic;
    @Autowired
    private ModulePrivilegeMapLogic modulePrivilegeMapLogic;
    @Autowired
    private ModuleLogic moduleLogic;

    /**
     * 修改权限信息
     * @param priName
     * @param priDescribe
     * @return
     * @throws BizException
     */
    public void modifyPrivilegeByPriId(String priId, String priName, String priDescribe) throws BizException {
        //校验权限是否存在
        vaildPrivilegeById(priId);
        privilegeLogic.modifyPrivilegeByPriId(priId, priName, priDescribe);
    }

    /**
     * 校验权限
     * @param priId
     * @throws BizException
     */
    public void vaildPrivilegeById(String priId) throws BizException{
        Privilege privilege = privilegeLogic.getById(priId);
        if(null == privilege){
            throw new BizException(EduErrors.CODE_100004, "权限Id:"+priId+",查无权限");
        }
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
     * 校验以及删除相关信息
     * @param priId
     * @throws BizException
     */
    public void delPrivilegeRoleMapAndModulePrivilegeMap(String priId) throws BizException{
        //1.校验权限ID是否存在
        vaildPrivilegeById(priId);
        //2.校验角色是否拥有权限信息
        List<PrivilegeRoleMap> privilegeRoleMapList = privilegeRoleMapLogic.getPrivilegeRoleMapByPriId(priId);
        if (null != privilegeRoleMapList && !privilegeRoleMapList.isEmpty()){
            privilegeRoleMapLogic.deletePriRoleMapInfoById(priId, null);
        }
        //3.校验权限是否已经维护到模块信息上
        List<ModulePrivilegeMap> modulePrivilegeMapList = modulePrivilegeMapLogic.getsByPriId(priId);
        if (null != modulePrivilegeMapList && !modulePrivilegeMapList.isEmpty()){
            modulePrivilegeMapLogic.deleteMolPriMapInfoByPriIdList(priId);
        }
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
     * 根据用户ID查询角色ID
     * @return
     * @throws BizException
     */
    public List<String> getRolIdListByUserId(String userId) throws BizException {
        return userRoleMapLogic.getRolIdListByUserId(userId);
    }

    /**
     * 根据userId查找角色ID列表
     * 批量查询角色信息
     * @param userId
     * @return
     * @throws BizException
     */
    public List<Role> getListByRoleIdList(String userId) throws BizException{
        //查询关系表信息
        List<String> roleIdList = userRoleMapLogic.getRolIdListByUserId(userId);
        if (null == roleIdList || roleIdList.isEmpty()) {
            throw new BizException(EduErrors.CODE_100004, "当前用户:" + userId + ",查无授权角色信息的记录", null);
        }
        List<Role> roleList = roleLogic.getListByRoleIdList(roleIdList);
        if (null == roleList || roleList.isEmpty()) {
            throw new BizException(EduErrors.CODE_100004, "根据用户授权角色信息的记录:" + roleIdList + "查无角色信息", null);
        }
        return roleList;
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
     * 根据角色ID以及用户ID删除用户和角色的关系信息
     * @param roleIdList
     * @param userId
     * @return
     * @throws BizException
     */
    public void deleteUserRoleMapByUseId(List<String> roleIdList, String userId) throws BizException{
        userRoleMapLogic.deleteUserRoleMapByUseId(roleIdList, userId);
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
        vaildRoleById(useRoleId);
        userRoleMapLogic.updateUserRoleMapByUseId(useId, useUserId, useRoleId, useDescribe);
    }

    /**
     * 绑定权限和角色关系信息
     * @param roleId
     * @param privilegeIdList
     * @return
     * @throws BizException
     */
    public void addPrivilegeRoleMap(String roleId, List<String> privilegeIdList) throws BizException{
        //mybatis批量添加，必须都放进list
        List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
        for (String privilegeId : privilegeIdList){
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("privilegeId", privilegeId);
            param.put("priId", UKID.getGeneratorID());
            param.put("roleId", roleId);
            params.add(param);
        }
        privilegeRoleMapLogic.addPrivilegeRoleMap(params);
    }

    /**
     * 根据关系表ID删除关系表信息
     * @param privilegeIdList
     * @param roleId
     * @return
     * @throws BizException
     */
    public void deletePrivilegeRoleMapByPriId(List<String> privilegeIdList, String roleId) throws BizException{
        vaildRoleById(roleId);
        privilegeRoleMapLogic.deletePrivilegeRoleMapByPriId(privilegeIdList, roleId);
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
    public void updatePrivilegeRoleMapByPriId(String priId, String priPrivilegeId, String priRoleId, String priDescribe) throws BizException{
        vaildRoleById(priRoleId);
        vaildPrivilegeById(priPrivilegeId);
        privilegeRoleMapLogic.updatePrivilegeRoleMapByPriId(priId, priPrivilegeId, priRoleId, priDescribe);
    }

    /**
     * 查询当前子系统下拥有的全部权限信息
     * @return
     * @throws BizException
     */
    public List<Privilege> getListAllPrivilege() throws BizException{
        return privilegeLogic.getListAllPrivilege();
    }

    /**
     * 分页查询用户与角色绑定的信息
     * @param roleId
     * @return
     * @throws BizException
     */
    public List<Privilege> getListPrivilegeRoleMap(String roleId) throws BizException{
        List<Privilege> privilegeList = new ArrayList<Privilege>();
        List<String> priIdList = privilegeRoleMapLogic.getListPriIdByRoleId(roleId);
        if (null != priIdList && !priIdList.isEmpty()){
            privilegeList = privilegeLogic.getListByPriIdList(priIdList);
        }
        return privilegeList;
    }

    /**
     * 绑定用户和角色的信息
     * @param useUserId
     * @param roleIdList
     * @return
     * @throws BizException
     */
    public void addUserRoleMap(String useUserId, List<String> roleIdList) throws BizException{
        String useId = UKID.getGeneratorID();
        userRoleMapLogic.addUserRoleMap(useId, useUserId, roleIdList);
    }

    /**
     * 查询角色未分配的权限信息以及角色现有的权限信息
     * @param roleId
     * @return
     * @throws BizException
     */
    public Map<String, Object> getListExistedAndNoHavePrivilegeRoleMap(String roleId) throws BizException{
        Map<String, Object> data = new HashMap<String, Object>();
        List<Privilege> privilegeRoleList = new ArrayList<Privilege>();
        //查询所有权限信息
        List<Privilege> allPrivilegeList = privilegeLogic.getListAllPrivilege();
        //查询当前角色已经拥有的权限id
        List<String> priIdList = privilegeRoleMapLogic.getListPriIdByRoleId(roleId);
        if (null != priIdList && !priIdList.isEmpty()) {
            //根据角色的权限ID，查询权限信息
            privilegeRoleList = privilegeLogic.getListByPriIdList(priIdList);
        }
        data.put("privilegeRoleOfExisting", privilegeRoleList);//角色现有的权限信息
        //todo 这个地方要注意：是否需要根据子系统进行区分
        data.put("allPrivilegeList", allPrivilegeList);//全部的权限信息
        return data;
    }

    /**
     * 查询用户未被授权的角色信息以及用户现有的角色信息
     * @param userId
     * @return
     * @throws BizException
     */
    public Map<String, Object> getListExistedAndNoHaveUserRoleMap(String userId) throws BizException{
        Map<String, Object> data = new HashMap<String, Object>();
        List<Role> userRoleList = new ArrayList<Role>();
        //查询所有角色信息
        List<Role> roles = roleLogic.getListAllRole();
        //查询当前用户已经拥有的角色id
        List<String> roleIdList = userRoleMapLogic.getListRoleIdByUserId(userId);
        if (null != roleIdList || !roleIdList.isEmpty()){
            //根据用户的角色ID，查询角色信息
            userRoleList = roleLogic.getListByRoleIdList(roleIdList);
        }
        //绑定信息
        data.put("userRoleList", userRoleList);//用户拥有的角色信息
        //todo 角色信息也是受子系统限制
        data.put("roleList", roles);//全部的角色信息
        return data;
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
     * 获取当前子系统所有模块信息
     * @return
     * @throws BizException
     */
    public List<Module> getListAllModule() throws BizException{
        return moduleLogic.getListAllModule();
    }

    /**
     * 获取当前权限拥有的全部模块信息
     * @param privilegeId
     * @return
     * @throws BizException
     */
    public List<Module> getListModulePrivilegeMap(String privilegeId) throws BizException{
        List<Module> moduleList = new ArrayList<Module>();
        List<String> moduleIdList = modulePrivilegeMapLogic.getModuleIdListByPriId(privilegeId);
        if (null != moduleIdList && !moduleIdList.isEmpty()){
            moduleList = moduleLogic.getListModuleIdList(moduleIdList);
        }
        return moduleList;
    }

    /**
     * 批量添加权限的模块信息
     * @param privilegeId
     * @param moduleIdList
     * @throws BizException
     */
    public void addModulePrivilegeMap(String privilegeId, List<String> moduleIdList) throws BizException{
        //todo 校验权限是否存在
        List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
        for (String moduleId : moduleIdList){
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("modId", UKID.getGeneratorID());
            param.put("privilegeId", privilegeId);
            param.put("moduleId", moduleId);
            params.add(param);
        }
        modulePrivilegeMapLogic.addModulePrivilegeMap(params);
    }

    /**
     * 批量删除权限的模块信息
     * @param moduleIdList
     * @param privilegeId
     */
    public void deleteModulePrivilegeMapByPriId(List<String> moduleIdList, String privilegeId) throws BizException{
        //todo 校验
        modulePrivilegeMapLogic.deleteModulePrivilegeMapByPriId(moduleIdList, privilegeId);
    }

    /**
     * 校验数据并且删除关系表信息
     * @param roleId
     * @throws BizException
     */
    public void delPrivilegeRoleMapAndUserRoleMap(String roleId) throws BizException{
        //1.先校验角色ID是否存在
        vaildRoleById(roleId);
        //2.校验当前角色是否拥有权限信息
        List<PrivilegeRoleMap> privilegeRoleMapList = privilegeRoleMapLogic.getPrivilegeRoleMapByRolId(roleId);
        if(null != privilegeRoleMapList && !privilegeRoleMapList.isEmpty()){
            privilegeRoleMapLogic.deletePriRoleMapInfoById(null, roleId);
        }
        //3.校验当前角色是否被赋予用户
        List<UserRoleMap> userRoleMapList = userRoleMapLogic.getsByRolId(roleId);
        if(null != userRoleMapList && !userRoleMapList.isEmpty()){
            userRoleMapLogic.deleteUserRoleMapByRolId(roleId);
        }
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
     * 查询当前子系统下的全部角色信息
     * @return
     * @throws BizException
     */
    public List<Role> getListAllRole() throws BizException{
        return roleLogic.getListAllRole();
    }

    /**
     * 获取当前用户拥有的全部角色信息
     * @param userId
     * @return
     * @throws BizException
     */
    public List<Role> getListUserRoleMap(String userId) throws BizException{
        List<Role> roleList = new ArrayList<Role>();
        List<String> roleIdList = userRoleMapLogic.getListRoleIdByUserId(userId);//todo 子系统也要带上
        if(null != roleIdList && !roleIdList.isEmpty()){
            roleList = roleLogic.getListByRoleIdList(roleIdList);
        }
        return roleList;
    }

    /**
     * 分页查询角色列表
     * @param pageIndex
     * @param pageCount
     * @return
     * @throws BizException
     */
    public PageVo getListRoleByPageVo(int pageIndex, int pageCount)throws BizException {
        int count = roleLogic.getRoleCount();
        PageVo pageVo = new PageVo(pageIndex, count, pageCount);
        pageVo.init();
        pageVo.setList(roleLogic.getsByParams(pageVo));
        return pageVo;
    }

    /**
     * 分页查询权限总列表
     * @param pageIndex
     * @param pageCount
     * @return
     * @throws BizException
     */
    public PageVo getListPrivilegeByPageVo(int pageIndex, int pageCount) throws BizException {
        int count = privilegeLogic.getPrivilegeCount();
        PageVo pageVo = new PageVo(pageIndex, count, pageCount);
        pageVo.init();
        pageVo.setList(privilegeLogic.getsByParams(pageVo));
        return pageVo;
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
        Role role = roleLogic.getById(roleId);
        if(null == role){
            throw new BizException(EduErrors.CODE_100004, "角色Id:"+roleId+",查无角色信息");
        }
    }


}

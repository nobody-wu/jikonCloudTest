package cn.jikon.api.usermanage;

import cn.jikon.api.BaseController;
import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.entity.managesystem.Privilege;
import cn.jikon.domain.entity.managesystem.Role;
import cn.jikon.domain.entity.module.Module;
import cn.jikon.domain.vo.PageVo;
import cn.jikon.service.user.UserService;
import cn.jikon.service.usermanage.PrivilegeManageService;
import cn.jikon.service.usermanage.PrivilegeRoleMapService;
import cn.jikon.service.usermanage.UserRoleMapService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wujiapeng
 * @ApiGroup 普通管理员操作功能---权限／角色的管理
 * @date 2017-3-31
 */
@RestController
@RequestMapping("/api/userManagerCenter")
public class PrivilegeManageController extends BaseController {
    @Autowired
    private PrivilegeManageService privilegeManageService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleMapService userRoleMapService;
    @Autowired
    private PrivilegeRoleMapService privilegeRoleMapService;

    /**
     * @ApiMethod:true
     * @ApiMethodName: 查询权限列表信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getPrivilegeList")
    public String getPrivilegeList(@RequestBody String params) {
        int pageCount = 0;
        try {
            JSONObject jsonObject = parseObject(params);
            String pageIndexStr = getRequestParam(jsonObject, "pageIndex", "", true);//当前页数
            int pageIndex = Integer.parseInt(pageIndexStr);
            String pageCountStr = jsonObject.getString("pageCount");
            //如果前端传了pageCount参数，那就用前端传的
            if (null == pageCountStr){
                pageCount = BaseController.pageCount;
            }
            if (null != pageCountStr){
                pageCount = Integer.parseInt(pageCountStr);
            }
            if (pageCount <= 0){
                return getResponse(EduErrors.CODE_100000, "指定每页条数必须大于0", null);
            }
            PageVo pageVo = privilegeManageService.getListPrivilegeByPageVo(pageIndex, pageCount);
            return getResponse(EduErrors.CODE_100000, "请求成功", pageVo);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 修改权限信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/modifyPrivilegeByPriId")
    public String modifyPrivilegeByPriId(@RequestBody String params /*HttpServletRequest request*/) {
        try {
            JSONObject jsonObject = parseObject(params);
            String priName = getRequestParam(jsonObject, "name", "", true);
            String priDescribe = getRequestParam(jsonObject, "describe", "", true);
            String priId = getRequestParam(jsonObject, "id", "", true);
            privilegeManageService.modifyPrivilegeByPriId(priId, priName, priDescribe);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 删除权限信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/deletePrivilegeByPriIdList")
    public String deletePrivilegeByPriIdList(@RequestBody String params) {
        try {
            JSONArray privilegeList = JSON.parseArray(params);
            List<String> priIdList = new ArrayList<String>();
            for (Object privilege : privilegeList) {
                JSONObject obj = parseObject(privilege.toString());
                String priId = getRequestParam(obj, "id", "", true);
                privilegeManageService.delPrivilegeRoleMapAndModulePrivilegeMap(priId);
                //4.添加到集合
                priIdList.add(priId);
            }
            //5.批量删除
            privilegeManageService.deletePrivilegeByPriIdList(priIdList);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 增加权限信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/addPrivilege")
    public String addPrivilege(@RequestBody String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            String priName = getRequestParam(jsonObject, "name", "", true);
            String priDescribe = getRequestParam(jsonObject, "describe", "", true);
            privilegeManageService.addPrivilege(priName, priDescribe);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * 根据userId获取对应角色接口
     *
     * @param requestBody
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getListUserRoleByUserId")
    public String getListUserRoleByUserId(@RequestBody String requestBody) {
        try {
            JSONObject jSONObject = parseObject(requestBody);
            String userId = getRequestParam(jSONObject, "userId", "", true);
            List<Role> roleList = privilegeManageService.getListByRoleIdList(userId);
            return getResponse(EduErrors.CODE_100000, "请求成功", roleList);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 查询角色列表信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getRoleList")
    public String getRoleList(@RequestBody String params) {
        int pageCount = 0;
        try {
            JSONObject jsonObject = parseObject(params);
            String pageIndexStr = getRequestParam(jsonObject, "pageIndex", "", true);//当前页数
            int pageIndex = Integer.parseInt(pageIndexStr);
            String pageCountStr = jsonObject.getString("pageCount");
            //如果前端传了pageCount参数，那就用前端传的
            if (null == pageCountStr){
                pageCount = BaseController.pageCount;
            }
            if (null != pageCountStr){
                pageCount = Integer.parseInt(pageCountStr);
            }
            if (pageCount <= 0){
                return getResponse(EduErrors.CODE_100000, "指定每页条数必须大于0", null);
            }
            PageVo pageVo = privilegeManageService.getListRoleByPageVo(pageIndex, pageCount);
            return getResponse(EduErrors.CODE_100000, "请求成功", pageVo);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 修改角色信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/modifyRoleByRolId")
    public String modifyRoleByRolId(@RequestBody String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            String rolName = getRequestParam(jsonObject, "name", "", true);
            String rolDescribe = getRequestParam(jsonObject, "describe", "", true);
            String rolId = getRequestParam(jsonObject, "id", "", true);
            privilegeManageService.modifyRoleByRolId(rolId, rolName, rolDescribe);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 批量删除角色信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/deleteRoleByRolIdList")
    public String deleteRoleByRolIdList(@RequestBody String params) {
        try {
            JSONArray jsonArray = JSON.parseArray(params);
            List<String> rolIdList = new ArrayList<String>();
            Boolean flag;
            for (Object jsonObject : jsonArray) {
                JSONObject obj = parseObject(jsonObject.toString());
                String roleId = getRequestParam(obj, "id", "", true);
                privilegeManageService.delPrivilegeRoleMapAndUserRoleMap(roleId);
                //4.添加到集合
                rolIdList.add(roleId);
            }
            //5.批量删除
            privilegeManageService.deleteRoleByRolIdList(rolIdList);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 增加角色信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/addRole")
    public String addRole(@RequestBody String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            String rolName = getRequestParam(jsonObject, "name", "", true);
            String rolDescribe = getRequestParam(jsonObject, "describe", "", true);
            privilegeManageService.addRole(rolName, rolDescribe);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 获取所有角色信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getListAllRole")
    public String getListAllRole() {
        try {
            List<Role> roleList = privilegeManageService.getListAllRole();
            return getResponse(EduErrors.CODE_100000, "请求成功", roleList);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 获取当前用户拥有的全部角色信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getListUserRoleMap")
    public String getListUserRoleMap(@RequestBody String params) {
        try {JSONObject jsonObject = parseObject(params);
            String userId = getRequestParam(jsonObject, "userId", "", true);
            List<Role> roleList = privilegeManageService.getListUserRoleMap(userId);
            return getResponse(EduErrors.CODE_100000, "请求成功", roleList);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }


    /**
     * @ApiMethod:true
     * @ApiMethodName: 查询用户未被授权的角色信息以及用户现有的角色信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     * @deprecated getListExistedAndNoHaveUserRoleMap接口暂不符合前端需求，无用。但不删除，先留着以后再说吧
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getListExistedAndNoHaveUserRoleMap")
    public String getListExistedAndNoHaveUserRoleMap(@RequestBody String params) {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = parseObject(params);
            String userId = getRequestParam(jsonObject, "userId", "", true);
            data = privilegeManageService.getListExistedAndNoHaveUserRoleMap(userId);
            return getResponse(EduErrors.CODE_100000, "请求成功", data);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), data);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 批量添加用户的角色信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/addUserRoleMap")
    public String addUserRoleMap(@RequestBody String params) {
        List<String> roleIdList = new ArrayList<String>();
        try {
            JSONObject jsonObject = parseObject(params);
            String useUserId = getRequestParam(jsonObject, "userId", "", true);//用户ID
            String roleIdListStr = getRequestParam(jsonObject, "roleIdList", "", true);
            JSONArray roleIdArray = JSON.parseArray(roleIdListStr);
            for (Object object : roleIdArray){
                JSONObject jsonObject1 = JSONObject.parseObject(object.toString());
                String roleId = getRequestParam(jsonObject1, "roleId", "", true);
                userRoleMapService.vaildUserRoleMapExit(useUserId, roleId);
                roleIdList.add(roleId);
            }
            privilegeManageService.addUserRoleMap(useUserId, roleIdList);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        } catch (Exception e){
            return getResponse(1, e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 批量删除用户的角色信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/deleteUserRoleMapByUseId")
    public String deleteUserRoleMapByUseId(@RequestBody String params) {
        List<String> roleIdList = new ArrayList<String>();
        try {
            JSONObject jsonObject = parseObject(params);
            String userId = getRequestParam(jsonObject, "userId", "", true);
            String roleIdListStr = getRequestParam(jsonObject, "roleIdList", "", true);
            //校验用户是否存在
            userService.validUserById(userId);
            JSONArray roleIdArray = JSON.parseArray(roleIdListStr);
            for (Object object : roleIdArray){
                JSONObject jsonObject1 = JSONObject.parseObject(object.toString());
                String roleId = getRequestParam(jsonObject1, "roleId", "", true);
                userRoleMapService.vaildUserRoleMapNoExit(userId, roleId, roleIdList);
            }
            privilegeManageService.deleteUserRoleMapByUseId(roleIdList, userId);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 修改用户的角色信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/updateUserRoleMapByUseId")
    public String updateUserRoleMapByUseId(@RequestBody String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            String useId = getRequestParam(jsonObject, "id", "", true);
            String useUserId = getRequestParam(jsonObject, "userId", "", true);
            String useRoleId = getRequestParam(jsonObject, "roleId", "", true);
            String useDescribe = getRequestParam(jsonObject, "describe", "", true);
            //校验用户是否存在
            userService.validUserById(useUserId);
            privilegeManageService.updateUserRoleMapByUseId(useId, useUserId, useRoleId, useDescribe);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 查询角色的权限信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getListPrivilegeRoleMap")
    public String getListPrivilegeRoleMap(@RequestBody String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            String roleId = getRequestParam(jsonObject, "roleId", "", true);//当前页数
            List<Privilege> privilegeList = privilegeManageService.getListPrivilegeRoleMap(roleId);
            return getResponse(EduErrors.CODE_100000, "请求成功", privilegeList);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 查询当前子系统下的全部权限信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getListAllPrivilege")
    public String getListAllPrivilege() {
        try {
            List<Privilege> privilegeList = privilegeManageService.getListAllPrivilege();
            return getResponse(EduErrors.CODE_100000, "请求成功", privilegeList);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 查询角色未分配的权限信息以及角色现有的权限信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     * @deprecated getListExistedAndNoHavePrivilegeRoleMap接口暂不符合前端需求，无用。但不删除，先留着以后再说吧
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getListExistedAndNoHavePrivilegeRoleMap")
    public String getListExistedAndNoHavePrivilegeRoleMap(@RequestBody String params) {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = parseObject(params);
            String roleId = getRequestParam(jsonObject, "roleId", "", true);
            data = privilegeManageService.getListExistedAndNoHavePrivilegeRoleMap(roleId);
            return getResponse(EduErrors.CODE_100000, "请求成功", data);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 批量添加角色的权限信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/addPrivilegeRoleMap")
    public String addPrivilegeRoleMap(@RequestBody String params) {
        List<String> privilegeIdList = new ArrayList<String>();
        try {
            JSONObject jsonObject = parseObject(params);
            String roleId = getRequestParam(jsonObject, "roleId", "", true);
            String privilegeIdListStr = getRequestParam(jsonObject, "privilegeIdList", "", true);
            JSONArray privilegeIdListArray = JSONObject.parseArray(privilegeIdListStr);
            for (Object object : privilegeIdListArray){
                JSONObject jsonObject1 = JSONObject.parseObject(object.toString());
                String privilegeId = getRequestParam(jsonObject1, "privilegeId", "", true);
                privilegeRoleMapService.vaildPriRoleMapByPriIdAndRolId(roleId, privilegeId, privilegeIdList);
            }
            privilegeManageService.addPrivilegeRoleMap(roleId, privilegeIdList);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 批量删除角色的权限信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/deletePrivilegeRoleMapByPriId")
    public String deletePrivilegeRoleMapByPriId(@RequestBody String params) {
        List<String> privilegeIdList = new ArrayList<String>();
        try {
            JSONObject jsonObject = parseObject(params);
            String privilegeIdListStr = getRequestParam(jsonObject, "privilegeIdList", "", true);
            String roleId = getRequestParam(jsonObject, "roleId", "", true);
            JSONArray privilegeIdListArray = JSONArray.parseArray(privilegeIdListStr);
            for (Object object : privilegeIdListArray){
                JSONObject jsonObject1 = JSONObject.parseObject(object.toString());
                String privilegeId = getRequestParam(jsonObject1, "privilegeId", "", true);
                //todo 校验
                privilegeIdList.add(privilegeId);
            }
            privilegeManageService.deletePrivilegeRoleMapByPriId(privilegeIdList, roleId);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 修改权限与角色的绑定信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/updatePrivilegeRoleMapByPriId")
    public String updatePrivilegeRoleMapByPriId(@RequestBody String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            //关系表ID
            String priId = getRequestParam(jsonObject, "id", "", true);
            //权限ID
            String priPrivilegeId = getRequestParam(jsonObject, "privilegeId", "", true);
            String priRoleId = getRequestParam(jsonObject, "roleId", "", true);
            String priDescribe = getRequestParam(jsonObject, "describe", "", true);
            privilegeManageService.updatePrivilegeRoleMapByPriId(priId, priPrivilegeId, priRoleId, priDescribe);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 获取当前子系统所有模块信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getListAllModule")
    public String getListAllModule() {
        try {
            List<Module> moduleList = privilegeManageService.getListAllModule();
            return getResponse(EduErrors.CODE_100000, "请求成功", moduleList);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 获取当前权限拥有的全部模块信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getListModulePrivilegeMap")
    public String getListModulePrivilegeMap(@RequestBody String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            String privilegeId = getRequestParam(jsonObject, "privilegeId", "", true);
            List<Module> moduleList = privilegeManageService.getListModulePrivilegeMap(privilegeId);
            return getResponse(EduErrors.CODE_100000, "请求成功", moduleList);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 批量添加权限的模块信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/addModulePrivilegeMap")
    public String addModulePrivilegeMap(@RequestBody String params) {
        List<String> moduleIdList = new ArrayList<String>();
        try {
            JSONObject jsonObject = parseObject(params);
            String privilegeId = getRequestParam(jsonObject, "privilegeId", "", true);
            String moduleIdListStr = getRequestParam(jsonObject, "moduleIdList", "", true);
            JSONArray moduleIdArray = JSONArray.parseArray(moduleIdListStr);
            for (Object object : moduleIdArray){
                JSONObject jsonObject1 = JSONObject.parseObject(object.toString());
                String moduleId = getRequestParam(jsonObject1, "moduleId", "", true);
                //todo 验证
                moduleIdList.add(moduleId);
            }
            privilegeManageService.addModulePrivilegeMap(privilegeId, moduleIdList);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 批量删除权限的模块信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/deleteModulePrivilegeMapByPriId")
    public String deleteModulePrivilegeMapByPriId(@RequestBody String params) {
        List<String> moduleIdList = new ArrayList<String>();
        try {
            JSONObject jsonObject = parseObject(params);
            String privilegeId = getRequestParam(jsonObject, "privilegeId", "", true);
            String moduleIdListStr = getRequestParam(jsonObject, "moduleIdList", "", true);
            JSONArray moduleIdListArray = JSONArray.parseArray(moduleIdListStr);
            for (Object object : moduleIdListArray){
                JSONObject jsonObject1 = JSONObject.parseObject(object.toString());
                String moduleId = getRequestParam(jsonObject1, "moduleId", "", true);
                moduleIdList.add(moduleId);
            }
            privilegeManageService.deleteModulePrivilegeMapByPriId(moduleIdList, privilegeId);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

}

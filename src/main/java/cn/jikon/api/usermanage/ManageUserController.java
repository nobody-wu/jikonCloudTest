package cn.jikon.api.usermanage;

import cn.jikon.api.BaseController;
import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.entity.managesystem.ManageUser;
import cn.jikon.domain.entity.user.User;
import cn.jikon.domain.vo.PageVo;
import cn.jikon.domain.vo.UserVo;
import cn.jikon.service.user.UserService;
import cn.jikon.service.usermanage.ManageUserService;
import cn.jikon.service.usermanage.OrganizationUserMapService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/api/userManagerCenter")
public class ManageUserController extends BaseController {

    @Autowired
    ManageUserService manageUserService;

    @Autowired
    UserService userService;
    @Autowired
    OrganizationUserMapService organizationUserMapService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/managerUserLogin", method = RequestMethod.POST)
    public String managerUserLogin(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        Map<String, Object> data = null;
        try {
            jSONObject = parseObject(requestBody);
            String account = getRequestParam(jSONObject, "account", "", true);
            String password = getRequestParam(jSONObject, "password", "", true);
            String verCode = getRequestParam(jSONObject, "verCode", "", true);
            String verCodeId = getRequestParam(jSONObject, "verCodeId", "", true);
            ManageUser manageUser = manageUserService.managerUserLogin(account, password, verCodeId, verCode);
            String managerId = manageUser.getId();
            data = new HashMap<String, Object>();
            data.put("manId", manageUser.getId());
            data.put("userType", manageUser.getType());
            data.put("account", manageUser.getAccount());
            data.put("token", userService.getToken(managerId));
            return getResponse(EduErrors.CODE_100000, "登录成功", data);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/registerManagerUser", method = RequestMethod.POST)
    public String registerManagerUser(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        Map<String, Object> data = null;
        try {
            jSONObject = parseObject(requestBody);
            String account = getRequestParam(jSONObject, "account", "", true);
            String password = getRequestParam(jSONObject, "password", "", true);
            String verCode = getRequestParam(jSONObject, "verCode", "", true);
            String verCodeId = getRequestParam(jSONObject, "verCodeId", "", true);
            manageUserService.registerManagerUser(account, password, verCodeId, verCode);
            data = new HashMap<String, Object>();
            data.put("userType", "N");
            return getResponse(EduErrors.CODE_100000, "用户注册成功", data);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public String getUserList(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        Map<String, Object> data = null;
        try {
            jSONObject = parseObject(requestBody);
            String pageIndex = getRequestParam(jSONObject, "pageIndex", "", true);
            String pageCount = getRequestParam(jSONObject, "pageCount", "", true);
            int count = userService.getUserCount();
            PageVo<User> pageVo = getPageVo(pageIndex, Integer.parseInt(pageCount), count);
            if (count > 0) {
                List<User> users = userService.getUserList(pageVo.getStart(), pageVo.getPageCount());
                pageVo.setList(users);
            }
            return getResponse(EduErrors.CODE_100000, "成功", pageVo);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getManageUserList", method = RequestMethod.POST)
    public String getManageUserList(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String pageIndex = getRequestParam(jSONObject, "pageIndex", "", true);
            String pageCount = getRequestParam(jSONObject, "pageCount", "", true);
            int count = manageUserService.getManageUserCount();
            PageVo<ManageUser> pageVo = getPageVo(pageIndex, Integer.parseInt(pageCount), count);
            if (count > 0) {
                List<ManageUser> ManageUsers = manageUserService.getManageUserList(pageVo.getStart(), pageVo.getPageCount());
                pageVo.setList(ManageUsers);
            }
            return getResponse(EduErrors.CODE_100000, "成功", pageVo);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/activationUserApply", method = RequestMethod.POST)
    public String activationUserApply(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String manageUserId = getRequestParam(jSONObject, "manageUserId", "", true);
            String systemId = getRequestParam(jSONObject, "systemId", "", true);
            String userId = getRequestParam(jSONObject, "userId", "", true);
            userService.activationUserApply(manageUserId, systemId, userId);
            return getResponse(EduErrors.CODE_100000, "已激活", null);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/activationManageUserApply", method = RequestMethod.POST)
    public String activationManageUserApply(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String superManageUserId = getRequestParam(jSONObject, "superManageUserId", "", true);
            String manageUserId = getRequestParam(jSONObject, "manageUserId", "", true);
            String systemId = getRequestParam(jSONObject, "systemId", "", true);
            manageUserService.activationManageUserApply(superManageUserId, manageUserId,systemId);
            return getResponse(EduErrors.CODE_100000, "已激活", null);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String account = getRequestParam(jSONObject, "account", "", true);
            String name = getRequestParam(jSONObject, "name", "", true);
            String describe = getRequestParam(jSONObject, "describe", "", false);
            String orgIdList = getRequestParam(jSONObject, "organizationIdList", "", true);
            List<String> organizationIdList = JSON.parseArray(orgIdList,String.class);
            userService.addUser(account, name, describe, organizationIdList);
            User user = userService.getUserByAccount(account);
            UserVo userVo = new UserVo(user.getId(), user.getUseName(), describe, user.getType());
            return getResponse(EduErrors.CODE_100000, "新增完成", userVo);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    public String delUser(@RequestBody String requestBody) {
        try {
            JSONArray userIdJsonArray = JSON.parseArray(requestBody);
            List<String> userIdList = assertUserIdList(userIdJsonArray);
            userService.delUser(userIdList);
            return getResponse(EduErrors.CODE_100000, "删除成功", null);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/updateOrganizationUserMap", method = RequestMethod.POST)
    public String updateOrganizationUserMap(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String useIdList = getRequestParam(jSONObject, "userIdList", "", true);
            String orgIdList = getRequestParam(jSONObject, "organizationIdList", "", true);
            List<String> userIdList = JSON.parseArray(useIdList, String.class);
            List<String> organizationIdList = JSON.parseArray(orgIdList, String.class);
            organizationUserMapService.updateOrganizationUserMap(userIdList, organizationIdList);
            return getResponse(EduErrors.CODE_100000, "更新成功", null);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    //List<JSONArray>数组转化成List<String>并对元素做判空
    private List<String> assertUserIdList(JSONArray userIdJsonArray) throws BizException {
        List<String> userIdList = new ArrayList<String>();
        for (Object userIdObj : userIdJsonArray) {
            JSONObject jSONObject = parseObject(userIdObj.toString());
            String userId = getRequestParam(jSONObject, "id", "", true);
            userIdList.add(userId);
        }
        return userIdList;
    }
//    //List<JSONArray>数组转化成List<String>并对元素做判空
//    private List<String> assertOrganizationIdList(JSONArray organizationIdJsonArray) throws BizException {
//        List<String> organizationIdList = new ArrayList<String>();
//        for (Object organizationIdObj : organizationIdJsonArray) {
//            JSONObject jSONObject = parseObject(organizationIdObj.toString());
//            String organizationId = getRequestParam(jSONObject, "id", "", true);
//            organizationIdList.add(organizationId);
//        }
//        return organizationIdList;
//    }

}
package cn.jikon.api.user;


import cn.jikon.api.BaseController;
import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.entity.user.User;
import cn.jikon.domain.vo.UserDetailVo;
import cn.jikon.domain.vo.UserVo;
import cn.jikon.domain.vo.VerCodeVo;
import cn.jikon.service.user.UserDetailService;
import cn.jikon.service.user.UserService;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by lwj on 2017/3/28.
 */
@RestController
@RequestMapping("/api/userCenter/")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailService userDetailService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getVerCode", method = RequestMethod.POST)
    public String getVerCode(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        Map<String, Object> data = null;
        try {
            jSONObject = parseObject(requestBody);
            VerCodeVo verCodeVo = userService.getVerCode();
            data = new HashMap<String, Object>();
            data.put("verCodeVo", verCodeVo);
            return getResponse(EduErrors.CODE_100000, "获取成功", data);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String account = getRequestParam(jSONObject, "account", "", true);
            String password = getRequestParam(jSONObject, "password", "", true);
            String verCode = getRequestParam(jSONObject, "verCode", "", true);
            String verCodeId = getRequestParam(jSONObject, "verCodeId", "", true);
            UserVo userVo = userService.userLogin(account, password, verCodeId, verCode);
            return getResponse(EduErrors.CODE_100000, "登陆成功", userVo);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getUserDetailById", method = RequestMethod.POST)
    public String getUserDetailById(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String userId = getRequestParam(jSONObject, "userId", "", true);
            UserDetailVo userDetailVo = userService.getUserDetailById(userId);
            return getResponse(EduErrors.CODE_100000, "获取成功", userDetailVo);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String userId = getRequestParam(jSONObject, "userId", "", true);
            String email = getRequestParam(jSONObject, "email", "", false);
            String address = getRequestParam(jSONObject, "address", "", false);
            userDetailService.updateUser(userId, address, email);
            return getResponse(EduErrors.CODE_100000, "更新成功", null);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/checkPassword", method = RequestMethod.POST)
    public String checkPassword(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String userId = getRequestParam(jSONObject, "userId", "", true);
            String password = getRequestParam(jSONObject, "password", "", true);
            userService.checkPassword(userId, password);
            return getResponse(EduErrors.CODE_100000, "验证成功", null);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/updateUserPassword", method = RequestMethod.POST)
    public String updateUserPassword(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String userId = getRequestParam(jSONObject, "userId", "", true);
            String password = getRequestParam(jSONObject, "password", "", true);
            String oldPassword = getRequestParam(jSONObject, "oldPassword", "", true);
            userService.updateUserPassword(userId, password, oldPassword);
            return getResponse(EduErrors.CODE_100000, "更新成功", null);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    //--------------------------------------------管理员操作权限---------------------------------------------------------

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getUserById", method = RequestMethod.POST)
    public String getUserById(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        Map<String, Object> data = null;
        try {
            jSONObject = parseObject(requestBody);
            String userId = getRequestParam(jSONObject, "userId", "", true);
            User user = userService.getUser(userId);
            data = new HashMap<String, Object>();
            data.put("user", user);
            return getResponse(EduErrors.CODE_100000, "已获取", data);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

}
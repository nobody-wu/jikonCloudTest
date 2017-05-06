package cn.jikon.service.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.common.util.MD5Util;
import cn.jikon.common.util.UKID;
import cn.jikon.common.util.ValidateUtil;
import cn.jikon.common.util.VerCodeUtil;
import cn.jikon.domain.entity.managesystem.ManageUser;
import cn.jikon.domain.entity.managesystem.SubSystem;
import cn.jikon.logic.supermanage.SubSystemLogic;
import cn.jikon.logic.usermanage.ManageUserLogic;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import cn.jikon.logic.usermanage.UserRoleMapLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageUserService {

    @Autowired
    ManageUserLogic manageUserLogic;

    @Autowired
    SubSystemLogic subSystemLogic;

    public ManageUser managerUserLogin(String account, String password, String verCodeId, String verCode) throws BizException {
        ManageUser manageUser = manageUserLogic.getManageUserByAccount(account);
        if (null == manageUser) {
            throw new BizException(EduErrors.CODE_100008, "该用户不存在");
        }
        if (!manageUser.isStatus()) {
            throw new BizException(EduErrors.CODE_100018, "该用户未激活");
        }
        boolean flag = false;
        try {
            flag = MD5Util.validPassword(password, manageUser.getPassword());
        } catch (Exception e) {
            throw new BizException(EduErrors.CODE_100022, "密码编译失败");
        }
        if (!flag) {
            throw new BizException(EduErrors.CODE_100009, "密码错误");
        }
        VerCodeUtil.checkVerCode(verCodeId, verCode);
        return manageUser;
    }

    public void registerManagerUser(String account, String password, String verCodeId, String verCode) throws BizException {
        VerCodeUtil.checkVerCode(verCodeId, verCode);
        if (null != manageUserLogic.getManageUserByAccount(account)) {
            throw new BizException(EduErrors.CODE_100015, "账号已经存在");
        }

        if (false == ValidateUtil.validateLength_account(account)) {
            throw new BizException(EduErrors.CODE_100011, "用户名长度为6-11位");
        }

        int passwordLength = password.length();
        if (passwordLength < 8 || passwordLength > 16) {
            throw new BizException(EduErrors.CODE_100011, "密码长度为8-16位");
        }

        String encryptedPwd = null;
        try {
            encryptedPwd = MD5Util.getEncryptedPwd(password);
            manageUserLogic.registerManagerUser(account, encryptedPwd);
        } catch (BizException e) {
            throw new BizException(EduErrors.CODE_100001, "新增失败");
        } catch (Exception e) {
            throw new BizException(EduErrors.CODE_100022, "密码编译失败");
        }
        VerCodeUtil.checkVerCode(verCodeId, verCode);
    }

    public void activationManageUserApply(String superManageUserId, String manageUserId,String systemId) throws BizException {
        SubSystem subSystem =  subSystemLogic.getSubSystemBySubId(systemId);
        if(null == subSystem){
            throw new BizException(EduErrors.CODE_100008, "系统不存在");
        }
        ManageUser manageUser = manageUserLogic.getManageUserById(superManageUserId);
        if (!"S".equals(manageUser.getType())) {
            throw new BizException(EduErrors.CODE_100010, "无权限激活");
        }
        manageUserLogic.activationManageUserApply(manageUserId);
    }

    public int getManageUserCount() {
        return manageUserLogic.getManageUserCount();
    }

    public List<ManageUser> getManageUserList(int start, int pageCount) {
        return manageUserLogic.getManageUserList(start, pageCount);
    }

}

package cn.jikon.service.user;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.common.util.*;
import cn.jikon.domain.entity.managesystem.ManageUser;
import cn.jikon.domain.entity.managesystem.SubSystem;
import cn.jikon.domain.entity.user.User;
import cn.jikon.domain.entity.user.UserDetail;
import cn.jikon.domain.vo.UserDetailVo;
import cn.jikon.domain.vo.UserVo;
import cn.jikon.domain.vo.VerCodeVo;
import cn.jikon.logic.supermanage.SubSystemLogic;
import cn.jikon.logic.user.UserDetailLogic;
import cn.jikon.logic.user.UserLogic;
import cn.jikon.logic.usermanage.ManageUserLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserLogic userLogic;

    @Autowired
    UserDetailLogic userDetailLogic;
    @Autowired
    ManageUserLogic manageUserLogic;
    @Autowired
    SubSystemLogic subSystemLogic;

    @Autowired
    private JwtUtil jwtUtil;

    public VerCodeVo getVerCode() throws BizException {
        VerCodeUtil verCodeUtil = VerCodeUtil.instance();
        String verCode = verCodeUtil.getString();
        String verCodeId = UUID.getIdString();
        verCodeUtil.saveMessage(verCodeId, verCode);
        return new VerCodeVo(verCodeId, verCodeUtil.getImage());
    }

    public User getUserByAccount(String account) {
        User user = userLogic.getUserByAccount(account);
        return user;
    }

    public void validUserById(String id) throws BizException {
        getUser(id);
    }

    public User getUser(String id) throws BizException {
        User user = userLogic.getUserById(id);
        if (null == user) {
            throw new BizException(EduErrors.CODE_100008, "该用户不存在");
        }
        return user;
    }

    public UserVo userLogin(String account, String password, String verCodeId, String verCode) throws BizException {
        User user = userLogic.getUserByAccount(account);
        if (null == user) {
            throw new BizException(EduErrors.CODE_100008, "该用户不存在");
        } else {
            checkOldPassword(password, user.getPassword());
        }
        // 用户已激活标识为 1
        if (user.isStatus()) {
            throw new BizException(EduErrors.CODE_100018, "该用户未激活");
        }
        VerCodeUtil.checkVerCode(verCodeId, verCode);
        UserVo userVo = new UserVo();
        userVo.setType(user.getType());
        userVo.setAccount(user.getAccount());
        userVo.setUserId(user.getId());
        userVo.setName(user.getUseName());
        userVo.setToken(getToken(user.getId()));
        return userVo;
    }

    public void checkPassword(String userId, String password) throws BizException {
        User user = getUser(userId);
        checkOldPassword(password, user.getPassword());
    }

    public void updateUserPassword(String userId, String password, String oldPassword) throws BizException {
        try {
            password = MD5Util.getEncryptedPwd(password);
        } catch (Exception e) {
            throw new BizException(EduErrors.CODE_100012, "密码异常");
        }
        User user = getUser(userId);
        checkOldPassword(oldPassword, user.getPassword());
        userLogic.updateUserPassword(userId, password);
    }

    public int getUserCount() {
        return userLogic.getUserCount();
    }

    public List<User> getUserList(int start, int pageCount) {
        return userLogic.getUserList(start, pageCount);
    }

    public void activationUserApply(String manageUserId, String systemId, String userId) throws BizException {
        SubSystem subSystem =  subSystemLogic.getSubSystemBySubId(systemId);
        if(null == subSystem){
            throw new BizException(EduErrors.CODE_100008, "系统不存在");
        }
        ManageUser manageUser = manageUserLogic.getManageUserById(manageUserId);
        User user = userLogic.getUserById(userId);
        if (user.isStatus()) {
            throw new BizException(EduErrors.CODE_100010, "用户无需激活");
        }
        if (null == manageUser) {
            throw new BizException(EduErrors.CODE_100010, "无权限激活");
        }
        userLogic.activationUserApply(userId);
    }

    public void addUser(String account, String name, String describe, List<String> organizationIdList) throws BizException {
        if (null != userLogic.getUserByAccount(account)) {
            throw new BizException(EduErrors.CODE_100015, "账号已经存在");
        }
        try {
            userLogic.addUser(account, name, describe, organizationIdList);
        } catch (Exception e) {
            throw new BizException(EduErrors.CODE_100001, "增加失败");
        }
    }

    public void delUser(List<String> userIdList) throws BizException {
        try {
            for (String userId : userIdList) {
                userLogic.delUser(userId);
            }
        } catch (BizException e) {
            throw new BizException(EduErrors.CODE_100003, "删除失败");
        }
    }

    public UserDetailVo getUserDetailById(String userId) throws BizException {
        validUserById(userId);
        UserDetail userDetail = userDetailLogic.getUserDetailById(userId);
        UserDetailVo userDetailVo = new UserDetailVo();
        userDetailVo.setUserId(userDetail.getId());
        userDetailVo.setEmail(userDetail.getEmail());
        userDetailVo.setAddress(userDetail.getAddress());
        return userDetailVo;
    }

    public String getToken(String userId) throws BizException {
        String token = "";
        try {
            String random = UUID.getRandom();
            token = jwtUtil.createJWT(userId, random, Constants.JWT_TTL);
            CacheMap.tokenMap.put(random, token);
        } catch (Exception e) {
            throw new BizException(EduErrors.CODE_100023, "token生成失败");
        }
        return token;
    }

    private void checkOldPassword(String password, String oldPassword) throws BizException {
        try {
            if (!MD5Util.validPassword(password, oldPassword)) {
                throw new BizException(EduErrors.CODE_100009, "密码错误");
            }
        } catch (Exception e) {
            throw new BizException(EduErrors.CODE_100009, "密码错误");
        }
    }

}

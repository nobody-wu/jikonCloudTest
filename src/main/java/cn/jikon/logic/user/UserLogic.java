package cn.jikon.logic.user;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.common.util.MD5Util;
import cn.jikon.common.util.UKID;
import cn.jikon.domain.dao.user.UserDao;
import cn.jikon.domain.dao.user.UserDetailDao;
import cn.jikon.domain.dao.usermanage.OrganizationUserMapDao;
import cn.jikon.domain.entity.user.OrganizationUserMap;
import cn.jikon.domain.entity.user.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLogic {

    @Autowired
    UserDao userDao;

    @Autowired
    UserDetailDao userDetailDao;
    @Autowired
    OrganizationUserMapDao organizationUserMapDao;

    public User getUserByAccount(String account) {
        return userDao.getUserByAccount(account);
    }

    public User getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    public int updateUserPassword(String userId, String password) {
        return userDao.updateUserPassword(userId, password);
    }

    public int getUserCount() {
        return userDao.getUserCount();
    }

    public List<User> getUserList(int start, int pageCount) {
        return userDao.getUserList(start, pageCount);
    }

    public void activationUserApply(String userId) throws BizException {
        try {
            userDao.activationUserApply(userId);
        } catch (Exception e) {
            throw new BizException(EduErrors.CODE_100010, e.getMessage());
        }
    }

    @Transactional
    public void addUser(String account, String name, String describe, List<String> organizationIdList) throws Exception {
        String userId = UKID.getGeneratorID();
        User user = new User(userId, account, name, describe);
        OrganizationUserMap organizationUserMap = new OrganizationUserMap();
        user.setPassword(MD5Util.getEncryptedPwd("123456"));
        userDao.addUser(user);
        userDetailDao.addUserDetail(UKID.getGeneratorID(), userId);
        for (String organizationId : organizationIdList) {
            organizationUserMap.setId(UKID.getGeneratorID());
            organizationUserMap.setUserId(userId);
            organizationUserMap.setOrganizationId(organizationId);
            organizationUserMap.setDescribe(describe);
            organizationUserMapDao.addOrganizationUserMap(organizationUserMap);
        }
    }

    @Transactional
    public void delUser(String userId) throws BizException {
        userDao.delUser(userId);
        userDetailDao.delUserDetail(userId);
        organizationUserMapDao.delOrganizationUserMapByUserId(userId);
    }

}

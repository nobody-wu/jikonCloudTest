package cn.jikon.logic.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.common.util.UKID;
import cn.jikon.domain.dao.usermanage.ManageUserDao;
import cn.jikon.domain.entity.managesystem.ManageUser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManageUserLogic {

    @Autowired
    ManageUserDao manageUserDao;

    @Autowired
    UserRoleMapLogic userRoleMapLogic;

    @Transactional
    public void registerManagerUser(String account, String password) throws BizException {
        String manageUserId = UKID.getGeneratorID();
        ManageUser manageUser = new ManageUser();
        manageUser.setId(manageUserId);
        manageUser.setAccount(account);
        manageUser.setPassword(password);
        manageUserDao.registerManagerUser(manageUser);

    }

    public void activationManageUserApply(String manageUserId) throws BizException {
        try {
            manageUserDao.activationManageUserApply(manageUserId);
        } catch (Exception e) {
            throw new BizException(EduErrors.CODE_100002, "普通管理员激活失败");
        }
    }

    public ManageUser getManageUserByAccount(String account) {
        return manageUserDao.getManageUserByAccount(account);
    }

    public int getManageUserCount() {
        return manageUserDao.getManageUserCount();
    }

    public List<ManageUser> getManageUserList(int start, int pageCount) {
        return manageUserDao.getManageUserList(start, pageCount);
    }

    public ManageUser getManageUserById(String manageUserId) {
        return manageUserDao.getManageUserById(manageUserId);
    }

    public void updateManagerPassword(String manageUserId, String password) {
        manageUserDao.updateManagerPassword(manageUserId, password);
    }
}

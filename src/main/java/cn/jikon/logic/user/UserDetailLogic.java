package cn.jikon.logic.user;

import cn.jikon.domain.dao.user.UserDetailDao;
import cn.jikon.domain.entity.user.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailLogic {

    @Autowired
    UserDetailDao userDetailDao;

    public UserDetail getUserDetailById(String userId) {
        return userDetailDao.getUserDetailById(userId);
    }

    public int updateUserDetail(String userId, String address, String email) {
        UserDetail userDetail = new UserDetail();
        userDetail.setId(userId);
        userDetail.setEmail(email);
        userDetail.setAddress(address);
        return userDetailDao.updateUserDetail(userDetail);
    }

}

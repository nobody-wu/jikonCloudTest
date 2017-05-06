package cn.jikon.service.user;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.entity.user.UserDetail;
import cn.jikon.logic.user.UserDetailLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxin on 2017/4/27.
 */
@Service
public class UserDetailService {

    @Autowired
    UserDetailLogic userDetailLogic;

    public void updateUser(String userId, String address, String email) throws BizException {
        UserDetail userDetail = userDetailLogic.getUserDetailById(userId);
        if (null == userDetail) {
            throw new BizException(EduErrors.CODE_100008, "该用户不存在");
        } else {
            userDetailLogic.updateUserDetail(userId, address, email);
        }
    }

}

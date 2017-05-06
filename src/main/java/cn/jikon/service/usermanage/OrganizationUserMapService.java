package cn.jikon.service.usermanage;

import cn.jikon.domain.entity.user.User;
import cn.jikon.domain.vo.UserVo;
import cn.jikon.logic.user.UserLogic;
import cn.jikon.logic.usermanage.OrganizationUserMapLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxin on 2017/4/24.
 */


@Service
public class OrganizationUserMapService {

    @Autowired
    OrganizationUserMapLogic organizationUserMapLogic;

    @Autowired
    UserLogic userLogic;

    public void updateOrganizationUserMap(List<String> userIdList, List<String> organizationIdList) {
        for (String userId:userIdList){
            organizationUserMapLogic.updateOrganizationUserMapByUserId(userId,organizationIdList);
        }
    }

    public List<UserVo> getUserListByOrganizationId(String fatherId) {
        List<String> userIdList = organizationUserMapLogic.getUserIdListByOrganizationId(fatherId);
        List<UserVo> userVoList = new ArrayList<UserVo>();
        for(String userId : userIdList){
            User user = userLogic.getUserById(userId);
            UserVo userVo = new UserVo();
            userVo.setUserId(user.getId());
            userVo.setName(user.getUseName());
            userVo.setType(user.getType());
            userVoList.add(userVo);
        }
        return userVoList;
    }
}

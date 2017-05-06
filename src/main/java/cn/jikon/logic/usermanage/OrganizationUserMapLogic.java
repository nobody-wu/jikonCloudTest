package cn.jikon.logic.usermanage;

import cn.jikon.domain.dao.usermanage.OrganizationUserMapDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangxin on 2017/4/21.
 */

@Service
public class OrganizationUserMapLogic {
    @Autowired
    OrganizationUserMapDao organizationUserMapDao;

    public void updateOrganizationUserMapByUserId(String userId, List<String> organizationIdList){
        for(String organizationId : organizationIdList){
            organizationUserMapDao.updateOrganizationUserMapByUserId(userId,organizationId);
        }
    }

    public List<String> getUserIdListByOrganizationId(String organizationId) {
        return organizationUserMapDao.getUserIdListByOrganizationId(organizationId);
    }

    public int getUserCountByOrganizationId(String organizationId) {
        return organizationUserMapDao.getUserCountByOrganizationId(organizationId);
    }
}

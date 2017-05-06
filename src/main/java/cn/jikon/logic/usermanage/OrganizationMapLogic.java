package cn.jikon.logic.usermanage;

import cn.jikon.domain.dao.usermanage.OrganizationMapDao;
import cn.jikon.domain.entity.managesystem.OrganizationMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangxin on 2017/4/25.
 */
@Service
public class OrganizationMapLogic {

    @Autowired
    OrganizationMapDao organizationMapDao;

    public int getOrganizationCountByFatherId(String fatherId) {
        return organizationMapDao.getOrganizationCountByFatherId(fatherId);
    }

}

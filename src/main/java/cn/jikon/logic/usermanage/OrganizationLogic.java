package cn.jikon.logic.usermanage;


import cn.jikon.common.util.UKID;
import cn.jikon.domain.dao.usermanage.OrganizationDao;
import cn.jikon.domain.dao.usermanage.OrganizationMapDao;
import cn.jikon.domain.dao.usermanage.OrganizationUserMapDao;
import cn.jikon.domain.entity.managesystem.Organization;
import cn.jikon.domain.entity.managesystem.OrganizationMap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizationLogic {

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    OrganizationMapDao orgMapDao;
    @Autowired
    OrganizationUserMapDao orgUserMapDao;

    public List<Organization> getOrganizationListByFatherId(String fatherId) {
        List<String> organizationIdList = orgMapDao.getChildIdListByFatherId(fatherId);
        List<Organization> organizationList = new ArrayList<Organization>();
        for (String orgId : organizationIdList) {
            Organization organization = new Organization();
            organization = orgDao.getOrganizationById(orgId);
            organizationList.add(organization);
        }
        return organizationList;
    }

    @Transactional
    public void addOrganization(String fatherId, String name) {
        String organizationId = UKID.getGeneratorID();
        Organization org = new Organization(organizationId, name);
        orgDao.addOrganization(org);

        String organizationMapId = UKID.getGeneratorID();
        OrganizationMap organizationMap = new OrganizationMap();
        organizationMap.setChildId(organizationId);
        organizationMap.setFatherId(fatherId);
        organizationMap.setId(organizationMapId);
        orgMapDao.addOrganizationMap(organizationMap);
    }

    public Organization getOrganizationByName(String name) {
        return orgDao.getOrganizationByName(name);
    }

    @Transactional
    public void updateOrganization(String organizationId, String fatherId, String name) throws Exception {
        Organization organization = new Organization(organizationId, name);
        orgDao.updateOrganization(organization);

        OrganizationMap organizationMap = new OrganizationMap(organizationId, fatherId);
        orgMapDao.updateOrganizationMap(organizationMap);

    }

    @Transactional
    public void delOrganization(String organizationId) {
        List<String> allChildrenOrganizationIdList = getAllOrganizationIdById(organizationId);

        for (String childrenOrganizationId : allChildrenOrganizationIdList) {
            orgDao.delOrganizationById(childrenOrganizationId);
        }
        for (String childrenOrganizationId : allChildrenOrganizationIdList) {
            orgMapDao.delOrganizationMapByChildId(childrenOrganizationId);
        }
        for (String childrenOrganizationId : allChildrenOrganizationIdList) {
            orgUserMapDao.delOrganizationUserMapByOrganizationId(childrenOrganizationId);
        }
    }


    public Organization getOrganizationById(String organizationId) {
        return orgDao.getOrganizationById(organizationId);
    }

    /**
     * 传入一个组织ID，找出该组织下的 所有子组织和他本身 组成的一个 组织ID的list
     * @param organizationId
     * @return List<String> allChildrenIdList
     */
    private List<String> getAllOrganizationIdById(String organizationId) {
        List<String> childrenIdList = new ArrayList<String>();
        List<String> organizationIdList = orgMapDao.getChildIdListByFatherId(organizationId);
        List<String> allChildrenIdList = tressOrgIdList(organizationIdList, childrenIdList);
        allChildrenIdList.add(organizationId);
        return allChildrenIdList;
    }

    /**
     * 传入组织ID的list 找出各个组织下各个层级的所有子组织拼装成childrenIdList
     * @param organizationIdList
     * @param childrenIdList
     * @return
     */
    private List<String> tressOrgIdList(List<String> organizationIdList, List<String> childrenIdList) {
        for (String organizationId : organizationIdList) {
            childrenIdList.add(organizationId);
            List<String> list = orgMapDao.getChildIdListByFatherId(organizationId);
            if (!list.isEmpty()) {
                tressOrgIdList(list, childrenIdList);
            }
        }
        return childrenIdList;
    }

}

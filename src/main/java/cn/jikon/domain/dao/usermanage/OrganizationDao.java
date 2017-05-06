package cn.jikon.domain.dao.usermanage;

import cn.jikon.domain.entity.managesystem.Organization;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationDao {

    Organization getOrganizationById(@Param("organizationId") String organizationId);

    Organization getOrganizationByName(@Param("name") String name);

    void delOrganizationById(@Param("organizationId") String organizationId);

    void addOrganization(Organization organization);

    void updateOrganization(Organization organization);

}

package cn.jikon.domain.dao.usermanage;

import cn.jikon.domain.entity.managesystem.Organization;
import cn.jikon.domain.entity.managesystem.OrganizationMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationMapDao {

    List<String> getChildIdListByFatherId(@Param("fatherId") String fatherId);

    void addOrganizationMap(OrganizationMap organizationMap);

    void updateOrganizationMap(OrganizationMap organizationMap);

    void delOrganizationMapByChildId(@Param("childId") String organizationId);

    int getOrganizationCountByFatherId(@Param("fatherId") String fatherId);
}

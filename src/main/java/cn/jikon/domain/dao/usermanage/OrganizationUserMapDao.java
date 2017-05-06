package cn.jikon.domain.dao.usermanage;

import cn.jikon.domain.entity.user.OrganizationUserMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationUserMapDao {

    void addOrganizationUserMap(OrganizationUserMap organizationUserMap);

    void delOrganizationUserMapByUserId(@Param("userId") String userId);

    void delOrganizationUserMapByOrganizationId(@Param("organizationId") String organizationId);

    void updateOrganizationUserMapByUserId(@Param("userId") String userId,@Param("organizationId") String organizationId);

    List<String> getUserIdListByOrganizationId(@Param("organizationId") String organizationId);

    int getUserCountByOrganizationId(@Param("organizationId") String organizationId);
}

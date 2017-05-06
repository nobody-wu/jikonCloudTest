package cn.jikon.domain.dao.usermanage;

import cn.jikon.domain.entity.managesystem.ManageUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManageUserDao {

    int registerManagerUser(ManageUser manageUser);

    ManageUser getManageUserByAccount(@Param("account") String account);

    int getManageUserCount();

    List<ManageUser> getManageUserList(@Param("start") int start, @Param("pageCount") int pageCount);

    void activationManageUserApply(@Param("manageUserId") String manageUserId);

    ManageUser getManageUserById(@Param("manageUserId") String manageUserId);

    void updateManagerPassword(@Param("manageUserId")String managerId,@Param("password") String password);

}

package cn.jikon.domain.dao.user;

import cn.jikon.domain.entity.user.UserDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailDao {

    UserDetail getUserDetailById(@Param("userId") String userId);

    int updateUserDetail(UserDetail userDetail);

    void addUserDetail(@Param("generatorID") String generatorID, @Param("userId") String userId);

    void delUserDetail(@Param("userId") String userId);
}
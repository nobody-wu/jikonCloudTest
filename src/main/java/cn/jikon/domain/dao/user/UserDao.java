package cn.jikon.domain.dao.user;

import cn.jikon.common.BizException;
import cn.jikon.domain.entity.user.User;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User getUserById(@Param("userId") String userId);

    User getUserByAccount(@Param("account") String account);

    int updateUserPassword(@Param("userId") String userId, @Param("password") String password);

    int getUserCount();

    List<User> getUserList(@Param("start") int start, @Param("pageCount") int pageCount);

    int activationUserApply(@Param("userId") String userId);

    void addUser(User user);

    void delUser(@Param("userId") String userId);

}
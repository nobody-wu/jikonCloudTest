package cn.jikon.domain.dao.module;

import cn.jikon.domain.entity.module.Module;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxin on 2017/4/25.
 */
@Repository
public interface ModuleDao {

    void addModule(@Param("moduleId") String moduleId, @Param("name") String name, @Param("describe") String describe);

    void delModuleById(@Param("moduleId") String moduleId);

    void updateModule(@Param("moduleId") String moduleId, @Param("name") String name, @Param("describe") String describe);

    Module getModule(@Param("moduleId") String moduleId);

    Module getModuleByName(@Param("name") String name);

    int getModuleCount();

    List<Module> getModuleList(@Param("start")int start,@Param("pageCount") int pageCount);

    List<Module> getListAllModule();

    List<Module> getListModuleIdList(@Param("list")List<String> moduleIdList);
}

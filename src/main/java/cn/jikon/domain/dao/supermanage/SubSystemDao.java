package cn.jikon.domain.dao.supermanage;

import cn.jikon.common.BizException;
import cn.jikon.domain.entity.managesystem.SubSystem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wujiapeng on 17/4/25.
 */
@Repository
public interface SubSystemDao {

    /**
     * 查询子系统信息总条数
     * @throws BizException
     */
    public int getSubSystemCount();

    /**
     * 查询子系统信息
     * @return
     * @throws Exception
     */
    public List<SubSystem> getListSubSystem();

    /**
     * 根据子系统编码查询唯一的子系统
     * @param subCode
     * @return
     * @throws Exception
     */
    public SubSystem getSubSystemBySubCode(@Param("subCode") String subCode);

    /**
     * 添加子系统
     * @param subId
     * @param subName
     * @param subCode
     * @param subDescribe
     */
    public void addSubSystem(@Param("subId") String subId,
                             @Param("subName") String subName,
                             @Param("subCode") String subCode,
                             @Param("subDescribe") String subDescribe);

    /**
     * 根据子系统id查询信息
     * @param subId
     * @return
     */
    public SubSystem getSubSystemBySubId(@Param("subId")String subId);

    /**
     * 根据ID更新子系统信息
     * @param subId
     * @param subName
     * @param subCode
     * @param subDescribe
     */
    public void modifySubSystemBySubId(@Param("subId") String subId,
                                       @Param("subName") String subName,
                                       @Param("subCode") String subCode,
                                       @Param("subDescribe") String subDescribe);

    /**
     * 批量删除子系统信息
     * @param subIdList
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteSubSystemBySubIdList(@Param("subIdList")List<String> subIdList);

}

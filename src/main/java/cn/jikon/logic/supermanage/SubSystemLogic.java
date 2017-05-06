package cn.jikon.logic.supermanage;

import cn.jikon.domain.dao.supermanage.SubSystemDao;
import cn.jikon.domain.entity.managesystem.SubSystem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wujiapeng on 17/4/25.
 */
@Service
public class SubSystemLogic {

    @Resource
    private SubSystemDao subSystemDao;

    public int getSubSystemCount(){
        return subSystemDao.getSubSystemCount();
    }

    public List<SubSystem> getListSubSystem(){
        return subSystemDao.getListSubSystem();
    }

    public SubSystem getSubSystemBySubCode(String subCode){
        return subSystemDao.getSubSystemBySubCode(subCode);
    }

    public void addSubSystem(String subId, String subName, String subCode, String subDescribe){
        subSystemDao.addSubSystem(subId, subName, subCode, subDescribe);
    }

    public SubSystem getSubSystemBySubId(String subId){
        return subSystemDao.getSubSystemBySubId(subId);
    }

    public void modifySubSystemBySubId(String subId, String subName, String subCode, String subDescribe){
        subSystemDao.modifySubSystemBySubId(subId, subName, subCode, subDescribe);
    }

    public void deleteSubSystemBySubIdList(List<String> subIdList){
        subSystemDao.deleteSubSystemBySubIdList(subIdList);
    }

}

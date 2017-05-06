package cn.jikon.service.usermanage;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.common.util.ValidateUtil;
import cn.jikon.domain.entity.managesystem.Organization;
import cn.jikon.domain.entity.managesystem.OrganizationMap;
import cn.jikon.domain.vo.OrganizationVo;
import cn.jikon.logic.usermanage.OrganizationLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cn.jikon.logic.usermanage.OrganizationMapLogic;
import cn.jikon.logic.usermanage.OrganizationUserMapLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    OrganizationLogic organizationLogic;

    @Autowired
    OrganizationMapLogic organizationMapLogic;
    @Autowired
    OrganizationUserMapLogic organizationUserMapLogic;

    public List<OrganizationVo> getOrganizationList(String fatherId) throws BizException {
        checkOrganization(fatherId);
        List<Organization> organizationList = organizationLogic.getOrganizationListByFatherId(fatherId);
        List<OrganizationVo> organizationVoList = new ArrayList<OrganizationVo>();
        OrganizationVo organizationVo;
        boolean flag = true;
        int userCount;
        int organizationCount;
        String organizationId;
        for (Organization organization : organizationList) {
            organizationId = organization.getId();
            userCount = organizationUserMapLogic.getUserCountByOrganizationId(organizationId);
            organizationCount = organizationMapLogic.getOrganizationCountByFatherId(organizationId);
            //判断组织下面是否有子组织或者人员
            flag = (0 == userCount && 0 == organizationCount);
            organizationVo = new OrganizationVo();
            organizationVo.setFatherId(fatherId);
            organizationVo.setLabel(organization.getName());
            organizationVo.setId(organizationId);
            organizationVo.setCheckIsEmpty(flag);
            organizationVoList.add(organizationVo);
        }
        return organizationVoList;
    }

    public OrganizationVo addOrganization(String fatherId, String name) throws BizException {
        checkOrganization(fatherId);
        ValidateUtil.validateLength_organization_name(name);
        checkOrganizationName(name);

        organizationLogic.addOrganization(fatherId, name);
        //获取新增加的组织ID 并把他转化成业务对象给前端
        String organizationId = organizationLogic.getOrganizationByName(name).getId();
        OrganizationVo organizationVo = new OrganizationVo();
        organizationVo.setId(organizationId);
        organizationVo.setLabel(name);
        organizationVo.setCheckIsEmpty(true);
        return organizationVo;
    }

    public void updateOrganization(String organizationId, String fatherId, String name) throws BizException {
        checkOrganization(fatherId);
        checkOrganization(organizationId);
        ValidateUtil.validateLength_organization_name(name);
        Organization organization = organizationLogic.getOrganizationById(organizationId);
        if (!name.equals(organization.getName())) {
            checkOrganizationName(name);
        }
        try {
            organizationLogic.updateOrganization(organizationId, fatherId, name);
        } catch (Exception e) {
            throw new BizException(EduErrors.CODE_100002, "更新失败");
        }
    }

    public void delOrganization(List<String> organizationIdList) throws BizException {
        for(String organizationId : organizationIdList){
            organizationLogic.delOrganization(organizationId);
        }
    }

    private void checkOrganization(String organizationId) throws BizException {
        if (null == organizationLogic.getOrganizationById(organizationId)) {
            throw new BizException(EduErrors.CODE_100008, "组织不存在");
        }
    }

    private void checkOrganizationName(String name) throws BizException {
        Organization organization = organizationLogic.getOrganizationByName(name);
        if (null != organization) {
            throw new BizException(EduErrors.CODE_100012, "组织名重复");
        }
    }

}

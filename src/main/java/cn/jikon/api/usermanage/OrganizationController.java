package cn.jikon.api.usermanage;

import cn.jikon.api.BaseController;
import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.vo.OrganizationVo;
import cn.jikon.domain.vo.UserVo;
import cn.jikon.service.usermanage.OrganizationService;
import cn.jikon.service.usermanage.OrganizationUserMapService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/api/userManagerCenter")
public class OrganizationController extends BaseController {

    @Autowired
    OrganizationService organizationService;
    @Autowired
    OrganizationUserMapService orgUserMapService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/getOrganizationList")
    public String getOrganizationList(@RequestBody String requestBody) {
        JSONObject jsonObject = null;
        Map<String, Object> data = null;
        try {
            jsonObject = parseObject(requestBody);
            String fatherId = getRequestParam(jsonObject, "fatherId", "", false);
            List<OrganizationVo> organizationVoList = organizationService.getOrganizationList(fatherId);
            List<UserVo> userVoList = orgUserMapService.getUserListByOrganizationId(fatherId);
            data = new HashMap<String, Object>();
            data.put("userVoList", userVoList);
            data.put("organizationVoList", organizationVoList);
            return getResponse(EduErrors.CODE_100000, "操作成功", data);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/addOrganization")
    public String addOrganization(@RequestBody String requestBody) {
        JSONObject jsonObject = null;
        try {
            jsonObject = parseObject(requestBody);
            String fatherId = getRequestParam(jsonObject, "fatherId", "", false);
            String name = getRequestParam(jsonObject, "name", "", true);
            OrganizationVo organizationVo = organizationService.addOrganization(fatherId, name);
            return getResponse(EduErrors.CODE_100000, "新增完成", organizationVo);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/updateOrganization")
    public String updateOrganization(@RequestBody String requestBody) {
        JSONObject jsonObject = null;
        try {
            jsonObject = parseObject(requestBody);
            String organizationId = getRequestParam(jsonObject, "orgId", "", true);
            String fatherId = getRequestParam(jsonObject, "fatherId", "", false);
            String name = getRequestParam(jsonObject, "name", "", true);
            organizationService.updateOrganization(organizationId, fatherId, name);
            return getResponse(EduErrors.CODE_100000, "更新成功", null);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/delOrganization")
    public String delOrganization(@RequestBody String requestBody) {
        try {
            JSONArray organizationIds = JSONArray.parseArray(requestBody);
            List<String> organizationIdList = assertOrganizationIdList(organizationIds);
            organizationService.delOrganization(organizationIdList);
            return getResponse(EduErrors.CODE_100000, "已删除", null);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    private List<String> assertOrganizationIdList(JSONArray organizationIdJsonArray) throws BizException {
        List<String> organizationIdList = new ArrayList<String>();
        for (Object organizationIdObj : organizationIdJsonArray) {
            JSONObject jSONObject = parseObject(organizationIdObj.toString());
            String organizationId = getRequestParam(jSONObject, "id", "", true);
            organizationIdList.add(organizationId);
        }
        return organizationIdList;
    }

}
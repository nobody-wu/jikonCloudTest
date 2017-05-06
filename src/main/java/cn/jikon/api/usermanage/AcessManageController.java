package cn.jikon.api.usermanage;

import cn.jikon.api.BaseController;
import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.vo.ManUserBindVo;
import cn.jikon.service.usermanage.AcessManagerService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/userManagerCenter")
public class AcessManageController extends BaseController {

    @Autowired
    private AcessManagerService acessManagerService;

    @RequestMapping(value = "/getManageUserBindList", method = RequestMethod.POST)
    public String getManUserBindList(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String manageUserId = getRequestParam(jSONObject, "manageUserId", "", true);
            List<ManUserBindVo> manUserBindVoList = acessManagerService.getManagerUserBindList(manageUserId);
            return getResponse(EduErrors.CODE_100000, "成功", manUserBindVoList);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/bindSubsystemModule", method = RequestMethod.POST)
    public String bindSubsystemModule(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        Map<String, Object> data = null;
        try {
            jSONObject = parseObject(requestBody);
            String manageUserId = getRequestParam(jSONObject, "manageUserId", "", true);
            String subsystemModuleId = getRequestParam(jSONObject, "subsystemModuleId", "", true);
            acessManagerService.bindSubsystemModule(manageUserId,subsystemModuleId);
            return getResponse(EduErrors.CODE_100000, "成功", data);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/unbindSubsystemModule", method = RequestMethod.POST)
    public String unbindSubsystemModule(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        Map<String, Object> data = null;
        try {
            jSONObject = parseObject(requestBody);
            String manageUserId = getRequestParam(jSONObject, "manageUserId", "", true);
            String subsystemModuleId = getRequestParam(jSONObject, "subsystemModuleId", "", true);
            acessManagerService.unbindSubsystemModule(manageUserId,subsystemModuleId);
            return getResponse(EduErrors.CODE_100000, "成功", data);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

}

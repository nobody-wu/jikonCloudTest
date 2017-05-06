package cn.jikon.api.module;

import cn.jikon.api.BaseController;
import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.vo.ModuleVo;
import cn.jikon.domain.vo.PageVo;
import cn.jikon.service.module.ModuleService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangxin on 2017/4/25.
 */
@RestController
@RequestMapping("/api/superManagerUser/")
public class ModuleController extends BaseController {

    @Autowired
    ModuleService moduleService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/addModule", method = RequestMethod.POST)
    public String addModule(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String name = getRequestParam(jSONObject, "name", "", true);
            String describe = getRequestParam(jSONObject, "describe", "", false);
            String subsystemId = getRequestParam(jSONObject, "subsystemId", "", true);
            ModuleVo moduleVo = moduleService.addModule(name, describe, subsystemId);
            return getResponse(EduErrors.CODE_100000, "增加成功", moduleVo);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/delModule", method = RequestMethod.POST)
    public String delModule(@RequestBody String requestBody) {
        try {
            JSONArray moduleIdJsonArray = JSON.parseArray(requestBody);

            //List<JSONArray>数组转化成List<String>
            List<String> moduleIdList = new ArrayList<String>();
            for (Object moduleIdObj : moduleIdJsonArray) {
                JSONObject obj = parseObject(moduleIdObj.toString());
                String moduleId = getRequestParam(obj, "id", "", true);
                moduleIdList.add(moduleId);
            }

            moduleService.delModule(moduleIdList);
            return getResponse(EduErrors.CODE_100000, "删除成功", null);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/updateModule", method = RequestMethod.POST)
    public String updateModule(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String name = getRequestParam(jSONObject, "name", "", true);
            String moduleId = getRequestParam(jSONObject, "moduleId", "", true);
            String describe = getRequestParam(jSONObject, "describe", "", false);
            String subsystemId = getRequestParam(jSONObject, "subsystemId", "", true);
            moduleService.updateModule(moduleId, name, subsystemId, describe);
            return getResponse(EduErrors.CODE_100000, "更新成功", null);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getModuleList", method = RequestMethod.POST)
    public String getModuleList(@RequestBody String requestBody) {
        JSONObject jSONObject = null;
        try {
            jSONObject = parseObject(requestBody);
            String pageIndex = getRequestParam(jSONObject, "pageIndex", "", true);
            String pageCount = getRequestParam(jSONObject, "pageCount", "", true);
            int count = moduleService.getModuleCount();
            PageVo<ModuleVo> pageVo = getPageVo(pageIndex, Integer.parseInt(pageCount), count);
            List<ModuleVo> moduleVoList = new ArrayList<ModuleVo>();
            if (count > 0) {
                moduleVoList = moduleService.getModuleList(pageVo.getStart(), pageVo.getPageCount());
            }
            pageVo.setList(moduleVoList);
            return getResponse(EduErrors.CODE_100000, "获取成功", pageVo);
        } catch (BizException e) {
            e.printStackTrace();
            return getResponse(e.getErrno(), e.getMessage(), null);
        }
    }

}

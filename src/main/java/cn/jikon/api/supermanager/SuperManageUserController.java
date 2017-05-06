package cn.jikon.api.supermanager;

import cn.jikon.api.BaseController;
import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.domain.entity.managesystem.SubSystem;
import cn.jikon.service.supermanager.SuperManageUserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wujiapeng
 * @ApiGroup 超级管理员对子系统信息的管理
 * @date 2017-4-25
 */
@RestController
@RequestMapping("/api/superManagerUser")
public class SuperManageUserController extends BaseController{

    @Resource
    private SuperManageUserService superManageUserService;

    /**
     * @ApiMethod:true
     * @ApiMethodName: 查询子系统列表信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"all"})
    @RequestMapping("/getListSubSystem")
    public String getListSubSystem() {
        int pageCount = 0;
        try {
//            JSONObject jsonObject = parseObject(params);
//            String pageIndexStr = getRequestParam(jsonObject, "pageIndex", "", true);//当前页数
//            String pageCountStr = jsonObject.getString("pageCount");
//            int pageIndex = Integer.parseInt(pageIndexStr);//比valueOf更有效
//            //如果前端传了pageCount参数，那就用前端传的
//            if (null == pageCountStr){
//                pageCount = BaseController.pageCount;
//            }
//            if (null != pageCountStr){
//                pageCount = Integer.parseInt(pageCountStr);
//            }
//            if (pageCount <= 0){
//                return getResponse(EduErrors.CODE_100000, "指定每页条数必须大于0", null);
//            }
            List<SubSystem> subSystemList = superManageUserService.getListSubSystem();
            return getResponse(EduErrors.CODE_100000, "请求成功", subSystemList);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), e);
        } catch (Exception e){
            return getResponse(EduErrors.CODE_100004, "查询子系统列表失败", e);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 增加子系统信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/addSubSystem")
    public String addSuperManageUser(@RequestBody String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            String subName = getRequestParam(jsonObject, "name", "", true);
            String subCode = getRequestParam(jsonObject, "code", "", true);
            String subDescribe = getRequestParam(jsonObject, "describe", "", true);
            superManageUserService.addSubSystem(subName, subCode, subDescribe);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        } catch (Exception e){
            return getResponse(EduErrors.CODE_100001, "新增子系统信息失败", null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 更新子系统信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/modifySubSystemBySubId")
    public String modifySubSystemBySubId(@RequestBody String params /*HttpServletRequest request*/) {
        try {
            JSONObject jsonObject = parseObject(params);
            String subName = getRequestParam(jsonObject, "name", "", true);
            String subDescribe = getRequestParam(jsonObject, "describe", "", true);
            String subId = getRequestParam(jsonObject, "id", "", true);
            String subCode = getRequestParam(jsonObject, "code", "", true);
            superManageUserService.modifySubSystemBySubId(subId, subName, subCode, subDescribe);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        } catch (Exception e){
            return getResponse(EduErrors.CODE_100002, "更新子系统信息失败", null);
        }
    }

    /**
     * @ApiMethod:true
     * @ApiMethodName: 批量删除子系统信息
     * @ApiRequestParamsDes:
     * @ApiRequestParams:
     * @ApiResponse: 成功返回:
     * 异常返回:
     * @ApiMethodEnd
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/deleteSubSystemBySubIdList")
    public String deleteSubSystemBySubIdList(@RequestBody String params) {
        try {
            JSONArray subIdJsonArray = JSON.parseArray(params);
            List<String> subIdList = assertSubIdList(subIdJsonArray);
            superManageUserService.deleteSubSystemBySubIdList(subIdList);
            return getResponse(EduErrors.CODE_100000, "请求成功", null);
        } catch (BizException e) {
            return getResponse(e.getErrno(), e.getMessage(), null);
        } catch (Exception e){
            return getResponse(EduErrors.CODE_100003, "批量删除子系统信息失败", null);
        }
    }


    /**
     * 组装子系统ID集合
     * @param subIdJsonArray
     * @return
     * @throws BizException
     */
    private List<String> assertSubIdList(JSONArray subIdJsonArray) throws Exception {
        List<String> subIdList = new ArrayList<String>();
        for (Object subIdObj : subIdJsonArray) {
            JSONObject obj = parseObject(subIdObj.toString());
            String subId = getRequestParam(obj, "id", "", true);
            superManageUserService.validSubSystemAndDeleteMap(subId);
            subIdList.add(subId);
        }
        return subIdList;
    }


}

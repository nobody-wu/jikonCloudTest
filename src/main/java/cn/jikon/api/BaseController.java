package cn.jikon.api;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.common.util.JsonUtil;
import cn.jikon.domain.vo.PageVo;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController<T> {
    //protected transient Logger logger = LogManager.getLogger(getClass());
    protected static final int pageCount = 20;

    protected String getResponse(int code, String message, Object data) {
        JSONObject result = new JSONObject();
        Map<String, Object> meta = new HashMap<String, Object>();
        meta.put("code", code);
        meta.put("message", message);
        result.put("meta", meta);
        if (data != null) {
            result.put("data", data);
        }
        return result.toJSONString();
    }

    protected JSONObject parseObject(String requestBody) throws BizException {
        JSONObject jSONObject = null;
        try {
            jSONObject = JsonUtil.getRequestJSONContent(requestBody);
        } catch (Exception e) {
            throw new BizException(EduErrors.CODE_100012, "请求内容异常");
        }
        return jSONObject;

    }

    protected Map<String, Object> checkpassword(String password, String oldPassword, String userId, String userType, String account) throws BizException {
        Map<String, Object> data = null;
        if (password.equals(oldPassword)) {
            data = new HashMap<String, Object>();
            data.put("userId", userId);
            data.put("mobile", account);
            data.put("type", userType);
            data.put("access_token", "");
        } else {
            throw new BizException(EduErrors.CODE_100009, "密码错");
        }

        return data;
    }

    protected String getRequestParam(Map<String, ?> params, String paramName, String defaultValue, boolean checkIsEmpty) throws BizException {
        String resut = JsonUtil.getRequestParam(params, paramName, "");
        if (checkIsEmpty && "".equals(resut)) {
            throw new BizException(EduErrors.CODE_100007, paramName + " is empty");
        }
        return resut;

    }

    protected PageVo<T> getPageVo(String pageIndex, int pageCount, int count) {
        if ("".equals(pageIndex)) {
            pageIndex = "0";
        }
        PageVo<T> pageVo = new PageVo<T>(Integer.parseInt(pageIndex), count, pageCount);
        pageVo.init();
        return pageVo;
    }

    public static List<?> getListObject(String jsonString) {

        return JsonUtil.getListObject(jsonString);
    }

    public static Object getObject(String jsonString) {

        return JsonUtil.getObject(jsonString);
    }

}

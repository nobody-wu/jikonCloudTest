package cn.jikon.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by lwj on 2017/3/28.
 */
public class JsonUtil {
    public static String getRequestParam(Map<String, ?> params, String paramName, String defaultValue) {
        if (!params.containsKey(paramName) && !params.containsKey(paramName.toLowerCase())) {
            return defaultValue;
        }
        try {
            if (params instanceof JSONObject) {
                if (params.containsKey(paramName)) {
                    return ((JSONObject) params).getString(paramName);
                } else {
                    return ((JSONObject) params).getString(paramName.toLowerCase());
                }
            }
            Object value = null;
            if (params.containsKey(paramName)) {
                value = params.get(paramName);
            } else {
                value = params.get(paramName.toLowerCase());
            }
            if (value == null) {
                return "";
            }
            return (String) value;
        } catch (Exception e) {
            throw new java.lang.IllegalArgumentException(e.getMessage());
        }
    }

    public static JSONObject getRequestJSONContent(String requestBody) throws Exception {
        JSONObject requestJson = JSON.parseObject(requestBody);
        if (null == requestJson) {
            throw new Exception("requestBody is null!"); // 请求内容不能为空
        }
        return requestJson;
    }

    public static List<Object> getListObject(String jsonString) {
        List<Object> objects = JSON.parseArray(jsonString, Object.class);
        return objects;
    }

    public static Object getObject(String jsonString) {
        Object object = JSON.parseObject(jsonString, Object.class);
        return object;
    }
}

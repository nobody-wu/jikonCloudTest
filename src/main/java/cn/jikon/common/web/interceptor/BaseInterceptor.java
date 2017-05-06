package cn.jikon.common.web.interceptor;

import cn.jikon.common.web.NoWebLog;
import cn.jikon.common.web.RecordLogger;
import cn.jikon.common.web.WebHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by wangxin on 2017/4/21.
 */

public abstract class BaseInterceptor  extends HandlerInterceptorAdapter {

    protected static Logger webLogger = Logger.getLogger("web");
    private Map<HandlerMethod, Boolean> logHandlerMethods = new HashMap<HandlerMethod, Boolean>();

    protected Set<String> getHeaderParamNames() {
        return null;
    }

    // 根据handler判断是否记录日志
    protected boolean isLogRequest(Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (isNoWebLogMethod(handlerMethod)) {
            return false;
        }
        else {
            return true;
        }
    }

    protected void logQueryString(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (queryString == null || queryString.length() < 1) {
            return;
        }

        webLogger.info("QueryString: " + queryString);

        Map<String, String> params = WebHelper.getRequestURLParams(request);
        for (String name : params.keySet()) {
            if (name.equals("sign")) { // sign不用记录了
                continue;
            }
            String value = params.get(name);
            webLogger.info("* " + name + ": " + value);
        }
    }

    protected void logHeaderParams(HttpServletRequest request) {
        Map<String, String> params = getHeaderParams(request);
        if (params == null) {
            return;
        }
        for (String name : params.keySet()) {
            String value = params.get(name);
            webLogger.info("** " + name + ": " + value);
        }
    }

    protected void logRequestBody(HttpServletRequest request) {
        webLogger.info("REQUEST BODY: ");

        byte[] requestData;
        try {
            requestData = WebHelper.getRequestData(request);
        }
        catch (IOException e) {
            return;
        }

        if (requestData == null || requestData.length < 1) {
            return;
        }

        String requestString = getRequestBody(requestData);
        webLogger.info(requestString);
    }

    private Map<String, String> getHeaderParams(HttpServletRequest request) {
        Set<String> headerParamNames = getHeaderParamNames();
        if (headerParamNames == null) {
            return null;
        }

        Map<String, String> params = new HashMap<String, String>();

        for (String paramName : headerParamNames) {
            String paramValue = request.getHeader(paramName);
            if (paramValue == null) {
                continue;
            }

            if (paramValue.length() > 0) {
                try {
                    paramValue = URLDecoder.decode(paramValue, "UTF-8");
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Object paramObject = convertJson(paramValue);
                if (paramObject != null) {
                    if (paramObject instanceof JSONArray) {
                        paramValue = RecordLogger.formatParams(paramObject);
                    }
                    else if (paramObject instanceof JSONObject) {
                        paramValue = RecordLogger.formatParams(paramObject);
                    }
                }
            }

            params.put(paramName, paramValue);
        }
        return params;
    }

    private String getRequestBody(byte[] requestData) {
        String requestString = new String(requestData);
        Object requestObject = convertJson(requestString);

        if (requestObject != null) {
            return RecordLogger.formatParams(requestObject);
        }

        if (requestString.length() < 1) {
            return "";
        }

        if (requestString.length() > 64) {
            requestString = requestString.substring(0, 64) + "...";
        }
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < requestString.length(); i++) {
            char ch = requestString.charAt(i);
            if (ch > 0 && ch <= 127) {
                if (ch == 13 || ch == 10) {
                    s.append(" ");
                }
                else {
                    s.append(ch);
                }
            }
            else {
                s.append("?");
            }
        }
        requestString = s.toString();

        return requestString;
    }

    private Object convertJson(String test) {
        if (test == null || test.length() < 2) {
            return null;
        }
        if (test.startsWith("[") && test.endsWith("]")) {
            try {
                return JSON.parseArray(test);
            }
            catch (Exception e) {
                return null;
            }
        }
        else if (test.startsWith("{") && test.endsWith("}")) {
            try {
                return JSON.parseObject(test);
            }
            catch (Exception e) {
                return null;
            }
        }
        else {
            return null;
        }
    }

    private boolean isNoWebLogMethod(HandlerMethod handlerMethod) {
        if (logHandlerMethods.containsKey(handlerMethod)) {
            return logHandlerMethods.get(handlerMethod);
        }

        synchronized (logHandlerMethods) {
            if (logHandlerMethods.containsKey(handlerMethod)) {
                return logHandlerMethods.get(handlerMethod);
            }

            Method method = handlerMethod.getMethod();

            NoWebLog noWebLogAnnotation = method.getDeclaredAnnotation(NoWebLog.class);
            if (noWebLogAnnotation != null) {
                logHandlerMethods.put(handlerMethod, true);
                return true;
            }

            noWebLogAnnotation = method.getDeclaringClass().getDeclaredAnnotation(NoWebLog.class);
            if (noWebLogAnnotation != null) {
                logHandlerMethods.put(handlerMethod, true);
                return true;
            }

            logHandlerMethods.put(handlerMethod, false);
            return false;
        }
    }
}
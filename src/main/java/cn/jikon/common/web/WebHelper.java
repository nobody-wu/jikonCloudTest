package cn.jikon.common.web;

import cn.jikon.common.BizException;
import cn.jikon.common.EduErrors;
import cn.jikon.common.rules.BizResult;
import cn.jikon.common.util.Utils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by wangxin on 2017/4/21.
 */
public class WebHelper {


    public static String getClientIP(HttpServletRequest request) {
        String[] headerNames = {"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP"};
        String ip;
        for (String headerName : headerNames) {
            ip = request.getHeader(headerName);

            if (ip == null) {
                ip = request.getHeader(headerName.toLowerCase());
            }
            if (Utils.isIp(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    public static String getRequestPath(HttpServletRequest request) {
        String path = request.getRequestURI();
        int a = path.indexOf("?");
        if (a > 0) {
            path = path.substring(0, a);
        }
        if (path.substring(path.length() - 1).equals("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * 获取请求参数. 先从header里获取, 再从cookie里获取, 再从post, 最后get
     *
     * @param request
     * @param paramName
     * @return
     */
    public static String getRequestParam(HttpServletRequest request, String paramName) throws BizException {
        return getRequestParam(request, paramName, "");
    }

    /**
     * 获取请求参数. 先从header里获取, 再从cookie里获取, 再post, 最后get
     *
     * @param request
     * @param paramName
     * @param defaultValue
     */
    public static String getRequestParam(HttpServletRequest request, String paramName, String defaultValue) throws BizException {
        Map<String, String> params = getRequestParams(request);
        return getRequestParam(params, paramName, defaultValue);
    }

    public static String getRequestParam(HttpServletRequest request, String paramName, String defaultValue, boolean isContainsPost) throws BizException {
        Map<String, String> params = getRequestParams(request, isContainsPost);
        return getRequestParam(params, paramName, defaultValue);
    }

    public static String getRequestParam(Map<String, ?> params, String paramName) throws BizException {
        return getRequestParam(params, paramName, "");
    }

    /**
     * 获取请求参数
     *
     * @param params
     * @param paramName
     * @param defaultValue
     */
    public static String getRequestParam(Map<String, ?> params, String paramName, String defaultValue) throws BizException {
        if (!params.containsKey(paramName) && !params.containsKey(paramName.toLowerCase())) {
            return defaultValue;
        }
        // JSONObject对象:
        if (params instanceof JSONObject) {
            if (params.containsKey(paramName)) {
                return ((JSONObject) params).getString(paramName);
            } else {
                return ((JSONObject) params).getString(paramName.toLowerCase());
            }
        }

        // 其他情况:
        Object value = null;
        if (params.containsKey(paramName)) {
            value = params.get(paramName);
        } else {
            value = params.get(paramName.toLowerCase());
        }
        if (value == null) {
            return null;
        }
        BizResult.ensureTrue(value instanceof String, EduErrors.CODE_100009, "param " + paramName + " not String");
        return (String) value;


    }

    protected Object getJsonParam(JSONObject requestJson, String paramName, Object defaultValue) {
        if (!requestJson.containsKey(paramName)) {
            return defaultValue;
        }
        Object value = requestJson.get(paramName);
        if (null == value) {
            return defaultValue;
        }
        if (value instanceof String) {
            return value;
        }
        return value;
    }

    public static Object getRequestObject(Map<String, ?> params, String paramName) {
        return getRequestObject(params, paramName, null);
    }

    public static Object getRequestObject(Map<String, ?> params, String paramName, Object defaultValue) {
        if (!params.containsKey(paramName) && !params.containsKey(paramName.toLowerCase())) {
            return defaultValue;
        }
        Object value = null;
        if (params.containsKey(paramName)) {
            value = params.get(paramName);
        } else {
            value = params.get(paramName.toLowerCase());
        }
        if (value == null) {
            return null;
        }
        return value;
    }

    public static Map<String, String> getRequestParams(HttpServletRequest request) {
        return getRequestParams(request, false);
    }

    /**
     * 获取请求参数. 先从header里获取, 再从cookie里获取, 再post, 最后get
     *
     * @param request
     * @return
     */
    public static Map<String, String> getRequestParams(HttpServletRequest request, boolean isContainsPost) {
        Map<String, String> params = new HashMap<String, String>();

        Enumeration<?> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = (String) headerNames.nextElement();
                String value = request.getHeader(name);
                if (null == value) {
                    value = "";
                }
                value = urldecode(value);
                params.put(name, value);
            }
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                String name = cookies[i].getName();
                String value = cookies[i].getValue();
                if (null == value) {
                    value = "";
                }
                value = urldecode(value);
                params.put(name, value);
            }
        }

        if (isContainsPost) {
            Enumeration<?> paramNames = request.getParameterNames();
            if (paramNames != null) {
                while (paramNames.hasMoreElements()) {
                    String name = (String) paramNames.nextElement();
                    String value = request.getParameter(name);
                    if (null == value) {
                        value = "";
                    }
                    value = urldecode(value);
                    params.put(name, value);
                }
            }
        }

        Map<String, String> urlParams = getRequestURLParams(request);
        for (String name : urlParams.keySet()) {
            String value = urlParams.get(name);
            params.put(name, value); // decode过了
        }

        return params;
    }

    public static byte[] getRequestData(HttpServletRequest request) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(request.getInputStream());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];
        int read = 0;
        IOException exception = null;
        try {
            while ((read = inputStream.read(buffer, 0, bufferSize)) > 0) {
                outputStream.write(buffer, 0, read);
            }
        } catch (IOException e) {
            exception = e;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {

            }

            try {
                outputStream.close();
            } catch (IOException e) {

            }
        }

        if (exception != null) {
            throw exception;
        }

        return outputStream.toByteArray();
    }

    public static String getRequestURLParam(HttpServletRequest request, String paramName) {
        return getRequestURLParam(request, paramName, "");
    }

    public static String getRequestURLParam(HttpServletRequest request, String paramName, String defaultValue) {
        String queryString = request.getQueryString();
        if (queryString == null) {
            return defaultValue;
        }
        String result = defaultValue;
        String[] items = queryString.split("\\&");
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            int a = item.indexOf("=");
            if (a < 1) {
                continue;
            }

            String name = item.substring(0, a).trim();
            if (name.length() < 1) {
                continue;
            }

            if (!name.equalsIgnoreCase(paramName)) {
                continue;
            }

            String value = "";
            if (a <= item.length() - 1) {
                value = item.substring(a + 1);
            }

            result = urldecode(value);
        }

        return result;
    }

    public static Map<String, String> getRequestURLParams(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return parseRequestParams(queryString);
    }

    public static ResponseEntity<byte[]> getResponseEntity(byte[] data, boolean noCache, String contentType, HttpStatus status) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", contentType);
        return getResponseEntity(data, noCache, headers, status);
    }

    public static ResponseEntity<byte[]> getResponseEntity(byte[] data, boolean noCache, Map<String, String> headers, HttpStatus status) {
        return createResponseEntity(data, noCache, headers, status);
    }

    public static ResponseEntity<String> getResponseEntity(String data, boolean noCache, Map<String, String> headers, HttpStatus status) {
        return createResponseEntity(data, noCache, headers, status);
    }

    public static ResponseEntity<JSONObject> getResponseEntity(int code, String message, Object data, HttpStatus status) {
        return getResponseEntity(code, message, data, true, null, status);
    }

    public static ResponseEntity<JSONObject> getResponseEntity(int code, String message, Object data, boolean noCache, Map<String, String> headers) {
        HttpStatus status = HttpStatus.OK;
        return getResponseEntity(code, message, data, noCache, headers, status);
    }

    public static ResponseEntity<JSONObject> getResponseEntity(int code, String message, Object data, boolean noCache, Map<String, String> headers, HttpStatus status) {
        Map<String, String> messages = new HashMap<>();
        messages.put("message", message);

        Map<String, Object> datas = new HashMap<>();
        datas.put("content", data);

        if (data == null) {
            datas = null;
        }
        return getResponseEntity(code, messages, datas, noCache, headers, status);
    }

    public static ResponseEntity<JSONObject> getResponseEntity(int code, Map<String, String> messages, Map<String, Object> datas, boolean noCache, Map<String, String> headers, HttpStatus status) {
        JSONObject result = new JSONObject();

        JSONObject output = new JSONObject();

        JSONObject info = new JSONObject();
        info.put("code", code);

        if (messages != null) {
            for (String messageName : messages.keySet()) {
                info.put(messageName, messages.get(messageName));
            }
        }

        output.put("info", info);

        if (datas != null) {
            for (String dataName : datas.keySet()) {
                output.put(dataName, datas.get(dataName));
            }
        }

        result.put("response", output);

        return createResponseEntity(result, noCache, headers, status);
    }

    public static Map<String, String> parseRequestParams(String data) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> params2 = parseRequestParams2(data);
        for (String name : params2.keySet()) {
            params.put(name, params2.get(name)[0]);
        }
        return params;
    }

    public static Map<String, String[]> parseRequestParams2(String data) {
        HashMap<String, String[]> params = new HashMap<String, String[]>();
        if (data == null || data.length() < 1) {
            return params;
        }

        StringTokenizer st = new StringTokenizer(data, "&");
        while (st.hasMoreTokens()) {
            String pair = st.nextToken();
            int pos = pair.indexOf('=');
            if (pos == -1) {
                continue;
            }
            String name = pair.substring(0, pos);
            String value = urldecode(pair.substring(pos + 1, pair.length()));

            if (params.containsKey(name)) {
                String[] oldValues = params.get(name);
                String[] values = new String[oldValues.length + 1];
                System.arraycopy(oldValues, 0, values, 0, oldValues.length);
                values[values.length - 1] = value;
                params.put(name, values);
            } else {
                String[] values = new String[1];
                values[0] = value;
                params.put(name, values);
            }
        }

        return params;
    }

    // /**
    // * 输出数据
    // *
    // * @param response
    // * @param code
    // * @param message
    // * @param data
    // * @throws IOException
    // */
    // public static void renderJson(HttpServletResponse response, int code,
    // String message, Object data) throws IOException {
    // int httpCode = 200;
    // /*
    // switch (code) {
    // case Errors.ERR_SYS_FORBIDDEN:
    // httpCode = 403;
    // break;
    // case Errors.ERR_SYS_NOT_EXISTS:
    // httpCode = 404;
    // break;
    // }
    // */
    // renderJson(response, code, message, data, httpCode);
    // }
    //
    // /**
    // * 输出数据
    // *
    // * @param response
    // * @param code
    // * @param message
    // * @param data
    // * @param httpCode
    // * @throws IOException
    // */
    // public static void renderJson(HttpServletResponse response, int code,
    // String message, Object data, int httpCode) throws IOException {
    // JSONObject output = new JSONObject();
    // output.put("code", code);
    // output.put("message", message);
    // if (null != data) {
    // output.put("data", data);
    // }
    // if (httpCode != 200) {
    // response.setStatus(httpCode);
    // }
    // response.getWriter().write(output.toJSONString());
    // response.getWriter().close();
    // }
    //
    // /**
    // * 输出异常
    // * 如果是BizException, 属于业务异常, 输出错误码和错误描述;
    // * 其他异常, 直接输出HTTP 514错误或其他错误. 并且把错误描述放Http Header里输出
    // *
    // * @param response
    // * @param e
    // * @param data
    // * @throws IOException
    // */
    // public static void renderJson(HttpServletResponse response, Throwable e,
    // Object data) throws IOException {
    // String message = e.getMessage();
    // if (e instanceof BizException) {
    // renderBizException(response, (BizException) e, data);
    // }
    // else {
    // response.sendError(514, message);
    // e.printStackTrace();
    // }
    // }
    //
    // /**
    // * 输出异常
    // *
    // * @param request
    // * @param e
    // * @throws IOException
    // */
    // public static void renderJson(HttpServletResponse response, Throwable e)
    // throws IOException {
    // renderJson(response, e, null);
    // }
    //
    // /**
    // * 输出 BizException 异常
    // *
    // * @param response
    // * @param e
    // * @param data
    // * @throws IOException
    // */
    // private static void renderBizException(HttpServletResponse response,
    // BizException e, Object data) throws IOException {
    // int errno = e.getErrno();
    // String errmsg = e.getMessage();
    //
    // //特殊处理
    // String serviceMethodName = getLastCauseServiceMethod(e);
    // if (serviceMethodName != null) {
    // if (serviceMethodName.equals("TestService.test")) {
    // renderJson(response, errno, errmsg, data);
    // return;
    // }
    // }
    //
    // renderJson(response, errno, errmsg, data);
    // }

    public static <T> ResponseEntity<T> createResponseEntity(T output) {
        return createResponseEntity(output, true, null, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> createResponseEntity(T output, HttpStatus status) {
        return createResponseEntity(output, true, null, status);
    }

    public static <T> ResponseEntity<T> createResponseEntity(T output, Map<String, String> headers, HttpStatus status) {
        return createResponseEntity(output, true, headers, status);
    }

    /**
     * createResponseEntity
     *
     * @param output
     * @param noCache
     * @param headers
     * @param status
     * @return
     */
    public static <T> ResponseEntity<T> createResponseEntity(T output, boolean noCache, Map<String, String> headers, HttpStatus status) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            for (String name : headers.keySet()) {
                String value = headers.get(name);
                httpHeaders.set(name, value);
            }
        }

        if (noCache) {
            httpHeaders.set("CacheControl", "no-cache");
            httpHeaders.set("Pragma", "no-cache");
            httpHeaders.set("Expires", "-1");
        }

        if (output instanceof byte[]) {
            httpHeaders.set("Content-Length", Integer.toString(((byte[]) output).length));
        } else if (output instanceof String) {
            // httpHeaders.set("Content-Length", Integer.toString(((String)
            // output).length()));
        } else {

        }
        return new ResponseEntity<T>(output, httpHeaders, status);
    }

    // /**
    // * 从异常里提取最后一个的Service函数
    // *
    // * @param e
    // * @return
    // */
    // private static String getLastCauseServiceMethod(BizException e) {
    // StackTraceElement[] stackTraceElements = e.getStackTrace();
    // String servicePackageName = Constants.PROJECT_PACKAGE_NAME + ".service.";
    // for (int i = 0; i < stackTraceElements.length; i++) {
    // StackTraceElement stackTraceElement = stackTraceElements[i];
    // String className = stackTraceElement.getClassName();
    // if (className.length() <= servicePackageName.length()) {
    // continue;
    // }
    // if (className.substring(0,
    // servicePackageName.length()).equals(servicePackageName)) {
    // int a = className.lastIndexOf(".");
    // String serviceName = className.substring(a + 1);
    // String methodName = stackTraceElement.getMethodName();
    // return serviceName + "." + methodName;
    // }
    // }
    // return null;
    // }


    public static boolean isJSONRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int a = uri.lastIndexOf("."); // 如果是.json请求
        if (a > 0) {
            String ftype = uri.substring(a + 1).toLowerCase();
            if (ftype.equals("json")) {
                return true;
            }
        }
        String contentType = request.getContentType(); // 如果是json请求头
        if (contentType != null && contentType.length() > 0 && contentType.endsWith("/json")) {
            return true;
        }

        return false;
    }


    public static String urldecode(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (Exception e) {
            // e.printStackTrace();
            return value;
        }
    }
}

package cn.jikon.common.web;

import cn.jikon.common.EduErrors;
import cn.jikon.common.util.CacheMap;
import cn.jikon.common.util.Constants;
import cn.jikon.common.util.JwtUtil;
import cn.jikon.common.util.UUID;
import cn.jikon.common.web.WebHelper;
import cn.jikon.common.web.interceptor.BaseInterceptor;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxin on 2017/4/21.
 */
@Component
public class WebInterceptor extends BaseInterceptor{

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token="";
//        String uri=request.getRequestURI();
//        token= WebHelper.getRequestParam( request, "token", "" );
//        if ("/api/userCenter/login".equals( uri ) || "/api/userManagerCenter/managerUserLogin".equals( uri ) || "/".equals( uri )) {
//            return true;
//        } else {
//            checkToken( response, token );
//        }
        return true;
    }

    private void checkToken(HttpServletResponse response, String token) throws Exception {
        try {

            Claims claims =  jwtUtil.parseJWT( token );

//            if(!token.equals(CacheMap.tokenMap.get(claims.getSubject()))){
//                CacheMap.tokenMap.remove(claims.getSubject());
//                String responseResult=getResponse( EduErrors.CODE_100011, "校验token失败", null );
//                response( response, responseResult );
//            }
//            CacheMap.tokenMap.remove(claims.getSubject());
            String random = UUID.getRandom();
            String tokenTemp = jwtUtil.createJWT( "", random , Constants.JWT_TTL );
            CacheMap.tokenMap.put(random,tokenTemp);
            response.addHeader("token",tokenTemp);
        } catch (Exception e) {
            String responseResult=getResponse( EduErrors.CODE_100011, "校验token失败", null );
            response( response, responseResult );
        }

    }


    private static void response(HttpServletResponse response, String responseResult) {
        response.setContentType( "application/json;charset=UTF-8" );
        PrintWriter writer=null;
        try {
            writer=response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write( responseResult );
        writer.flush();
        writer.close();
    }

    private String getResponse(int code, String message, Object data) {
        JSONObject result=new JSONObject();
        Map<String, Object> meta=new HashMap<String, Object>();
        meta.put( "code", code );
        meta.put( "message", message );
        result.put( "meta", meta );
        if (data != null) {
            result.put( "data", data );
        }
        return result.toJSONString();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        endLog( request, handler ); // 记日志
    }

    private void endLog(HttpServletRequest request, Object handler) {


    }

}

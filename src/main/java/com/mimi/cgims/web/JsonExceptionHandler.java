package com.mimi.cgims.web;

import com.mimi.cgims.util.ResultUtil;
import net.sf.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonExceptionHandler extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {
        // TODO Auto-generated method stub

        HandlerMethod mathod = (HandlerMethod) handler;
        ResponseBody body = mathod.getMethodAnnotation(ResponseBody.class);
        if (body == null) {
            return super.doResolveException(request, response, handler, ex);
        }
        ModelAndView mv = new ModelAndView();
        //设置状态码
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        //设置ContentType
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //避免乱码
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
//            response.getWriter().write(JSONObject.fromObject(ResultUtil.getFailResultMap(ex.getMessage())).toString());
            response.getWriter().write(JSONObject.fromObject(ResultUtil.getFailResultMap("操作失败，请稍后重试")).toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mv;
    }

}

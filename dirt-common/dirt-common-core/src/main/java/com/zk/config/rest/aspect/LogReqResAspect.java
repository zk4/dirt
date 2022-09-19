package com.zk.config.rest.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Aspect
public class LogReqResAspect {


    ObjectMapper objectMapper;

    public LogReqResAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)"
    )
    public void requestMapping() {
    }


    @Before(value = "requestMapping()")
    public void beforeRequestLogging(JoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            return;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String ip = request.getRemoteAddr();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        MDC.clear();
        MDC.put("sessionId", "trace-id");
        log.debug("[{}][请求接口] - {} : {} : {}",Thread.currentThread().getName(), ip, method, uri);

        Object[] parameterValues = joinPoint.getArgs();
        int parameterValuesLength = parameterValues.length;
        if (parameterValuesLength == 0) {
            log.debug("[请求参数] - 无");
            return;
        }
        Signature signature = joinPoint.getSignature();
        if (Objects.isNull(signature)) {
            return;
        }
        String[] parameterNames = ((CodeSignature) signature).getParameterNames();
        if (Objects.isNull(parameterNames) || parameterNames.length != parameterValuesLength) {
            return;
        }
        Map<String, Object> params = new HashMap<>(4);
        for (int i = 0; i < parameterNames.length; i++) {
            params.put(parameterNames[i], parameterValues[i]);
        }

        try {
            String string = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
            log.debug("[请求参数] - {}", params);
            //log.debug("[请求参数] - {}", JSON.toJSONString(params, SerializerFeature.WriteMapNullValue));


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @AfterReturning(returning = "result", pointcut = "requestMapping()")
    public void afterRequestLogging(Object result) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            return;
        }
        HttpServletResponse response = servletRequestAttributes.getResponse();
        if (Objects.isNull(response)) {
            return;
        }
        int status = response.getStatus();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        log.debug("[{}][响应结果] - {} : {} : {}",Thread.currentThread().getName(), status, method, uri);
        //  if (log.isDebugEnabled()) {
        try {
            // TODO： 这里日志序列化有时会出错，影响正常业务输出，正常解决方案： 不将 entity 暴露给 controller 做为返回参数entity 暴露给 controller 做为返回参数
            String string = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            log.debug("[响应内容] - {}", string);
        } catch (Exception e) {

        }
        MDC.clear();
        //  }
    }
}
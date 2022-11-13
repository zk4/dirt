package com.zk.conf.wrapper.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.dirt.wrapper.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class AutoWrapperResultAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // response是ResponseEntity,Result 类型，或者注释了  DoNotWrapperResult 都不进行包装
        return !methodParameter.getParameterType().isAssignableFrom(ResponseEntity.class)
        && !methodParameter.getParameterType().isAssignableFrom(Result.class)
                &&  !methodParameter.hasMethodAnnotation(DoNotWrapperResult.class);

    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        // 设置返回类型为 json
        response.getHeaders().add("content-type", "application/json");

        // String类型不能直接包装
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();

            // 将数据包装在ResultVo里后转换为json串进行返回
            try {
                return objectMapper.writeValueAsString(Result.success(data));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


        }
        // 否则直接包装成ResultVo返回
        return Result.success(data);
    }


}

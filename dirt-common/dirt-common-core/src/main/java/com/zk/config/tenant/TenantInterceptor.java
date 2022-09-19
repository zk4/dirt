package com.zk.config.tenant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

// 帮助：
// https://callistaenterprise.se/blogg/teknik/2020/09/20/multi-tenancy-with-spring-boot-part2/
@Component
@Slf4j
public class TenantInterceptor implements WebRequestInterceptor {

    @Override
    public void preHandle(WebRequest request) throws Exception {
        String tenantId = null;
        if (request.getHeader("X-TENANT-ID") != null) {
            tenantId = request.getHeader("X-TENANT-ID");
        } else {
            tenantId = ((ServletWebRequest)request).getRequest().getServerName().split("\\.")[0];
            //log.debug("无 tenant");
        }

        // TODO，逻辑上还是比较复杂:
        //  1. 针对系统管理员，不检查租户，这个通过独立接口做， 不复用相关数据接口
        //  2. 针对运营人员
        //  2.1. 带权限接口，应该从 token 里拿到租户信息
        //  2.2  不带权限接口， 在接口参数里指定租户
        //  3. 针对普通用户，在接口参数里指定租户

        TenantContext.setTenantId(tenantId);
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        TenantContext.clear();
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
    }

}
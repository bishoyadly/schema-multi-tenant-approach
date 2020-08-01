package com.example.demo.config;

import com.example.demo.entities.Tenant;
import com.example.demo.exceptions.ApiException;
import com.example.demo.repositories.TenantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;

import static com.example.demo.config.TenantSchemaResolver.DEFAULT_SCHEMA;

@Component
public class TenantInterceptor implements HandlerInterceptor {
    private TenantRepository tenantRepository;

    TenantInterceptor(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    private String getTenantIdFromRequestUri(HttpServletRequest request) {
        Object object = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        final HashMap<String, String> pathVariables;
        String tenantId = "";
        if (Objects.nonNull(object) && object instanceof HashMap) {
            pathVariables = (HashMap<String, String>) object;
            tenantId = pathVariables.get("tenantId");
        }
        return tenantId;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantId = getTenantIdFromRequestUri(request);
        if (Objects.nonNull(tenantId)) {
            Optional<Tenant> optionalTenant = tenantRepository.findBySchemaName(tenantId);
            if (optionalTenant.isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND, "Tenant not found");
            } else {
                TenantContext.setTenantSchema(tenantId);
            }
        }
        TenantContext.setTenantSchema(tenantId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.setTenantSchema(DEFAULT_SCHEMA);
    }
}

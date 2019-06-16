package com.example.orm.filter;

import com.example.orm.model.Tenant;
import com.example.orm.tenancy.TenantAttributes;
import com.example.orm.tenancy.TenantHolder;
import com.example.orm.tenancy.TenantService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
public class TenantFilter implements Filter {

    private static final Log logger = LogFactory.getLog(TenantFilter.class);

    private static final String X_TENANT_ID = "x-tenant-id";

    @Autowired
    private TenantService tenantService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //get the header
        String tenantId = ((HttpServletRequest) servletRequest).getHeader(X_TENANT_ID);
        if (tenantId == null) {
            throw new ServletException("No x-tenant-id header set");
        }//end if
        //setup
        TenantAttributes tenantAttributes = tenantService.getTenantById(tenantId);
        TenantHolder.setTenantContext(tenantAttributes.getId(),tenantAttributes.getKey(),tenantAttributes.getIv());
        //continue
        filterChain.doFilter(servletRequest,servletResponse);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }


    @Override
    public void destroy() { }
}

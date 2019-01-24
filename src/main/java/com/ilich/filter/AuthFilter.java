package com.ilich.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ilich.filter.UserFromCookies.getUserId;


@WebFilter
public class AuthFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if (requestURI.equals("/signup") || requestURI.equals("/signin")) {
            chain.doFilter(request, response);
        } else {
            String currentUser = getUserId(((HttpServletRequest) request).getCookies());
            if (currentUser.equals("")) {
                ((HttpServletResponse) response).sendRedirect("/signin");
            } else {
                AuthenticationData.setUserId(currentUser);
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
        AuthenticationData.setUserId("");
    }
}

package com.ssamz.jblogweb.config;

import com.ssamz.jblogweb.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticateInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            response.sendRedirect("/auth/login");
        }

        return true;
    }
}

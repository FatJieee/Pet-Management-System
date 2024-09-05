package com.jieee.interceptor;

import com.jieee.utils.JwtUtils;
import com.jieee.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString();
        log.info("Request URL: {}", url);

        // Attempt to parse JWT on every request
        String jwt = request.getHeader("Authorization");
        if (StringUtils.hasLength(jwt)) {
            try {
                Map<String, Object> claims = JwtUtils.parseJWT(jwt);
                ThreadLocalUtil.set(claims); // Store claims for further use anywhere in the application
            } catch (Exception e) {
                log.error("Failed to parse JWT", e);
                // Optionally handle the error specifically if you need to do something when JWT is invalid
            }
        }

        // Additional checks for specific paths
        if (url.contains("cart")) {
            if (!StringUtils.hasLength(jwt)) {
                log.info("Authorization is empty on a protected path");
                denyAccess(response, "NOT_LOGIN");
                return false;
            }

            // Since we set claims earlier, check if claims are correctly set
            if (ThreadLocalUtil.get() == null) {
                log.info("JWT parsing failed or JWT was invalid for a protected path");
                denyAccess(response, "NOT_LOGIN");
                return false;
            }

            log.info("JWT is valid for a protected path");
        }

        return true;
    }

    private void denyAccess(HttpServletResponse response, String message) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //401
    }
}

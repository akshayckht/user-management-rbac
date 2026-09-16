package org.example.rbacminiproject.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class RoleBasedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request the request that caused the successful authentication
     * @param response the response
     * @param authentication the <tt>Authentication</tt> object which was created during the
     *     authentication process.
     */
    @Override
    public void onAuthenticationSuccess(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        log.info("Success Handler called");
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> Objects.equals(authority.getAuthority(), "ROLE_ADMIN"))) {

            log.info("is admin");
            response.sendRedirect("/admin/dashboard");
            return;
        }

        log.info("not admin");

        response.sendRedirect("/dashboard");
    }
}

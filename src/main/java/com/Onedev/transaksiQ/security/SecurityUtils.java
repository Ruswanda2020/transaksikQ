package com.Onedev.transaksiQ.security;

import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
    public static String getCurrentUserEmail() {
        Object principal = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        }
        throw new RuntimeException("Unable to retrieve user email from SecurityContext");
    }
}

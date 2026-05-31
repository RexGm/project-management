package com.codespells.pm.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

/**
 * SecurityContext'ten oturum acmis kullanici bilgisine erisim.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /** JWT subject olarak saklanan kullanici id'si. */
    public static UUID currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new IllegalStateException("Kimlik dogrulanmamis istek");
        }
        return UUID.fromString(auth.getName());
    }
}

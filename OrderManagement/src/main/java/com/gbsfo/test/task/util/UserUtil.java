package com.gbsfo.test.task.util;

import com.gbsfo.test.task.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public static User getCurrentUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}

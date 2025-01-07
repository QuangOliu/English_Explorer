package com.ptit.EnglishExplorer.utils;

import com.ptit.EnglishExplorer.data.entity.Role;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;

public class RoleUtils {

    // Kiểm tra xem user có vai trò cụ thể không
    public static boolean hasRole(User user, String roleName) {
        if (user == null || roleName == null || roleName.isEmpty()) {
            return false;
        }
        return user.getRoles().stream()
                .map(Role::getName)
                .anyMatch(roleName::equalsIgnoreCase);
    }

    // Kiểm tra xem user có phải là ADMIN hay không
    public static boolean isAdmin(User user) {
        return hasRole(user, "ADMIN");
    }

    // Kiểm tra xem user có phải là TEACHER hay không
    public static boolean isTeacher(User user) {
        return hasRole(user, "TEACHER");
    }

    // Kiểm tra xem user có phải là USER hay không
    public static boolean isUser(User user) {
        return hasRole(user, "USER");
    }

    public static boolean isOwn(User user, AuditableEntity entity) {
        return user.getUsername().equals(entity.getCreatedBy());
    }

    // Kiểm tra xem user có ít nhất một trong các vai trò được chỉ định
    public static boolean hasAnyRole(User user, String... roleNames) {
        if (user == null || roleNames == null || roleNames.length == 0) {
            return false;
        }
        return user.getRoles().stream()
                .map(Role::getName)
                .anyMatch(role -> {
                    for (String roleName : roleNames) {
                        if (roleName.equalsIgnoreCase(role)) {
                            return true;
                        }
                    }
                    return false;
                });
    }
}

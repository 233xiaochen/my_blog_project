package com.example.blog.utils;

import com.example.blog.service.impl.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author： chenlin
 * @create： 2024/4/9 14:34
 */

public class UserUtils {

    public static LoginUser getCurUserAccount(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return (LoginUser) authentication.getPrincipal();
    }

}

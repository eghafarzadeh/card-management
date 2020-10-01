package com.samples.paymentservice.util;

import com.samples.paymentservice.service.Context;
import com.samples.paymentservice.service.dto.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * @author Elham
 * @since 9/30/2020
 */
public class ContextUtil {
    public static Context getContext() {
        Context context = new Context();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDto) {
            context.setUserId(((UserDto) principal).getUserId());
        } else if (principal instanceof UserDetails) {
            context.setUsername(((UserDetails) principal).getUsername());
        } else {
            context.setUsername(principal.toString());
        }
        return context;
    }
}

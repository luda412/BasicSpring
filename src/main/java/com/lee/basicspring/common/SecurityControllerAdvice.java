package com.lee.basicspring.common;

import com.lee.basicspring.controller.SecurityController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = SecurityController.class)
public class SecurityControllerAdvice {
    @ModelAttribute
    public void setSecurityAttributes(Model model){
        model.addAttribute("loginType", "security-login");
        model.addAttribute("pageName", "security-로그인");
    }
}

package com.lee.basicspring.common;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.lee.basicspring.controller.SessionController;

@ControllerAdvice(assignableTypes = SessionController.class)
public class SessionControllerAdvice {
    
    @ModelAttribute
    public void setSessionAttributes(Model model){
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");
    }
}

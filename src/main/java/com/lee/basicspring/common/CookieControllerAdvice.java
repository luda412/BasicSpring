package com.lee.basicspring.common;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.lee.basicspring.controller.CookieController;


@ControllerAdvice(assignableTypes = CookieController.class)
public class CookieControllerAdvice {
    
    //공통적인 model 값 분리
    @ModelAttribute
    public void setCookieAttributes(Model model){
        model.addAttribute("loginType", "cookie-login");
        model.addAttribute("pageName", "쿠키 로그인");
    }
}

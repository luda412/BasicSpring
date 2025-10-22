package com.lee.basicspring.common;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class ControllerHelper {
    
    //공통적인 model 값 분리
    @ModelAttribute
    public void setCookieAttributes(Model model){
        model.addAttribute("loginType", "cookie-login");
        model.addAttribute("pageName", "쿠키 로그인");
    }

    @ModelAttribute
    public void setSessionAttributes(Model model){
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");
    }
}

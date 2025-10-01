package com.lee.basicspring.service;

import com.lee.basicspring.controller.dto.JoinRequest;

public interface MemberService {
    String join(JoinRequest joinRequest);
}

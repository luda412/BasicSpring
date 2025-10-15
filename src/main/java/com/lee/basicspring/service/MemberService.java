package com.lee.basicspring.service;

import com.lee.basicspring.data.dto.JoinRequest;

public interface MemberService {
    boolean checkLoginIdDuplicate (String LoginId);

    boolean checkNicknameDuplicate (String nickname);

    void join(JoinRequest req);

}

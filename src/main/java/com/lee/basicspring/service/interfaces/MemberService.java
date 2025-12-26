package com.lee.basicspring.service.interfaces;

import com.lee.basicspring.data.dto.JoinRequest;
import com.lee.basicspring.data.dto.LoginRequest;
import com.lee.basicspring.data.entity.Member;

public interface MemberService {
    boolean checkLoginIdDuplicate (String LoginId);

    boolean checkNicknameDuplicate (String nickname);

    void join(JoinRequest req);

    void joinEncoder(JoinRequest req);

    Member login(LoginRequest req);

    Member getLoginMemberById(Long MemberId);

    Member getLoginMemberByLoginId(String LoginId);
}

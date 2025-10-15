package com.lee.basicspring.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lee.basicspring.data.dto.JoinRequest;
import com.lee.basicspring.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceimpl implements MemberService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    /* When processing Join, check duplication LoginId wrote by User
     * DataBase Access, Consider readOnly option | If already exist loginId, return result is true
     */
    @Override
    public boolean checkLoginIdDuplicate(String LoginId) {
        return memberRepository.existsByLoginId(LoginId);   
    }

    /* When processing Join, check duplication Nickname wrote by User
    * DataBase Access, Consider readOnly option | If already exist nickname, return result is true
    */
    @Override
    public boolean checkNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }


    /* When processing Join, save entity object to Database
     * DataBase Access, Consider transactional option | have to save encoded passwords
     */
    @Override
    public void join(JoinRequest req) {
        memberRepository.save(req.toEntity(encoder.encode(req.getPassword())));
    }

}

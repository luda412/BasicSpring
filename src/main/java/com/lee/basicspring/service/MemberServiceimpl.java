package com.lee.basicspring.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.lee.basicspring.service.interfaces.MemberService;
import com.lee.basicspring.data.dto.JoinRequest;
import com.lee.basicspring.data.dto.LoginRequest;
import com.lee.basicspring.data.entity.Member;
import com.lee.basicspring.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

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
     * DataBase Access, Consider transactional option
     */
    @Override
    public void join(JoinRequest req) {
        // req.set
        memberRepository.save(req.toEntity());
    }

    /* When processing Join, save entity object to Database
     * DataBase Access, Consider transactional option | have to save encoded passwords
     */
    @Override
    public void joinEncoder(JoinRequest req) {
        memberRepository.save(req.toEntity(encoder.encode(req.getPassword())));
    }

    /* 
     * member 가 없어도 null, 비밀번호가 같지 않아도 null 이니까 넘기는 controller로 넘기는 값이 null로 같으면 안됨.
     */
    @Override
    public Member login(LoginRequest req) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(req.getLoginId());
        
        if(optionalMember.isEmpty()){
            return null;
            // consider about instead of null
        }

        Member member = optionalMember.get();

        if(!encoder.matches(req.getPassword(), member.getPassword())){
            return null;
            // consider about instead of null
        }
        return member;
    }


    /* 
     * 
     */
    @Override
    public Member getLoginMemberById(Long memberId) {
        if(memberId == null) return null;

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isEmpty()) return null;

        return optionalMember.get();
    }

    /* 
     * 
     */
    @Override
    public Member getLoginMemberByLoginId(String loginId) {
        if(loginId == null) return null;

        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        if(optionalMember.isEmpty()) return null;
        return optionalMember.get();
    }

}

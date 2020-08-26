package com.kh.spring.member.model.service;

import java.util.HashMap;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {

	Member memberLogin(Member m);

	int insertMember(Member m);

	int updateMember(Member m);

	int updatePwdMember(HashMap<String, String> hm);

	int deleteMember(String id);

	int idCheck(String id);

}

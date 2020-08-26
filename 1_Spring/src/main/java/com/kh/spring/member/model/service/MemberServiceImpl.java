package com.kh.spring.member.model.service;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.vo.Member;

@Service("mService") // 서비스 빈을 등록해줘야함, 이름은 생략가능
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public Member memberLogin(Member m) {
		return mDAO.memberLogin(sqlSession, m);
	}
	
	@Override
	public int insertMember(Member m) {
		return mDAO.insertMember(sqlSession, m);
	}
	
	@Override
	public int updateMember(Member m) {
		return mDAO.updateMember(sqlSession, m);
	}
	
	@Override
	public int updatePwdMember(HashMap<String, String> hm) {
		return mDAO.updatePwdMember(sqlSession, hm);
	}
	
	@Override
	public int deleteMember(String id) {
		return mDAO.deleteMember(sqlSession, id);
	}
	
	@Override
	public int idCheck(String id) {
		return mDAO.idCheck(sqlSession, id);
	}
}

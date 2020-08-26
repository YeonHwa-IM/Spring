package com.kh.spring.member.model.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository("mDAO")
public class MemberDAO {

	public Member memberLogin(SqlSessionTemplate sqlSession, Member m) {
		
		return (Member)sqlSession.selectOne("memberMapper.memberLogin", m);
	}

	public int insertMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.insert("memberMapper.insertMember", m);
	}

	public int updateMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.updateMember", m);
	}

	public int updatePwdMember(SqlSessionTemplate sqlSession, HashMap<String, String> hm) {
		return sqlSession.update("memberMapper.updatePwdMember", hm);
	}

	public int deleteMember(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.update("memberMapper.deleteMember", id);
	}

	public int idCheck(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("memberMapper.idCheckMember", id);
	}

}

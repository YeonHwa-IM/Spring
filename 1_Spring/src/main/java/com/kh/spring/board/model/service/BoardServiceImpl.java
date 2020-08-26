package com.kh.spring.board.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDAO;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.board.model.vo.Reply;

@Service("bService") //16
public class BoardServiceImpl implements BoardService {//15
	
	// public interface BoardService 보드서비스로 인터페이스를 먼저 만들고
	//BoardServiceImpl 이름으로 BoardService를 implements(구현하다)BoardService 해서
	// 인터페이스인 보드서비스를 임플리먼트 하겠다. 보드서비스임플 에서.
	
//	인터페이스는 추상클래스와는 달리 클래스가 아니므로 자식 클래스가 상속을 받을때 implements라는 키워드를 쓴다. 
//	예제에서 인터페이스 자식 클래스에서 추상메소드를 구현할때 접근지정자를 public 모드로 썼는데 이는 implements라는 키워드에서 느낄수 있듯이 
//	어떤 상황에서든 해당 멤버들을 반드시 구현해야하므로 관련 메소드에 아무런 제약없이 접근할수 있어야하기 때문이다. 
//	또한 인터페이스는 추상클래스나 일반클래스와는 달리 다중상속이 가능하다. 여러개의 인터페이스를 아래처럼 한꺼번에 상속받을수 있는데 
//	콤마(,)찍고 계속해서 원하는만큼 상속받을 인터페이스 이름을 적어주고 상속받아서 해당멤버들을 인터페이스 자식클래스에서 구현하면 된다.
//	class Triangle implements Shape, 인터페이스이름, 인터페이스이름
	
//	인터페이스는 디자인을 구성하는 요소들이 자주 바뀔때 쓰면 유용하고 당연하지만 메소드 형태만 서로 공유해서 구현되는 상황일때 적합하고 
//	클래스 전체가 아닌 메소드들만 쓰고 싶을때 인터페이스를 이용하면 효과적이다. 좀더 깊이 들어가자면 인터페이스의 사용빈도가 상당히 높은데 
//	그 이유중 하나가 동시개발이 가능하므로 시간을 많이 단축시킬수 있다는 점이다. 인터페이스안의 메소드들은 내용이 없는 상태이나 
//	메소드에 대한 결과값은 내용을 만들지 않아도 미리 알수 있으므로 인터페이스의 내용을 누군가가 구현하고 있는 동안에 
//	다른 한사람은 그 메소드가 구현되고 나면 작동할 결과값으로 같은 시간에 다른 일을 할수 있으니 개발작업이 한층더 빨라질수 있는 것이다. 
//	또한 여러사람이 인터페이스를 통해 그런 작업을 동시에 한다고 가정했을때 인터페이스안의 메소드 내용을 변경하더라도 
//	그와 관련된 모든 클래스들을 변경할 필요없이 해당 메소드의 구현되는 내용만 변경하면 
//	모든 처리가 가능해지므로 일거양득(一擧兩得)이라 할수 있겠다.^^
	
	
	@Autowired
	private BoardDAO bDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override //17
	public int getListCount() {
		return bDAO.getListCount(sqlSession);
	}
	
	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		return bDAO.selectList(sqlSession, pi);
	}
	
	@Override
		public int insertBoard(Board b) {
			return bDAO.insertBoard(sqlSession, b);
		}
	
	@Override
	public Board selectBoard(int bId) {
		
		//조회수 증가 체크하기
		Board b = null;
		
		int result = bDAO.updateCount(sqlSession, bId);
		
		if(result > 0) {
			b = bDAO.selectBoard(sqlSession, bId);
		}
		return b;
	}
	
	@Override
	public Board selectUpdateBoard(int bId) {
		return bDAO.selectBoard(sqlSession, bId);
	}
	
	@Override
	public int updateBoard(Board b) {
		return bDAO.updateBoard(sqlSession, b);
	}
	
	@Override
	public int deleteBoard(int bId) {
		return bDAO.deleteBoard(sqlSession, bId);
	}
	
	@Override
	public ArrayList<Reply> replyList(int bId) {
		return bDAO.replyList(sqlSession, bId);
	}
	
	@Override
	public int insertReply(Reply r) {
		return bDAO.insertReply(sqlSession, r);
	}
	@Override
	public ArrayList<Board> selectTopList() {
		return bDAO.selectTopList(sqlSession);
	}
}

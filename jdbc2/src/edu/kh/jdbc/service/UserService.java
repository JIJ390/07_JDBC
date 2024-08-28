package edu.kh.jdbc.service;

// import static : 지정된 경로에 존재하는 static 구문을 모두 얻어와
// 클래스명.메서드명() 이 아닌 메서드명() 만 작성해도 호출 가능하게 함
import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.UserDao;
import edu.kh.jdbc.dto.User;

// Service : 비즈니스 로직 처리 
// - DB 에 CRUD(Create, Read, Update, Delete 후 결과 반환 받기
//	 + DML 성공 여부에 따른 트랜잭션 제어 처리(commit/rollback)
//     --> commit / rollback 에는 Connection 객체가 필요하기 때문에
//		   Connection 객체를 Service 에서 생성 후
//		   Dao 에 전달하는 형식의 코드를 작성하게 됨
public class UserService {
	// 필드
	private UserDao dao = new UserDao();
	
	// 메서드
	
	/**
	 * 전달 받은 아이디와 일치하는 User 정보 반환
	 * @param  input : 입력된 아이디
	 * @return 아이디가 일치하는 회원 정보, 없으면 null 
	 */
	public User selectId(String input) {
		
		// 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// Dao 메서드 호출 후 결과 반환 받기
		User user = dao.selectId(conn, input);
		
		// 다 쓴 커넥션 닫기
		JDBCTemplate.close(conn);

		return user; // DB 조회 결과 반환
	}

	/**
	 * User 등록 서비스
	 * @param user
	 * @return 삽입 성공한 결과 행의 개수
	 * @throws Exception 
	 */
	public int insertUser(User user) throws Exception {
		// 1. Connection 생성
		Connection conn = getConnection();	
		// impoert static 으로 JDBCTemplate생략
		
		// 2. 데이터 가공(할게 없으면 pass)
		
		// 3. DAO 메서드(INSERT 수행) 호출 후 
		// 결과(삽입 성공한 행의 개수, int) 반환 받기
		int result = dao.insertUser(conn, user);
		
		// 4. INSERT 수행 결과에 따라 트랜잭션 제어 처리
		if (result > 0) commit(conn);
		else            rollback(conn);
		
		
		// 5. Connection 반환하기
		close(conn);
		
		
		// 6. 결과 반환
		return result;
	}

	
	/**
	 * User 전체 조회
	 * @return userList : 조회된 User 가 담긴 List
	 * @throws Exception
	 */
	public List<User> selectAll() throws Exception {
		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. 데이터 가공(필요 없으면 pass)
		
		// 3. Dao 메서드(SELECT) 호출 후 결과(List<User>) 반환 받기
		List<User> userList = dao.selectAll(conn);

		// 4. DML 인 경우 트랜잭션 처리
		//	  SELECT는 필요 X
		
		// 5. Connection 반환
		close(conn);
		
		// 6. 결과 반환
		return userList;
	}

	
	/**
	 * 이름에 검색어가 들어가는 user 모두 조회
	 * @param keyword
	 * @return searchList
	 * @throws Exception
	 */
	public List<User> selectName(String keyword) throws Exception {
		
		// DB 연결 정보를 담고 있는 객체
		Connection conn = getConnection();
		
		List<User> searchList = dao.selectName(conn, keyword);
		
		close(conn);
		
		return searchList;
	}

	/**
	 * 번호로 user 정보 조회
	 * @param numero
	 * @return user
	 * @throws Exception
	 */
	public User selectNo(int numero) throws Exception {
		Connection conn = getConnection();
		
		User user = dao.selectNo(conn, numero);
		
		close(conn);
		
		return user;
	}

	
	/**
	 * 번호로 user 정보 삭제
	 * @param numero
	 * @return result
	 * @throws Exception
	 */
	public int deleteUser(int numero) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.deleteUser(conn, numero);
		
		if (result > 0) commit(conn);
		else            rollback(conn);
		
		close(conn);
		
		return result;
	}

	/**
	 * ID, PW 일치하는 user 조회
	 * @param user
	 * @return userNo
	 * @throws Exception
	 */
	public int selectUser(User user) throws Exception {
		Connection conn = getConnection();
		
		int userNo = dao.selectUser(conn, user);
		
		close(conn);
		
		return userNo;
	}
	
	
	/**
	 * user 이름 수정
	 * @param user
	 * @return result
	 * @throws Exception
	 */
	public int updateUser(User user) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.udpateUser(conn, user);
		
		if (result > 0) commit(conn);
		else            rollback(conn);
		
		close(conn);
		
		return result;
	}

	
	/**
	 * ID 중복 확인
	 * @param userId
	 * @return count
	 * @throws Exception
	 */
	public int dupliCheck(String userId) throws Exception {
		Connection conn = getConnection();
		
		int count = dao.dupliCheck(conn, userId);
		
		close(conn);
		
		return count;
	}

	/**
	 * userList 에 있는 모든 user INSERT 하기
	 * @param userList
	 * @return count : 삽입된 행의 개수
	 * @throws Exception
	 */
	public int multiInsert(List<User> userList) throws Exception {
		Connection conn = getConnection();
		
		// 다중 insert 방법
		// 1) SQL 을 이용한 다중 INSERT		  (어려움)
		// 2) Java 반복문을 이용한 다중 INSERT (속도 느림)
		
		int count = 0;	// 삽입 성공한 행의 개수
		
		for (User user : userList) {
			count += dao.insertUser(conn, user);
		}
		
		// 전체 삽입 성공 시 commit / 아니면 rollback
		if (count == userList.size()) commit(conn);
		else                          rollback(conn);
		
		close(conn);
		
		return count;
	}

	



	

	

}






























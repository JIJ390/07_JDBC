package edu.kh.jdbc.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;

// DAO (Data Access Object) : 데이터가 저장된 곳에 접근하는 용도의 객체
// -> DB 에 접근하여 Java 에서 원하는 결과를 얻기 위해
//	  SQL 을 수행하고 결과 반환
public class UserDao {

	// 필드
	// - DB 접근 관현한 JDBC 객체 참조형 변수를 미리 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/** 전달받은 Connection 을 이용해 DB 에 접근하여
	 * 전달받은 아이디와 일치하는 User 정보 조회하기
	 * @param conn  : Service 에서 생성한 Connection 객체
	 * @param input : View 에서 입력 받은 아이디
	 * @return
	 */
	public User selectId(Connection conn, String input) {
		
		User user = null; // 결과 저장용 변수
		
		try {
			// SQL 작성
			String sql = """
					SELECT *
					FROM TB_USER
					WHERE USER_ID = ?
					""";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ?(placeholder)에 알맞은 값 대입
			pstmt.setString(1, input);
			
			// SQL 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우
			// -> 중복되는 아이디가 없다고 가정
			//    1 행만 조회 되기 때문에 while 보단 if 를 사용하는게 효과적
			
			if (rs.next()) {
				
				// 각 컬럼의 값 얻어오기
				int userNo      = rs.getInt("USER_NO");
				String userId   = rs.getString("USER_ID");
				String userPw   = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				
				// java.sql.date 활용
				Date enrollDate = rs.getDate("ENROLL_DATE"); 
				
				// 조회된 컬럼값을 이용해 User 객체 생성
				user = new User(
						userNo,
						userId,
						userPw,
						userName,
						enrollDate.toString());	// date 타입을 string 으로
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 사용한 객체 반환
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			// Connection 객체는 Service 에서 close!!!!
		}
		
		return user; // 결과 반환(생성된 User / null(조회 결과 없을 때)
		
	}

	
	/**
	 * User 등록 DAO 메서드
	 * @param conn : DB 연결 정보가 담겨있는 객체
	 * @param user : 입력 받은 id, pw, name
	 * @return result : INSERT 결과 행의 개수
	 * @throws Exception : 발생하는 예외 모두 던짐
	 */
	public int insertUser(Connection conn, User user) throws Exception{
		// sql 수행 중 발생하는 예외 전부 UserView.mainMenu 메서드에서 처리
		
		// SQL 수행 중 발생하는 예외를
		// catch로 처리하지 않고, throws를 이용해서 호출부로 던져 처리
		// -> catch문이 필요 없다!!
		
		
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		// catch 문 작성 필요 없어짐
		try {
			// 2. sql 문 작성
			String sql = """
					INSERT INTO TB_USER  
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					""";
			
			// 3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ? (placeholder) 알맞은 값 대입
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			
			// 5. SQL(INSERT) 수행(executeUpdate) 후 결과 반환 받기
			result = pstmt.executeUpdate();
			
		} finally {
			// 6. 사용한 JDBC 객체 자원 반환(close)
			close(pstmt);
		}
		
		// 7. 결과 저장용 변수에 저장된 값을 반환
		return result;
	}


	/**
	 * User 전체 조회 DAO 메서드
	 * @param conn
	 * @return userList
	 * @throws Exception
	 */
	public List<User> selectAll(Connection conn) throws Exception{
		// 1. 결과 저장용 변수 선언
		// -> List 같은 컬렉션을 반환하는 경우에는
		//    변수 선언 시 객체도 같이 생성해두자!!!!
		
		List<User> userList = new ArrayList<User>();
		
		try {
			// 2. SQL 작성
			String sql = """
SELECT 
	USER_NO,
	USER_ID,
	USER_PW,
	USER_NAME,
	TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
FROM TB_USER
ORDER BY USER_NO ASC
					""";
			
			// 3. prepareStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ? 대입 생략
			
			// 5. SQL(SELECT) 수행(executeQuery()) 후 결과(RESULTSET) 반환
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과(ResultSet)를
			//  커서를 이용해서 1행씩 접근하여 컬럼값 얻어오기
			
			/* 몇 행이 조회될지 모른다 -> while
			 * 무조건 1행이 조회 된다  -> if
			 */
			
			// rs.next() : 커서를 1행 이동 이동된 행에 데이터 유무에 따라 true/false 반환
			while (rs.next()) {
				int    userNo     = rs.getInt("USER_NO");
				String userId     = rs.getString("USER_ID");
				String userPw     = rs.getString("USER_PW");
				String userName   = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				// - java.sql.Date 타입으로 값을 저장하지 않는 이유!
				//  -> TO_CHAR()를 이용해서 문자열로 변환했기 때문!
				
				
				// 조회된 값을 userList 에 추가
				// -> User 객체를 생성해 조회된 값을 담고 userList 에 추가하기
				User user = new User(userNo, userId, userPw, userName, enrollDate);
				userList.add(user); 
				
				// ResultSet 을 List 에 옮겨 담는 이유
				// 1. List 가 사용이 편해서
				//  -> 호환되는 곳도 많음(jsp, thymeleaf 등)
				// 2. 사용된 rs 가 DAO 에서 close 되기 때문
			}
			
		} finally {
			// 7. 사용한 JDBC 객체 자원 반환(close)
			close(rs);
			close(pstmt);
		}
		
		
		// 8. 조회 결과가 담긴 List 반환
		return userList;
	}


	/**
	 * 이름 검색어 조회 DAO 메서드
	 * @param conn
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<User> selectName(Connection conn, String keyword) throws Exception {
		List<User> searchList = new ArrayList<User>();
		
		try {
			String sql = """
SELECT 
	USER_NO,
	USER_ID,
	USER_PW,
	USER_NAME,
	TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
FROM TB_USER
WHERE USER_NAME LIKE '%' || ? || '%'
ORDER BY USER_NO ASC			
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int    userNo     = rs.getInt("USER_NO");
				String userId     = rs.getString("USER_ID");
				String userPw     = rs.getString("USER_PW");
				String userName   = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				User user = new User(userNo, userId, userPw, userName, enrollDate);
				searchList.add(user); 
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return searchList;
	}


	/**
	 * 번호 조회 DAO 메서드
	 * @param conn
	 * @param numero
	 * @return user
	 * @throws Exception
	 */
	public User selectNo(Connection conn, int numero) throws Exception {
		
		User user = null;
		// 컬렉션이 아니기 때문에 null 로 해도 무방
		
		try {
			String sql = """
SELECT 
	USER_NO,
	USER_ID,
	USER_PW,
	USER_NAME,
	TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
FROM TB_USER
WHERE USER_NO = ?				
					""";// 1 행 조회, 정렬 구문(ORDER BY) 필요 없음
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, numero);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int    userNo     = rs.getInt("USER_NO");
				String userId     = rs.getString("USER_ID");
				String userPw     = rs.getString("USER_PW");
				String userName   = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				user = new User(userNo, userId, userPw, userName, enrollDate);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return user;
	}


	/**
	 * 번호로 User 삭제 DAO 메서드
	 * @param conn
	 * @param numero
	 * @return result
	 * @throws Exception
	 */
	public int deleteUser(Connection conn, int numero) throws Exception {
		int result = 0;
		
		try {
			String sql = """
DELETE 
FROM TB_USER 
WHERE USER_NO = ?
					""";
			
			// java에서 전달할 값들을 저장할 준비가 돼있는 statement
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, numero);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	
	/**
	 * ID, PW 일치 user 조회 DAD 메서드
	 * @param conn
	 * @param user
	 * @return userNo
	 * @throws Exception
	 */
	public int selectUser(Connection conn, User user) throws Exception{
		int userNo = 0;
		
		try {
			String sql = """
SELECT USER_NO
FROM TB_USER
WHERE USER_ID = ?
AND   USER_PW = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			
			rs = pstmt.executeQuery();
			
			// 조회 결과가 1행이라 가정(중복 id 없음)
			// 조회 결과 없을 시 userNo 는 0이 반환됨
			if (rs.next()) userNo = rs.getInt("USER_NO");
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return userNo;
	}
	
	

	/**
	 * user 이름 수정 DAO 메서드
	 * @param conn
	 * @param user
	 * @return result
	 * @throws Exception
	 */
	public int udpateUser(Connection conn, User user) throws Exception{
		int result = 0;
		
		try {
			String sql = """
UPDATE TB_USER
SET USER_NAME = ?
WHERE USER_NO = ?			
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getUserName());
			pstmt.setInt(2, user.getUserNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/**
	 * ID 중복 확인 DAO 메서드
	 * @param conn
	 * @param userId
	 * @return count
	 * @throws Exception
	 */
	public int dupliCheck(Connection conn, String userId) throws Exception {
		int count = 0;
		
		// 중복되는 아이디가 있는지 조회
		// 중복이면 1, 아니면 0
		// 처음엔 userNo으로 했으나 필요없는 변수(의미가 명확하지 않음)
		// 그런 변수를 가져온다는 느낌이 듬
		// count 가 수를 센다, 있다 없다를 판별하기 적절해 보임!!!
		try {
			String sql = """
SELECT COUNT(*) COUNT
FROM TB_USER
WHERE USER_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
//			if (rs.next()) count = rs.getInt("COUNT");
			if (rs.next()) count = rs.getInt(1);	// 조회된 컬럼 순서(첫번째) 로 값 얻어오기
													// 변경 가능성 있어 추천되지 않으나
													// 이 경우 컬럼이 하나뿐이라 쓰는 것이 권장
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return count;
	}


}



















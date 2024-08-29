package edu.kh.jdbc.dao;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.dto.User;

public interface UserDao {

	/**
	 * 사용자 등록 DAO 메서드
	 * @param conn
	 * @param user
	 * @return result 1 || 0
	 * @throws Exception
	 */
	int insertUser(Connection conn, User user) throws Exception;

	
	/**
	 * 아이디 중복 여부 DAO 메서드
	 * @param conn
	 * @param userId
	 * @return result (1 : 중복, 0 : 중복 X)
	 * @throws Exception
	 */
	int idCheck(Connection conn, String userId) throws Exception;


	/**
	 * 로그인 DAO 메서드
	 * @param conn
	 * @param userId
	 * @param userPw
	 * @return loginUser
	 * @throws Exception
	 */
	User login(Connection conn, String userId, String userPw) throws Exception;


	/**
	 * 모든 user 조회
	 * @param conn
	 * @return userList
	 * @throws Exception
	 */
	List<User> selectAll(Connection conn) throws Exception;


	List<User> selectUser(Connection conn, String searchName) throws Exception;




}

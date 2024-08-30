package edu.kh.jdbc.service;

import java.util.List;

import edu.kh.jdbc.dto.User;

public interface UserService {
	
	/**
	 * 사용자 등록
	 * @param user
	 * @return result : 1 || 0
	 * @throws Exception
	 */
	int insertUser(User user) throws Exception;	// 추상 메서드

	
	/** 
	 * 아이디 중복 여부 확인
	 * @param userId
	 * @return result (1 : 중복, 0 : 중복 X)
	 * @throws Exception
	 */
	int idCheck(String userId) throws Exception;


	/**
	 * 로그인 (내 정보를 가져오다??)
	 * @param userId
	 * @param userPw
	 * @return loginUser
	 * @throws Exception
	 */
	User login(String userId, String userPw) throws Exception;

	/**
	 * 모든 사용자 조회
	 * @return searchList : user 정보 담은 List
	 * @throws Exception
	 */
	List<User> selectAll() throws Exception;


	/**
	 * 검색어가 이름에 포함된 사용자 조회
	 * @param searchName
	 * @return searchList : 검색어가 포함된 user 정보 담은 List
	 * @throws Exception
	 */
	List<User> selectName(String searchName) throws Exception;


	/**
	 * userNo 가 일치하는 user 정보 조회
	 * @param userNo
	 * @return user
	 * @throws Exception
	 */
	User selectUser(int userNo) throws Exception;


	/**
	 * userNo 가 일치하는 user 정보 삭제
	 * @param userNo
	 * @return result : 0 || 1 (실패 / 성공)
	 * @throws Exception
	 */
	int deleteUser(int userNo) throws Exception;

	
	/**
	 * userNo 가 일치하는 user 정보 수정
	 * @param user
	 * @return result : 0 || 1 (실패 / 성공)
	 * @throws Exception
	 */
	int updateUser(User user) throws Exception;

	
	
}

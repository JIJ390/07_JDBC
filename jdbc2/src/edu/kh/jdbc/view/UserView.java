package edu.kh.jdbc.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;

public class UserView {
	// 필드
	private UserService service = new UserService();
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * JDBCTemplate 사용 테스트
	 */
	public void test() {
		// 입력 ID 와 일치하는 USER 정보 조회
		System.out.print("ID 입력 : ");
		String input = sc.nextLine();
		
		// 서비스 호출 후 결과 반환 받기
		User user = service.selectId(input);
		
		// 결과 출력
		System.out.println(user);
		
	}
	
	
	/**
	 * User 관리 프로그램 메인 메뉴
	 */
	public void mainMenu() {
		int input = 0;
		
		do {
			try {
				
				System.out.println("\n===== User 관리 프로그램 =====\n");
				System.out.println("1. User 등록(INSERT)");
				System.out.println("2. User 전체 조회(SELECT)");
				System.out.println("3. 이름으로 User 조회(포함)(SELECT)");
				System.out.println("4. USER_NO 번호로 User 조회(SELECT)");
				System.out.println("5. USER_NO 번호로 User 삭제(DELETE)");
				System.out.println("6. ID, PW 가 일치하는 회원 이름 수정(UPDATE)");
				System.out.println("7. User 등록(아이디 중복 검사)");
				System.out.println("8. 여러 User 등록하기");
				System.out.println("0. 프로그램 종료");
				
				System.out.println("=================================");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();	// 버퍼에 남은 개행문자 제거
				
				switch (input) {
				case 1  : insertUser();  break;
				case 2  : selectAll();   break;
				case 3  : selectName();  break;
				case 4  : selectNo();    break;
				case 5  : deleteUser();  break;
				case 6  : updateUser();  break;
				case 7  : insertUser2(); break;
				case 8  : multiInsert(); break;
				case 0  : System.out.println("\n[프로그램 종료]\n"); break;
				default : System.out.println("\n[메뉴 번호만 입력해 주세요]\n");
				}
				
				System.out.println("---------------------------------");
				
				
			} catch (InputMismatchException e) {
				// Scanner 를 이용한 입력 시 자료형이 잘못된 경우
				System.out.println("\n### 잘못 입력하셨습니다 ###\n");
				input = -1;		// 잘못 입력해서 while 문 멈추는 걸 방지
				sc.nextLine();	// 입력 버퍼 남아있는 잘못된 문자 제거
				
				
			} catch (Exception e) {
				// 발생되는 예외를 모두 해당 catch 구문으로 모아서 처리
				e.printStackTrace();
			}
			
			
		} while(input != 0);
	}
	

	/**
	 * 1. User 등록 
	 * @throws Exception 
	 */
	private void insertUser() throws Exception {
		System.out.println("\n=== 1. User 등록 ===\n");
		
		System.out.print("ID 입력 : ");
		String userId = sc.next();
		
		System.out.print("PW 입력 : ");
		String userPw = sc.next();
		
		System.out.print("이름 입력 : ");
		String userName = sc.next();
		
		// 입력 받은 값 3개를 한 번에 묶어서 전달할 수 있도록
		// User DTO 객체를 생성한 후 필드에 값 세팅
		User user = new User();
		
		// lombok 을 이용해 자동 생성된 setter 이용
		user.setUserId(userId); 
		user.setUserPw(userPw);
		user.setUserName(userName);
		
		// 서비스 호출(INSERT) 후 
		// 결과(삽입된 행의 개수, int) 반환 받기
		int result = service.insertUser(user);
		
		// 반환된 결과에 따라 출력할 내용 선택
		if (result > 0) {
			System.out.println("\n" + userId + " 사용자가 등록 되었습니다 \n");
		} else {
			System.out.println("\n### 등록 실패 ###\n");
		}
		
	}
	
	/**
	 * 2. User 전체 조회
	 * @throws Exception
	 */
	private void selectAll() throws Exception{
		System.out.println("\n=== 2. User 전체 조회(SELECT) ===\n");
		
		// 서비스 메서드 호출 후 결과(List<User>) 반환 받기
		List<User> userList = service.selectAll();
		
		// 조회결과가 없을 경우 .isEmpty() || .size() == 0
		if (userList.size() == 0) {
			System.out.println("\n조회결과가 없습니다\n");
			return;
		}
		
		// 향상된 for문
		for (User user : userList) {
			System.out.println(user); // 자동으로 user.toString() 호출
		}
	}
	

	/**
	 * 3. 이름으로 User 조회
	 * @throws Exception
	 */
	private void selectName() throws Exception {
		System.out.println("\n=== 3. 이름으로 User 조회(포함)(SELECT) ===\n");
		
		System.out.print("검색어 입력 : ");
		String keyword = sc.nextLine();
		
		// 서비스(SELECT) 호출 후 
		// 결과(List<User>) 반환 받기
		
		List<User> searchList = service.selectName(keyword);
		
		if (searchList.isEmpty()) {
			System.out.println("\n검색 결과가 없습니다\n");
			return;
		}
		
		for (User user : searchList) {
			System.out.println(user);
		}

	}
	
	
	/**
	 * 4. USER_NO 번호로 User 조회
	 * @throws Exception
	 */
	private void selectNo() throws Exception {
		System.out.println("\n=== 4. USER_NO 번호로 User 조회(SELECT) ===\n");
		
		System.out.print("사용자 번호 입력 : ");
		int numero = sc.nextInt();
		// 숫자 아닌 문자 넣었을 때 에러
		// java.sql.SQLSyntaxErrorException: ORA-01722: 수치가 부적합합니다
		// 보통 VIEW, JS 에서 처리함
		// ### 실수 String 자료형이 아닌 int 임!!!!!
		
		
		// 사용자 번호 == PK == 중복이 있을 수 없다
		// == 일치하는 사용자가 있다면 1행만 조회된다!!
		// -> 1행의 조회 결과를 담기 위해 사용하는 객체 == User DTO
		User user = service.selectNo(numero);
		
		if (user == null) {
			System.out.println("\n### 존재하지 않는 User 번호 ###\n");
			return;
		}
		
		System.out.println(user);
	}
	
	
	/**
	 * 5. USER_NO 번호로 User 삭제
	 * @throws Exception
	 */
	private void deleteUser() throws Exception {
		System.out.println("\n=== 5. USER_NO 번호로 User 삭제(DELETE) ===\n");
		
		System.out.print("번호 입력 : ");
		int numero = sc.nextInt();
		
		int result = service.deleteUser(numero);
		
		if (result > 0) {
			System.out.println("\n" + numero + " 번 User 정보가 삭제되었습니다\n");
		} else {
			System.out.println("\n### 존재하지 않는 User 번호 ###\n");
		}
		
	}
	
	/**
	 * 6. ID, PW 가 일치하는 회원 이름 수정(UPDATE)
	 * @throws Exception
	 */
	private void updateUser() throws Exception {
		System.out.println("\n=== 6. ID, PW 가 일치하는 회원 이름 수정(UPDATE) ===\n");
		
		System.out.print("ID 입력 : ");
		String userId = sc.next();
		
		System.out.print("PW 입력 : ");
		String userPw = sc.next();
		
		User user = new User();
		
		user.setUserId(userId);
		user.setUserPw(userPw);
		
		int userNo = service.selectUser(user); 
		
		if (userNo == 0) {
			System.out.println("\n### 아이디, 비밀번호가 일치하는 사용자가 없습니다 ###\n");
			return;
		}
		
		user.setUserNo(userNo);
		
		System.out.print("수정하려는 이름 입력 : ");
		String userName = sc.nextLine();
		
		user.setUserName(userName);
		
		int result = service.updateUser(user);
		
		if (result > 0) {
			System.out.println("\n수정 되었습니다\n");
		} else {
			System.out.println("\n### 수정 실패 ###\n");
			// 일반적인 경우 실패 가능성 없음
			// 실패하는 경우 )
			// 이름 입력 중 해당 user 삭제되는 경우
		}
	}
	

	/**
	 * 7. User 등록(아이디 중복 검사)
	 * @throws Exception
	 */
	private void insertUser2() throws Exception {
		System.out.println("\n=== 7. User 등록(아이디 중복 검사) ===\n");
		
		String userId = null;
		
		while (true) {
			System.out.print("ID 입력 : ");
			userId = sc.next();
			
			// 중복 검사
			int count = service.dupliCheck(userId);
			// 입력받은 userId가 중복인지 검사하는
			// 서비스(SELECT) 호출 후 
			// 결과(int, 중복 == 1, 아니면 == 0) 반환 받기
			
			if (count == 0) {
				System.out.println("\n사용 가능한 ID 입니다\n");
				break;
			}
			System.out.println("\n### 중복된 ID 가 존재합니다 ###");
			System.out.println("다시 입력해주세요\n");
		} 
		
		
		// 아이디가 중복이 아닌 경우 while 종료 후 pw, name 입력 받기
		System.out.print("PW 입력 : ");
		String userPw = sc.next();
		
		sc.nextLine();
		
		System.out.print("이름 입력 : ");
		String userName = sc.nextLine();
		
		User user = new User();
		
		user.setUserId(userId);
		user.setUserPw(userPw);
		user.setUserName(userName);
		
		int result = service.insertUser(user);
		
		if (result > 0) {
			System.out.println("\n" + userId + " 사용자가 등록 되었습니다 \n");
		} else {
			System.out.println("\n### 등록 실패 ###\n");
		}

		
	}
	

	/**
	 * 8. 여러 User 등록하기
	 * @throws Exception
	 */
	private void multiInsert() throws Exception {
		System.out.println("\n=== 8. 여러 User 등록하기 ===\n");
		
		System.out.print("등록할 유저 수 입력 : ");
		int numb = sc.nextInt(); 
		
		sc.nextLine();	// 개행 문자 제거
		
		List<User> userList = new ArrayList<User>();
		
		for (int i = 0; i < numb; i++) {
			
			String userId = null;
			
			while (true) {
				System.out.printf("%d 번째 ID 입력 : ", i + 1);
				userId = sc.nextLine();
				
				int count = service.dupliCheck(userId);
				boolean flag = true;
				
				// 도중에 입력된 userId 끼리 중복 검사
				for(User user : userList) {
					if (userId.equals(user.getUserId())) {
						flag = false;
						break;
					}
				}	

				if (count == 0 && flag) {
					System.out.println("\n사용 가능한 ID 입니다\n");
					break;
				}
				System.out.println("\n### 중복된 ID 가 존재합니다 ###");
				System.out.println("다시 입력해주세요\n");
			} 
			
			System.out.printf("%d 번째 PW 입력 : ", i + 1);
			String userPw = sc.nextLine();
			
			System.out.printf("%d 번째 이름 입력 : ", i + 1);
			String userName = sc.nextLine();
			
			System.out.println("-----------------------------");
			
			User user = new User();
			
			user.setUserId(userId); 
			user.setUserPw(userPw);
			user.setUserName(userName);
			
			userList.add(user);
		}
		
		// 입력 받은 모든 사용자 insert 하는 서비스 호출
		int result = service.multiInsert(userList);
		// 기존 insertUser 메서드 에서는 1행 단위로 트랜잭션 처리 일어나기 때문에
		// 모든 행을 동시 처리(트랜잭션) 하는 코드 형태를 작성해야 함
		
		if (result == userList.size()) {
			System.out.println("\n전체 삽입 성공\n");
		} else {
			System.out.println("\n### 등록 실패 ###\n");
		}
		
	}
	
}


































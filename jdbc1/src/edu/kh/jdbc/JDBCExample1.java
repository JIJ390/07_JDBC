package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample1 {
	public static void main(String[] args) {
		
		/* JDBC(Java DataBase Connectivity)
		 * 
		 * - Java 에서 DB 에 연결(접근) 할 수 있게 해주는
		 * 	 Java API (Java 에서 제공하는 코드)
		 * 	 -> java.sql 패키지에 존재함
		 */
		
		// Java 코드를 이용해
		// EMPLOYEE 테이블에서
		// 사번, 이름, 부서코드, 직급코드, 급여, 입사일 조회 후
		// 이클립스 콘솔에 출력

		/* 1. JDBC 객체 참조용 변수 선언 */
		// java.sql.Connection
		// 특정 DBMS 와 연결하기 위한 정보를 저장한 객체
		// == DBeaver 에서 사용하는 DB 연결 (새 데이터베이스 연결) 과 같은 역할의 객체
		//	  (DB 서버 주소, 포트 번호, DB이름, 계정명, 비밀번호)
		Connection conn = null;
		
		// java.sql.Statement
		// - 1) SQL 을 DB 에 전달
		// - 2) DB 에서 SQL 수행 결과를 반환 받아옴
		Statement stmt = null;
		
		// java.sql.ResultSet
		// - SELECT 조회 결과를 저장하는 객체
		ResultSet rs = null;
		
		try {
			/* 2. DriverManager 객체를 이용해서 Connection 객체 생성하기 */
			// java.sql.DriverManager
			//	- DB 연결 정보와 JDBC 드라이버를 이용해서
			//	  원하는 DB 와 연결할 수 있는 Connection 객체를 생성하는 객체
			
			/* 2-1) Oracle JDBC Driver 객체를 메모리에 로드(적재)하기 */
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Class.forName("패키지명+클래스명")
			//	- 해당 클래스를 읽어 메모리를 적재
			//	-> JVM 이 프로그램 동작에 사용할 객체를 생성하는 구문
			
			// oracle.jdbc.driver.OracleDriver
			// - Oracle DBMS 연결 시 필요한 코드가 담겨있는 클래스
			//	 (Oracle 에서 만들어서 준 클래스)
			
			/* 2-2) DB 연결 정보 작성 */ 
			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류/oacle:thin 타입
			
			String host = "localhost";  // DB 서버 컴퓨터의 IP 또는 도메인 주소
	                                    // == 현재 컴퓨터
			
			String port = ":1521";      // 프로그램 연결을 위한 구분 번호(오라클 기본번호:1521)  
			
			String dbName = ":XE";      // DBMS(XE = eXpress Edition)
			
			String userName = "KH_JIJ"; // 사용자 계정명
			
			String password = "KH1234"; // 계정 비밀번호
			
			/* 2-3) DB 연결 정보와 DriverManager 를 이용해서 
			 * Connection 객체 생성                          */
			
			conn = DriverManager.getConnection(
					type + host + port + dbName,	// url
					userName,						// user
					password); 						// password
			
			
			// Connection 객체가 잘 생성되었는지 확인
			// == DB 연결 정보에 오타가 없는지 확인
			System.out.println(conn);
			
			
			/* 3. SQL 작성 */
			// !! 주의사항 !!
			// -> JDBC 코드에서 SQL 작성 시
			//	  세미콜론 (;) 을 작성하면 안된다!!
			// -> "SQL 명령어가 올바르게 종료되지 않았습니다" 예외 발생
			
			//	 TIP) JDBC는 ; 을 인식하지 못함(어차피 SQL 코드를 하나씩 보내기 때문!!)
			String sql = 
					"SELECT " // 띄어쓰기 필수!! 없으면 SELECTEMP_ID 이런 식으로 붙여서 나옴
					+ "EMP_ID, EMP_NAME, DEPT_CODE, JOB_CODE, SALARY, HIRE_DATE "
					+ "FROM EMPLOYEE ";
			
			/* 4. Statement 객체 생성 */
			stmt = conn.createStatement();
			// 연결된 DB(Connection) 에 SQL 을 전달하고 결과를 반환받을 
			// Statement 객체를 생성함
			
			/* 5. Statement 객체를 이용해서 SQL 수행 후 결과 반환 받기 */
			// 1) ResultSet Statement.executeQuery(sql) 
			// -> SELECT 문 실행 메서드
			//	  결과로 java.sql.ResultSet 반환
			//	  DQL(Data Query Language) : 데이터 질의 언어
			
			
			// 2) int Statement.executeUpdate(sql);
			// -> [DML : Data Manipulation Language] : 데이터 조작 언어
			//	  INSERT, UPDATE, DELETE 실행 메서드
			//    결과로 int 반환 (삽입, 수정, 삭제된 행의 개수)
			
			rs = stmt.executeQuery(sql);
			
			/* 6. 조회 결과가 담겨 있는 ResultSet 을 
			 * 커서(Cursor) 를 이용해 1행씩 접근해
			 * 각 행에 작성된 컬럼 값 얻어오기 */
			
			// rs.next() : 커서를 다음 행으로 이동 시킨 후 
			//             이동된 행에 값이 있으면 true, 없으면 false 반환
			//             맨 처음 호출 시 1 행부터 시작
			
			while(rs.next()) {
				// 200	선동일	D9	J1	8000000	2000-02-06 00:00:00.000
				
				// rs.get자료형(컬럼명 | 순서);
				// -> 현재 행에서 지정된 컬럼의 값을 얻어와 반환
				// -> 지정된 자료형 형태로 값이 반환됨
				//	  (자료형을 잘못 지정하면 예외 발생!!)
				
				// [Java]         [DB]
				// String         CHAR, VARCHAR2
				// int, long      NUMBER (정수만 저장된 컬럼)
				// float, double  NUMBER (정수 + 실수)
				// java.sql.Date  DATE
				
				String empID    = rs.getString("EMP_ID");
				String empName  = rs.getString("EMP_NAME");
				String deptCode = rs.getString("DEPT_CODE");
				String jobCode  = rs.getString("JOB_CODE");
				int    salary   = rs.getInt("SALARY");
				Date   hireDate = rs.getDate("HIRE_DATE");
				
				System.out.printf(
						"사번 : %s / 이름 : %s / 부서코드 : %s "
						+ "/ 직급코드 : %s / 급여 : %d / 입사일 : %s \n",
						empID, empName, deptCode, jobCode, salary, hireDate.toString());
			}
			
			
			
		} catch (SQLException e) {
			// SQLException : DB 연결과 관련된 예외의 최상위 부모
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			/* 7. 사용 완료된 JDBC 객체 자원 반환(close) */
			
			// -> 수행하지 않으면 DB 와 연결된 Connection 이 남아 있어서
			//    다른 클라이언트가 추가적으로 연결되지 못하는 
			//    문제가 발생할 수 있다! (메모리 영역을 계속 차지하여 속도가 느려지기도...)
			
			try {
				/* 만들어진 역순으로 close 수행하는 것을 추천 */
				if (rs   != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				
				// if 문은 NullPointerException 방지용 구문
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}





























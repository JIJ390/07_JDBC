package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/* Template : 양식, 주형, 본을 뜨기 위한 틀
 * -> "미리 만들어뒀다" 를 의미
 * 
 * JDBCTemplate : JDBC 관련 작업을 위한 코드를
 * 			      미리 작성해서 제공하는 클래스
 * 
 * - getConnection() + AutoCommit false
 * - commit() / rollback()
 * - 각종 close()
 * 
 * ***** 중요!!! *****
 * 어디서든 JDBCTemplate 클래스를 
 * 객체로 만들기 않고도 메서드를 사용할 수 있도록 하기 위해
 * 모든 메서드를 public static 으로 선언!!!!!! 
 */
public class JDBCTemplate {

	// 필드 
	private static Connection conn = null;
	// -> static 메서드에서는 
	//    static 필드만 사용 가능
	// 프로그램 실행 시 만들어지는(static) conn 필드
	
	// 메서드
	
	/**
	 * 호출 시 Connection 객체를 생성해서 반환하는 메서드
	 * @return
	 */
	public static Connection getConnection() {
		
		try {
			// 이전에 참조하던 Connection 객체가 존재하고
			// 아직 closer된 상태가 아니라면
			if(conn != null && !conn.isClosed()) {
				return conn; // 새로 만들지 않고 기존 Connection 반환
			}
			
			/* DB 연결을 위한 정보들을 별도 파일에 작성하여
			 * 읽어오는 형식으로 코드를 변경!!
			 * 
			 * 이유 1 : 보안적인 측면에서 코드를 직접 보지 못하게 함
			 * 
			 * 이유 2 : 혹시라도 연결하는 DB 정보가 변경될 경우
			 * 			Java 코드가 아닌
			 * 			읽어오는 파일의 내용을 수정하면 되기 때문에
			 * 			Java 코드 수정 x -> 추가 컴파일 필요 x
			 * 			--> 개발 시간 단축!!
			 * 
			 */
			
			// driver.xml 파일 내용 읽어오기
			
//			1. Properties 객체 생성
//			 - Map 의 자식 클래스
//			 - K, V 가 모두 String 타입
//			 - xml 파일 입출력을 쉽게 할 수 있는 메서드 제공
			
			Properties prop = new Properties();
			
//			2. Properties 메서드를 이용해서
//			   driver.xml 파일 내용을 읽어와 prop 에 저장
			String filepath = 
					JDBCTemplate.class.getResource("/edu/kh/jdbc/sql/driver.xml").getPath();
			
			// -> 빌드(코드를 실행 가능한 상태로 만드는 것) 시
			//    JDBCTemplate.class (class == '2 진법으로 컴파일된', java 확장자는 사람이 보는 것!) 
			//    파일의 위치에서
			//    /edu/kh/jdbc/sql/driver.xml 파일을 찾아
			//    실제 경로를 얻어오기
				/* C:\workspace\07_JDBC/userManagement\src\main\java\edu\kh\jdbc\sql\driver.xml */
			
			
			prop.loadFromXML(new FileInputStream(filepath));
			
//			prop 에 저장된 값(driver.xml 에서 읽어온 값) 을 이용해
//			Connection 객체 생성
			
			Class.forName(prop.getProperty("driver"));
			String url      = prop.getProperty("url");
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password"); 
			
			conn = DriverManager.getConnection(url, userName, password);
			
			// 만들어진 Connection 에 AutoCommit 끄기
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	// 트랜잭션 제어 처리 메서드 (commit, rollback) 
	
	/**
	 * 전달 받은 커넥션에서 수행한 SQL 을 Commit 하는 메서드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		try {	// 정상적인 커넥션이 연결되어 있을 때
			if (conn != null && !conn.isClosed()) conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 전달 받은 커넥션에서 수행한 SQL 을 Rollback 하는 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 전달 받은 커넥션을 close(자원 반환)하는 메서드
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 전달 받은 Statement를 close(자원 반환)하는 메서드
	 * + PreparedStatement 도 close 처리 가능!!
	 * 	 왜? PreparedStatement 가 Statement 의 자식이기 때문에
	 * 	 (다형성 업캐스팅) (동적 바인딩)
	 * @param stmt
	 */
	public static void close(Statement stmt) {	// 메서드 오버로딩 close 같은 이름의 두개의 메서드
		try {
			if (stmt != null && !stmt.isClosed()) stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 전달 받은 ResultSet close(자원 반환)하는 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


















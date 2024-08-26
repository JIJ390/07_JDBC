package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample6 {
	public static void main(String[] args) {
		// 아이디, 비밀번호를 입력 받아
		// 일치하는 사용자(TB_USER) 의 이름을
		// 수정
		
//		UPDATE TB_USER
//		SET 
//			USER_NAME = '홍길동'
//		WHERE 
//			USER_ID = 'user01' 
//		AND 
//			USER_PW = 'pass01';
//		아이디, 비밀번호 일치 -> 수정 성공(1)
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "KH_JIJ";
			String password = "KH1234"; 
			
			conn = DriverManager.getConnection(url, userName, password);
			
			// 자동 커밋 끄기
			conn.setAutoCommit(false);
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("아이디 입력 : ");
			String id = sc.nextLine();
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.nextLine();
			
			System.out.print("수정할 이름 입력 : ");
			String name = sc.nextLine();

			
			String sql = """
				UPDATE TB_USER
				SET 
					USER_NAME = ?
				WHERE 
					USER_ID = ? 
				AND 
					USER_PW = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			
			int result = pstmt.executeUpdate();
			
			if (result > 0) {
				System.out.printf("이름이 %s 로 수정 되었습니다 \n", name);
				conn.commit();
			} else {
				System.out.println("아이디 또는 비밀번호 불일치");
				conn.rollback();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn  != null) conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	}
}

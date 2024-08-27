package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Practice1 {
	public static void main(String[] args) {
		
		// EMPLOYEE	테이블에서
		// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
		// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것
				
		// - 조건 1 : 성별 (M, F)
		// - 조건 2 : 급여 범위
		// - 조건 3 : 급여 오름차순/내림차순

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "KH_JIJ";
			String password = "KH1234"; 
			
			conn = DriverManager.getConnection(url, userName, password);
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("조회할 성별(M/F) : ");
			String inputGender = sc.nextLine().toUpperCase();
			
			System.out.print("급여 범위(최소, 최대 순서로 작성) : ");
			int min = sc.nextInt();
			int max = sc.nextInt();
			
			System.out.print("급여 정렬(1.ASC, 2.DESC) : ");
			int input = sc.nextInt();
			
			if (inputGender.equals("M")) inputGender = "1";
			if (inputGender.equals("F")) inputGender = "2";
			
			String sql = """
					SELECT EMP_ID, EMP_NAME, 	
						DECODE(SUBSTR(EMP_NO, 8, 1),
						'1', 'M',
						'2', 'F') 성별,
						SALARY, JOB_NAME, 
						NVL(DEPT_TITLE, '없음') 부서명
					FROM EMPLOYEE
					LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
					JOIN JOB USING (JOB_CODE)
					WHERE SUBSTR(EMP_NO, 8, 1) = ?
					AND SALARY BETWEEN ? AND ?
					ORDER BY SALARY 
					""";
			
//			WHERE 조건문 이런 식으로 작성도 가능
//			숫자로 변경해 주지 않아도 됨
//			DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') = 'M' || 'F'

			if (input == 1) sql += "ASC";
			if (input == 2) sql += "DESC";
//			sql 전달 전에 작성!!!
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputGender);
			pstmt.setInt(2, min);
			pstmt.setInt(3, max);
//			pstmt.setString(4, "ASC");
//			placeholder 는 값 대입 시 자료형에 맞는 형태의 리터럴 표기법에 따라 대입된다
//			.setString() 이 경우 ASC -> 'ASC' 형태로 들어가기 때문에 오류 발생!!!
			
			rs = pstmt.executeQuery();
			
			System.out.println();
			
			System.out.printf("%2s|%-7s|%-2s|%-7s|%-3s|%-10s \n",
					"사번", "이름", "성별", "급여", "직급명", "부서명");
			
			System.out.println("-----------------------------------------------");
			
			boolean flag = true;
			
			while (rs.next()) {
				flag = false;
				
				String empID      = rs.getString("EMP_ID");
				String empName    = rs.getString("EMP_NAME");
				String gender     = rs.getString("성별");
				int    salary     = rs.getInt("SALARY");
				String jobName    = rs.getString("JOB_NAME");
				String deptTitle  = rs.getString("부서명");
				
				
				System.out.printf("%-4s|%-6s|%-4s|%-9d|%-4s|%-10s \n", 
						empID, empName, gender, salary, jobName, deptTitle);
			}
			
			if (flag) System.out.println("### 조회된 결과가 없습니다 ###");
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}

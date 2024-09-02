package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {
	public static void main(String[] args) {
		// 입력 받은 최소 급여 이상
		// 입력 받은 최대 급여 이하를 받는
		// 사원의 사번, 이름, 급여를 급여 내림차순으로 조회
		
		// [실행화면]
		// 최소 급여 : 1000000
		// 최대 급여 : 3000000
		
		// (사번) / (이름) / (급여)
		// (사번) / (이름) / (급여)
		// (사번) / (이름) / (급여)
		
		
		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs   = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@"; 
			String host = "khj-1.xyz";  				//변경
			String port = ":10000";     				//변경
			String dbName = ":XE";      
			String userName = "KH_COMMON"; 				//변경
			String password = "KH1234";
			
			conn = DriverManager.getConnection(
					type + host + port + dbName,
					userName,
					password);
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("최소 급여 : ");
			int minSalary = sc.nextInt();
			
			System.out.print("최대 급여 : ");
			int maxSalary = sc.nextInt();
			
			
			String sql = """
					SELECT EMP_ID, EMP_NAME, SALARY
					FROM EMPLOYEE
					WHERE SALARY BETWEEN  
					"""	// 띄어쓰기 인식 못해서 엔터
					+ minSalary + " AND " + maxSalary
					+ " ORDER BY SALARY DESC";
			
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			int count = 0;
			int sum   = 0;
			
			while (rs.next()) {
				String empID   = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int    salary  = rs.getInt("SALARY");
				
				System.out.printf("%s / %s / %d \n", empID, empName, salary);
				count++;
				sum += salary;
			}
			
			System.out.printf("총     원 : %10d 명 \n", count);
			System.out.printf("총   급여 : %,10d 원 \n", sum);
			System.out.printf("평균 급여 : %,10d 원 \n", sum / count);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs   != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}

}



















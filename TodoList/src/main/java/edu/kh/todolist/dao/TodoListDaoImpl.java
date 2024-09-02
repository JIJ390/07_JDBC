package edu.kh.todolist.dao;

import static edu.kh.todolist.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.todolist.common.JDBCTemplate;
import edu.kh.todolist.dto.Todo;


public class TodoListDaoImpl implements TodoListDao{
	
	private Statement stmt; 	
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public TodoListDaoImpl() {
		try {
			String filePath = 
					JDBCTemplate.class.getResource("/edu/kh/todolist/sql/sql.xml").getPath();
			
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Todo> todoListFullView(Connection conn) throws Exception {
		List<Todo> todoList = new ArrayList<Todo>();
		
		try {
			String sql = prop.getProperty("selectAllTodo");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				String title    = rs.getString("TODO_TITLE");
				String detail   = rs.getString("TODO_DETAIL");
				String complete = rs.getString("TODO_COMPLETE");
				String regDate  = rs.getString("REG_DATE");
				
				Todo todo = new Todo(title, detail, complete, regDate);
				
				todoList.add(todo);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		return todoList;
	}

	
	
	@Override
	public Todo todoDetailView(Connection conn, int index) throws Exception {
		Todo todo = null;
		
		try {
			String sql = prop.getProperty("selectIndex");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, index);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String title    = rs.getString("TODO_TITLE");
				String detail   = rs.getString("TODO_DETAIL");
				String complete = rs.getString("TODO_COMPLETE");
				String regDate  = rs.getString("REG_DATE");
				
				todo = new Todo(title, detail, complete, regDate);
			}
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return todo;
	}

	@Override
	public int todoAdd(Connection conn, String title, String detail) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("insertTodo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, detail);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	@Override
	public int todoComplete(Connection conn, Todo todo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateComplete");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, todo.getTitle());
			pstmt.setString(2, todo.getRegDate());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	@Override
	public int todoUpdate(Connection conn, Todo targetTodo, Todo todo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateTodo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, todo.getTitle());
			pstmt.setString(2, todo.getDetail());
			pstmt.setString(3, targetTodo.getTitle());
			pstmt.setString(4, targetTodo.getRegDate());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	@Override
	public int todoDelete(Connection conn, Todo todo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("deleteTodo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, todo.getTitle());
			pstmt.setString(2, todo.getRegDate());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}
































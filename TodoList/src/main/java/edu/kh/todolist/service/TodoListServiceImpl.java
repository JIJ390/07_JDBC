package edu.kh.todolist.service;


import static edu.kh.todolist.common.JDBCTemplate.*;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import edu.kh.todolist.dao.TodoListDao;
import edu.kh.todolist.dao.TodoListDaoImpl;
import edu.kh.todolist.dto.Todo;

public class TodoListServiceImpl implements TodoListService{

	private TodoListDao dao = new TodoListDaoImpl(); 
	
	@Override
	public Map<String, Object> todoListFullView() throws Exception {
		
		Connection conn =  getConnection();
		
		List<Todo> todoList = dao.todoListFullView(conn);
		
		close(conn);
		
		int completeCount = 0;
		
		// 메서드 하나 만들어서 sql 에서 count 하는 것도 괜찮아 보인다

		for(Todo todo : todoList) {
			if(todo.getComplete().equals("O")) {
				completeCount++;
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("todoList", todoList);
		map.put("completeCount", completeCount);
		
		return map;
	}

	@Override
	public Todo todoDetailView(int index) throws Exception {
		
		Connection conn = getConnection();
		
		index++;
		
		Todo todo = dao.todoDetailView(conn, index);
		
		close(conn);
		
		return todo;
	}

	@Override
	public int todoAdd(String title, String detail) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.todoAdd(conn, title, detail);
		
		if(result > 0) commit(conn);
		else	           rollback(conn);
		
		close(conn);
		
		return result;
	}

	@Override
	public boolean todoComplete(int index) throws Exception {
		Connection conn = getConnection();
		
		index++;
		
		// index 로 타겟 판단하는게 번거롭긴 하지만
		// jsp 를 최대한 건드리지 않는 방향으로 작성
		
		// 다 만들고 생각하니 그냥 PK 주는게 더 나아보임
		// JSP 에서 생각보다 건드릴 부분이 없음
		
		// PK 안넣었을 때 문제점
		// DETAIL 빈칸 일때 수정 삭제 불가
		// 제목 내용 같으면 동시에 수정 삭제됨
		
		// 다음에 이런거 할때는 좀더 깊게 생각해서 PK 넣을지 말지 생각
		
		// 아니면 내용 말고 시간으로 하면 괜찮을거 같다
		Todo todo = dao.todoDetailView(conn, index);
		
		int result = dao.todoComplete(conn, todo);
		
		boolean complResult = false;
		
		if(result > 0) {
			commit(conn);
			complResult = true;
		}
		else	{
			rollback(conn);
		}
		
		close(conn);
		
		return complResult;
	}

	@Override
	public boolean todoUpdate(int index, String title, String detail) throws Exception  {
		Connection conn = getConnection();
		
		index++;
		
		Todo targetTodo = dao.todoDetailView(conn, index);
		
		Todo todo = new Todo();
		
		todo.setTitle(title);
		todo.setDetail(detail);
		
		int result = dao.todoUpdate(conn, targetTodo, todo);
		
		boolean updateResult = false;
		
		if(result > 0) {
			commit(conn);
			updateResult = true;
		}
		else	{
			rollback(conn);
		}
		
		close(conn);
		
		return updateResult;
	}

	@Override
	public String todoDelete(int index) throws Exception {
		Connection conn = getConnection();
		
		index++;
		
		Todo todo = dao.todoDetailView(conn, index);
		
		int result = dao.todoDelete(conn, todo);
		
		
		if(result > 0) {
			commit(conn);
			return todo.getTitle();
		}
		else	{
			rollback(conn);
		}
		
		close(conn);
		
		return null;
	}
	
}


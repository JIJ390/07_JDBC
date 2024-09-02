package edu.kh.todolist.dao;

import java.sql.Connection;
import java.util.List;

import edu.kh.todolist.dto.Todo;

public interface TodoListDao {

	List<Todo> todoListFullView(Connection conn) throws Exception;

	Todo todoDetailView(Connection conn, int index) throws Exception;

	int todoAdd(Connection conn, String title, String detail) throws Exception;

	int todoComplete(Connection conn, Todo todo) throws Exception;

	int todoUpdate(Connection conn, Todo targetTodo, Todo todo) throws Exception;

	int todoDelete(Connection conn, Todo todo) throws Exception;

}
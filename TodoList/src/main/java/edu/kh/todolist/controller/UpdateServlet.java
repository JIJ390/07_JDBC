package edu.kh.todolist.controller;

import java.io.IOException;

import edu.kh.todolist.dto.Todo;
import edu.kh.todolist.service.TodoListService;
import edu.kh.todolist.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/todo/update")
public class UpdateServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int index = Integer.parseInt(req.getParameter("index"));
		
		try {
			TodoListService service = new TodoListServiceImpl();
			Todo todo = service.todoDetailView(index);
			
			req.setAttribute("todo", todo);
			
			String path = "/WEB-INF/views/update.jsp";
			req.getRequestDispatcher(path).forward(req, resp);
			
			
		} catch (Exception e) {
			
		}
				
	}
	
	/* 요청 주소가 같아도
	 * 데이터 전달 방식(method - GET / POST) 이 다르면
	 * 각각의 메서드에서 처리할 수 있다
	 */
	
//	할 일 제목 / 내용 수정 (POST 방식 요청)
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int index = Integer.parseInt(req.getParameter("index"));
		String title   = req.getParameter("title");
		String detail = req.getParameter("detail");
		
		try {
			TodoListService service = new TodoListServiceImpl();
			boolean result = service.todoUpdate(index, title, detail);
			
			String url = null;
			String str = null;
			
			if (result) {
				str = "수정되었습니다";
				url = "/todo/detail?index=" + index;
				
			} else {
				str = "수정 실패";
				url = "/todo/update?index=" + index;	
			}
			
//			redirect 는 GET 방식 요청
			
			req.getSession().setAttribute("message", str);
			resp.sendRedirect(url);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

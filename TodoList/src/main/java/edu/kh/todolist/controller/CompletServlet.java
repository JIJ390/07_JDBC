package edu.kh.todolist.controller;

import java.io.IOException;

import edu.kh.todolist.service.TodoListService;
import edu.kh.todolist.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/todo/complete")
public class CompletServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int index = Integer.parseInt(req.getParameter("index"));
		
		try {
			TodoListService service = new TodoListServiceImpl();
			
			boolean result = service.todoComplete(index);
			
			HttpSession session = req.getSession();
			
//			변경 성공 시 -> 원래 보고 있던 상세 페이지로 redirect
			if (result) {
				session.setAttribute("message", "완료 여부가 변경되었습니다");
				resp.sendRedirect("/todo/detail?index=" + index);
				return;
			}
			
			session.setAttribute("message", "해당 index번째 todo 가 존재하지 않습니다");
			
			resp.sendRedirect("/");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	

}

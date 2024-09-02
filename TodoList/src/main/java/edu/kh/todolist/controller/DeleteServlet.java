package edu.kh.todolist.controller;

import java.io.IOException;

import edu.kh.todolist.service.TodoListService;
import edu.kh.todolist.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/todo/delete")
public class DeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int index = Integer.parseInt(req.getParameter("index"));
		
		try {
			TodoListService service = new TodoListServiceImpl();
			String title = service.todoDelete(index);
			
			String result = null;
			
			if (title != null) result = title + " 할 일이 삭제 되었습니다";
			else			   result = "해당 index 번째 todo 가 존재하지 않습니다";
			
			req.getSession().setAttribute("message", result);
			resp.sendRedirect("/");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

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

@WebServlet("/todo/detail")
public class DetailServlet extends HttpServlet{
//	a 태그 요청은 GET 방식
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
//			전달 받은 파라미터 얻어오기 
			int index = Integer.parseInt(req.getParameter("index"));
			
//			상세 조회 서비스 호출 후 결과 반환 받기
			TodoListService service = new TodoListServiceImpl();
			Todo todo = service.todoDetailView(index);
//			index 번째 todo 가 없다면 null 반환
			
//			index 번째 todo 가 존재하지 않을 경우
//			-> 메인 페이지(/) redirect 후
//			   "해당 index 에 할 일이 존재하지 않습니다"
//			   alert 출력
			if (todo == null) {
//				session 에 message 세팅
//				redirect 는 HttpServletRequest / HttpServletResponse가 새로 만들어지기 때문에 session 사용
				req.getSession().setAttribute("message", "해당 index 에 할 일이 존재하지 않습니다");
				resp.sendRedirect("/");
				
				return;
			}
			
//			index 번째 todo 가 존재하는 경우
//			detaul.jsp 로 forward 해서 응답
			
			req.setAttribute("todo", todo);
			
			String path = "/WEB-INF/views/detail.jsp";
			req.getRequestDispatcher(path).forward(req, resp);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}


















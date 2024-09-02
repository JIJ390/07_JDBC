package edu.kh.todolist.controller;

import java.io.IOException;

import edu.kh.todolist.service.TodoListService;
import edu.kh.todolist.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/todo/add")
public class TodoAddServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
//			1. TodoListServiceImple 객체 생성
			TodoListService service = new TodoListServiceImpl();
			
//			2. 요청 시 전달 받은 파라미터 얻어오기
			String title  = req.getParameter("title");
			String detail = req.getParameter("detail");
			
//			3. 서비스 호출 후 결과 반환 받기
			int index = service.todoAdd(title, detail);
//			실패 시 -1 반환
			
//			4. 성공/ 실패 메시지 세팅
			String message = null;
			if(index > 0) message = "추가 성공!";
			else	          message = "추가 실패!"; 	
			
//			5. 기존 req 를 사용할 수 없기 때문에
//			session을 이용해서 message 를 세팅
			req.getSession().setAttribute("message", message);
			
//			6. 메인 페이지로 redirect
			resp.sendRedirect("/");
//			-> @WebServlet("/main") 가 작성된
//			   Servlet을 재요청
			
//			일견 비효율 적인 것처럼 보이지만 todo가 저장된 list를
//			다시 불러와 갱신해야 함!!
			
			/* redirect 는 무조건 GET 방식 */
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

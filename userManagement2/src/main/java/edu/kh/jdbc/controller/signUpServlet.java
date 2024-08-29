package edu.kh.jdbc.controller;

import java.io.IOException;

import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;
import edu.kh.jdbc.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/signUp")
public class signUpServlet extends HttpServlet{

	// 사용자 등록 페이지로 전환
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "WEB-INF/views/signUp.jsp";
		req.getRequestDispatcher(path).forward(req, resp);
	}
	
	// 사용자 등록 요청 처리
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 제출된 파라미터 얻어오기 (input 의 name 속성값)
		String userId   = req.getParameter("userId");
		String userPw   = req.getParameter("userPw");
		String userName = req.getParameter("userName");
		
		try {
			// 전달 받은 파라미터를 한 번에 저장할
			// User DTO 객체 생성
			User user = new User();
			user.setUserId(userId);
			user.setUserPw(userPw);
			user.setUserName(userName);
			
			// 서비스(INSERT) 호출 후 결과(INT:삽입된 행의 개수) 반환
			UserService service = new UserServiceImpl();
			// 다형성 업캐스팅
			
			int result = service.insertUser(user);
			
			// 결과에 따라 응답 방법 처리
			String message = null;
			
			if (result > 0) message = userId + " 사용자 등록 성공";
			else            message = "등록 실패";
			
			// page, request, session, application 중
			// session을 이용해서 message 값 전달
			
//			HttpSession session = req.getSession();
//			session.setAttribute("message", message);
			
			req.getSession().setAttribute("message", message);
			
			// 메인 페이지로 리다이렉트(재요청) => request 폐기됨
			resp.sendRedirect("/");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}























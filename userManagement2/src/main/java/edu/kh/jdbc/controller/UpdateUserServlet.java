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

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 파라미터 얻어오기
			String userPw   = req.getParameter("userPw");
			String userName = req.getParameter("userName");
			
			int userNo      = Integer.parseInt(req.getParameter("userNo"));
			
			// 받아온 파라미터 User 객체에 세팅
			User user = new User();
			
			user.setUserPw(userPw);
			user.setUserName(userName);
			user.setUserNo(userNo);
			
			UserService service = new UserServiceImpl();
			
			int result = service.updateUser(user);
			
			String message = null;
			
			if (result > 0) message = "수정 되었습니다";
			else            message = "수정 실패";
			
			req.getSession().setAttribute("message", message);
			
			// 리다이렉트 GET 방식 요청(주소창뒤에 파라미터)
			resp.sendRedirect("/selectUser?userNo=" + userNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}

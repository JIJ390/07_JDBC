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

@WebServlet("/selectUser")
public class SelectUserServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			// 형변환 하지 않아도 오라클이 파싱해주긴 함!
			// 단 될 때도 안될 때도 있음
			// ex) ojdbc 옛날 버전이 안되는 경우
			// 그래서 자료형 맞춰주는 걸 권장
			int userNo = Integer.parseInt(req.getParameter("userNo"));
			
			UserService service = new UserServiceImpl();
			
			User user = service.selectUser(userNo);
			
			req.setAttribute("user", user);
			
			String path = "/WEB-INF/views/selectUser.jsp";
			req.getRequestDispatcher(path).forward(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

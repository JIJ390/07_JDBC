package edu.kh.jdbc.controller;

import java.io.IOException;
import java.util.List;

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
		
		String searchName = req.getParameter("userName");
		
		try {
			UserService service = new UserServiceImpl();
			
			List<User> searchList = service.selectUser(searchName);
			
			if (searchList.isEmpty()) {
				req.setAttribute("message", "조회된 결과가 없습니다");
			} else {
				req.setAttribute("userList", searchList);
			}
			
			String path = "/WEB-INF/views/selectAll.jsp";
			req.getRequestDispatcher(path).forward(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

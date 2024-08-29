package edu.kh.jdbc.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class MainServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// /main 으로 요청이 오면 /WEB-INF/views/main.jsp 경로로 요청 위임
		String path = "/WEB-INF/views/main.jsp";
		req.getRequestDispatcher(path).forward(req, resp);
		
	}
}

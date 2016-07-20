package com.zjhhcsoft.uac.clinet.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if (username.equals(password)) {
			req.getSession().setAttribute("LOGIN_USER", username); // 在session中保存登陆对象
			req.getRequestDispatcher("index.html").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("login.html").forward(req, resp);
	}

}

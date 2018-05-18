package servlet;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDAO;

public class Register extends HttpServlet {
	protected void service(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException {
		request.setCharacterEncoding("UTF-8");
		
		User user = new User();
		
		user.name = request.getParameter("name");
		user.password = request.getParameter("password");

		UserDAO dao = new UserDAO();
		if(!dao.exist(user.name)) {
			dao.add(user);
			response.sendRedirect("loginSuccess.html");
		}
		else 
			response.sendRedirect("registerFail.html");
	}
}

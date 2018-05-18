package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import bean.User;
import dao.UserDAO;

public class Login extends HttpServlet {
	protected void service(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException {
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		if(!new UserDAO().exist(name)) {
			response.sendRedirect("loginFail.html");
		}
		else if(new UserDAO().get(name).equals(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("username", name);
			response.sendRedirect("listRecord.jsp");
		}
		else {
			response.sendRedirect("loginFail.html");
		}
	}
}

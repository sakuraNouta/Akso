package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import bean.Record;
import dao.RecordDAO;

public class RecordList extends HttpServlet  {
	protected void service(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException {
			response.setContentType("text/html;charset=UTF-8");
			
			List<Record> records = new RecordDAO().list();

			
			request.setAttribute("records", records);
			request.getRequestDispatcher("listRecord.jsp").forward(request, response);
		}
}

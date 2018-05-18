package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Record;
import dao.RecordDAO;

public class RecordView extends HttpServlet{
	protected void service(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException {
		request.setCharacterEncoding("UTF-8");
		
		Record record = new Record();
		
		record.name = request.getParameter("name");
		record.date = request.getParameter("date");
		record.info = request.getParameter("info");

		RecordDAO dao = new RecordDAO();
		
		dao.add(record);
	}
}

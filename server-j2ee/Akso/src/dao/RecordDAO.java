package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Record;

public class RecordDAO {
	public RecordDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/how2java?characterEncoding=UTF-8",
				"root","admin");
	}
	
	/**返回数据库中数据的总数*/
	public int getTotal() {
		int total = 0;
		try(Connection c = getConnection();Statement s = c.createStatement();) {
			String sql = "select count(*) from record";
			
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				total = rs.getInt(1);
			}
			System.out.println("total:" + total);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	/**向record表中加入数据*/
	public void add(Record record) {
		String sql = "insert into record values(null,?,?,?)";
				
		try(Connection c = getConnection();PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1,record.name);
			ps.setString(2,record.date);
			ps.setString(3,record.info);
			
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) { 
				int id = rs.getInt(1);
				//置传入的record.id变量为数据库id
				record.id = id; 
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**更新record表中已有数据*/
	public void update(Record record) {
		String sql = "update record set name = ?,date = ?,info = ? where id = ?";
		try(Connection c = getConnection();PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1,record.name);
			ps.setString(2,record.date);
			ps.setString(3,record.info);
			ps.setInt(3,record.id);
			
			ps.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**删除*/
	public void delete(int id) {
		try(Connection c= getConnection();Statement s = c.createStatement();) {
			String sql = "delete from record where id = " + id;
			
			//处理sql语句
			s.execute(sql);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**根据id查找*/
	public Record get(int id) {
		Record record = null;
		
		try(Connection c = getConnection();Statement s= c.createStatement();) {
			String sql = "select * from record where id = " + id;
			
			ResultSet rs = s.executeQuery(sql);
			
			if(rs.next()) {
				record = new Record();
				String name = rs.getString(2);
				String date = rs.getString(3);
				String info = rs.getString(4);
				
				record.name = name;
				record.date = date;
				record.info = info;
				record.id = id;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return record;
	}
	
	/**查看数据库全部数据*/
	public List<Record> list() {
		return list(0,Short.MAX_VALUE);
	}
	
	/**分页显示*/
	public List<Record> list(int start,int count) {
		List<Record> records = new ArrayList<Record>();
		
		String sql = "select * from record order by id limit ?,?";
		
		try(Connection c= getConnection();PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1,start);
			ps.setInt(2,count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Record record = new Record();
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String date = rs.getString(3);
				String info = rs.getString(4);
				record.id = id;
				record.name = name;
				//对时间和日期进行处理
				String[] dates;
				dates = date.split("\\.");
				date = dates[0] + "月" + dates[1] +"日" + dates[2] + "时" + dates[3] + "分";
				record.date = date;
				if(info.equals("0")) {
					info = "超过半小时未服药";
				}
				else
					info = "已服药";
				record.info = info;
				records.add(record);	
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return records;
	}
}



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
	
	/**�������ݿ������ݵ�����*/
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
	
	/**��record���м�������*/
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
				//�ô����record.id����Ϊ���ݿ�id
				record.id = id; 
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**����record������������*/
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
	
	/**ɾ��*/
	public void delete(int id) {
		try(Connection c= getConnection();Statement s = c.createStatement();) {
			String sql = "delete from record where id = " + id;
			
			//����sql���
			s.execute(sql);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**����id����*/
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
	
	/**�鿴���ݿ�ȫ������*/
	public List<Record> list() {
		return list(0,Short.MAX_VALUE);
	}
	
	/**��ҳ��ʾ*/
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
				//��ʱ������ڽ��д���
				String[] dates;
				dates = date.split("\\.");
				date = dates[0] + "��" + dates[1] +"��" + dates[2] + "ʱ" + dates[3] + "��";
				record.date = date;
				if(info.equals("0")) {
					info = "������Сʱδ��ҩ";
				}
				else
					info = "�ѷ�ҩ";
				record.info = info;
				records.add(record);	
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return records;
	}
}



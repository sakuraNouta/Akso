package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.User;

public class UserDAO {
	public UserDAO() {
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
			String sql = "select count(*) from user";
			
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
	
	/**��user���м�������*/
	public void add(User user) {
		String sql = "insert into user values(null,?,?)";
				
		try(Connection c = getConnection();PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1,user.name);
			ps.setString(2,user.password);
			
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) { 
				int id = rs.getInt(1);
				//�ô����user.id����Ϊ���ݿ�id
				user.id = id; 
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**����user������������*/
	public void update(User user) {
		String sql = "update user set name = ?,password = ? where id = ?";
		try(Connection c = getConnection();PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1,user.name);
			ps.setString(2,user.password);
			ps.setInt(3,user.id);
			
			ps.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**ɾ��*/
	public void delete(int id) {
		try(Connection c= getConnection();Statement s = c.createStatement();) {
			String sql = "delete from user where id = " + id;
			
			//����sql���
			s.execute(sql);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**����id����*/
	public User get(int id) {
		User user = null;
		
		try(Connection c = getConnection();Statement s= c.createStatement();) {
			String sql = "select * from user where id = " + id;
			
			ResultSet rs = s.executeQuery(sql);
			
			if(rs.next()) {
				user = new User();
				String name = rs.getString(2);
				String password = rs.getString(3);
				user.name = name;
				user.password = password;
				user.id = id;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**�ж�name�Ƿ��Ѵ���*/
	public boolean exist(String name) {
		boolean exist = false;
		
		try(Connection c = getConnection();Statement s= c.createStatement();) {
			String sql = "select * from user where name = \"" +name+ "\"";
			
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				exist = true;
			}
			else
				exist = false;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}
	
	/**����name���Ҷ�Ӧ��password,�������ڴ�name�򷵻�0*/
	public String get(String name) {
		String password = null;
		
		try(Connection c = getConnection();Statement s= c.createStatement();) {
			String sql = "select * from user where name = \"" +name+ "\"";
			
			ResultSet rs = s.executeQuery(sql);

			if(rs.next()) {
				password = rs.getString(3);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return password;
	}
	
	/**�鿴���ݿ�ȫ������*/
	public List<User> list() {
		return list(0,Short.MAX_VALUE);
	}
	
	/**��ҳ��ʾ*/
	public List<User> list(int start,int count) {
		List<User> users = new ArrayList<User>();
		
		String sql = "select * from user order by id desc limit ?,?";
		
		try(Connection c= getConnection();PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1,start);
			ps.setInt(2,count);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				user.id = id;
				user.name = name;
				user.password = password;
				users.add(user);	
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
}


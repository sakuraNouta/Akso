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
	
	/**返回数据库中数据的总数*/
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
	
	/**向user表中加入数据*/
	public void add(User user) {
		String sql = "insert into user values(null,?,?)";
				
		try(Connection c = getConnection();PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1,user.name);
			ps.setString(2,user.password);
			
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) { 
				int id = rs.getInt(1);
				//置传入的user.id变量为数据库id
				user.id = id; 
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**更新user表中已有数据*/
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
	
	/**删除*/
	public void delete(int id) {
		try(Connection c= getConnection();Statement s = c.createStatement();) {
			String sql = "delete from user where id = " + id;
			
			//处理sql语句
			s.execute(sql);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**根据id查找*/
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
	
	/**判断name是否已存在*/
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
	
	/**根据name查找对应的password,若不存在此name则返回0*/
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
	
	/**查看数据库全部数据*/
	public List<User> list() {
		return list(0,Short.MAX_VALUE);
	}
	
	/**分页显示*/
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


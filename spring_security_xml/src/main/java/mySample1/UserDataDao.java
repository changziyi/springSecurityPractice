package mySample1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("userDataDao")
public class UserDataDao {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String URL = "jdbc:sqlserver://localhost:1433;" + "databaseName=springSecurityPractice";
//	String connectionUrl = "jdbc:sqlserver://localhost:1433;" +  
//			   "databaseName=springSecurityPractice;user=MyUserName;password=*****;";  
	// Database credentials
	static final String USER = "sa";
	static final String PASSWORD = "sa123456";

	public List<UserData> selectAll() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs =null;
		List<UserData> udL = new ArrayList<UserData>();
		try {
			// register JDBC driver
			Class.forName(JDBC_DRIVER);

			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT userId,userName,userPassword from userData";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				UserData ud = new UserData();
				ud.setUserId(StringUtils.trimToNull(rs.getString("userId")));
				ud.setUserName(StringUtils.trimToNull(rs.getString("userName")));
				ud.setUserPassword(StringUtils.trimToNull(rs.getString("userPassword")));
				udL.add(ud);
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return udL;
	}

	public UserData selectById(String id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		UserData ud = new UserData();
		try {
			// register JDBC driver
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Creating statement...");
			String sql;
			sql = "SELECT userId,userName,userPassword from userData where userId =?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ud.setUserId(StringUtils.trimToNull(rs.getString("userId")));
				ud.setUserName(StringUtils.trimToNull(rs.getString("userName")));
				ud.setUserPassword(StringUtils.trimToNull(rs.getString("userPassword")));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return ud;
	}

	public static void main(String[] args) throws SQLException {
		UserDataDao udd = new UserDataDao();
//		List<UserData> list = udd.selectAll();
		UserData ud =udd.selectById("01");
//		for (UserData ud : list) {
			System.out.println(ud.getUserId()+ud.getUserName()+ud.getUserPassword());
//		}
	}
}

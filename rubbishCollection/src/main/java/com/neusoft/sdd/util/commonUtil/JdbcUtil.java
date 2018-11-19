package com.neusoft.sdd.util.commonUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {
	
	private static String url;
	private static String user;
	private static String password;

	private JdbcUtil() {
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
//			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			
			Properties dbProp= PropertiesUtil.loadProperties("conf/dbType.properties");
			url = dbProp.getProperty("jdbc_url");
			user = dbProp.getProperty("jdbc_username");
			password = dbProp.getProperty("jdbc_password");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public static void free(ResultSet resultset, Statement st, Connection conn) {
		// 6.释放资源
		try {
			if (resultset != null)
				resultset.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}

	}
}

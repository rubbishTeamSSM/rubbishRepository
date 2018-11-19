package com.neusoft.sdd.util.wx;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;

public class DBHelp {
	public static Connection getConn() {
		Connection conn = null;
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			// String url = PropertiesUtils.getValue("mysqlUrl");
			// String user = PropertiesUtils.getValue("mysqlUser");
			// String password = PropertiesUtils.getValue("mysqlPwd");
			// conn = DriverManager.getConnection(url, user, password);

			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext
					.lookup("git_jeeshopDataSourceJNDI");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Connection getQueryConn() {
		Connection conn = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext
					.lookup("git_jeeshopDataSourceJNDI");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeObject(Object o) {
		if (o != null) {
			if (o instanceof Connection) {
				try {
					((Connection) o).close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (o instanceof Statement) {
				try {
					((Statement) o).close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (o instanceof PreparedStatement) {
				try {
					((PreparedStatement) o).close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (o instanceof ResultSet) {
				try {
					((ResultSet) o).close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static int getUniqueResult(String sql) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelp.getConn();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelp.closeObject(rs);
			DBHelp.closeObject(pst);
			DBHelp.closeObject(conn);
		}
		return result;
	}

	public static int doDML(String sql, Object[] objects) {
		Connection conn = null;
		PreparedStatement pst = null;
		int flag = 0;
		try {
			conn = getConn();
			pst = conn.prepareStatement(sql);
			if (objects != null) {
				for (int i = 0; i < objects.length; i++) {
					pst.setObject(i + 1, objects[i]);
				}
			}
			flag = pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelp.closeObject(pst);
			DBHelp.closeObject(conn);
		}
		return flag;
	}

	public static List<Map<String, String>> doQuery(String sql, String[] args,
			String[] columns) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rst = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			conn = getQueryConn();
			pst = conn.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					pst.setString(i + 1, args[i]);
				}
			}
			rst = pst.executeQuery();
			while (rst.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < columns.length; i++) {
					map.put(columns[i], rst.getString(columns[i]));
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelp.closeObject(rst);
			DBHelp.closeObject(pst);
			DBHelp.closeObject(conn);
		}
		return list;
	}

	public static <T> List<T> doQuery(String sql, Class<T> t) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rst = null;

		Object tempStr = null;
		List<T> list = new ArrayList<T>();
		try {
			conn = getQueryConn();
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			while (rst.next()) {
				T obj = t.newInstance();
				Field[] fields = obj.getClass().getDeclaredFields();
				for (Field field : fields) {
					try {
						tempStr = rst.getObject(field.getName());
					} catch (SQLException e) {
						tempStr = "";
					}

					field.setAccessible(true);

					BeanUtils.setProperty(obj, field.getName(), tempStr);
				}

				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelp.closeObject(rst);
			DBHelp.closeObject(pst);
			DBHelp.closeObject(conn);
		}
		return list;
	}

	public static int queryCount(String sql) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rst = null;
		int result = 0;
		try {
			conn = getQueryConn();
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			if (rst.next()) {
				result = rst.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelp.closeObject(rst);
			DBHelp.closeObject(pst);
			DBHelp.closeObject(conn);
		}
		return result;
	}

	public static <T> T queryOne(String sql, Class<T> t) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rst = null;
		T obj = null;
		String tempStr = null;
		try {
			obj = t.newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = getQueryConn();
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			if (rst.next()) {
				Field[] fields = obj.getClass().getDeclaredFields();
				for (Field field : fields) {
					if ("serialVersionUID".equalsIgnoreCase(field.getName())) {
						continue;
					}

					try {
						tempStr = rst.getString(field.getName());
					} catch (SQLException e) {
						tempStr = "";
					}

					BeanUtils.setProperty(obj, field.getName(), tempStr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelp.closeObject(rst);
			DBHelp.closeObject(pst);
			DBHelp.closeObject(conn);
		}
		return obj;
	}

	public static void addBatch(String sql, List<String[]> argsList) {
		Connection conn = null;
		try {
			conn = getConn();
			conn.setAutoCommit(false);
			PreparedStatement prest = conn.prepareStatement(sql);

			for (String[] strings : argsList) {
				for (int i = 0; i < strings.length; i++) {
					prest.setString(i + 1, strings[i]);
				}
				prest.addBatch();
			}

			prest.executeBatch();
			conn.commit();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static <T> T queryOne(String sql, List<Object> list, Class<T> t) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rst = null;
		T obj = null;
		String tempStr = null;
		try {
			obj = t.newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = getQueryConn();
			pst = conn.prepareStatement(sql);

			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					pst.setObject(i + 1, list.get(i));
				}
			}

			rst = pst.executeQuery();
			if (rst.next()) {
				Field[] fields = obj.getClass().getDeclaredFields();
				for (Field field : fields) {
					if ("serialVersionUID".equalsIgnoreCase(field.getName())) {
						continue;
					}

					try {
						tempStr = rst.getString(field.getName());
					} catch (SQLException e) {
						tempStr = "";
					}

					BeanUtils.setProperty(obj, field.getName(), tempStr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelp.closeObject(rst);
			DBHelp.closeObject(pst);
			DBHelp.closeObject(conn);
		}
		return obj;
	}
}

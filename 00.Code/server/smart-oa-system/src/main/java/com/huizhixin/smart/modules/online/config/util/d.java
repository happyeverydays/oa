package com.huizhixin.smart.modules.online.config.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.hibernate.Session;
import com.huizhixin.smart.common.util.SpringContextUtils;
import com.huizhixin.smart.modules.online.config.exception.DBException;
import com.huizhixin.smart.modules.online.config.service.DbTableHandleI;
import com.huizhixin.smart.modules.online.config.service.a.a;
import com.huizhixin.smart.modules.online.config.service.a.b;
import com.huizhixin.smart.modules.online.config.service.a.c;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class d {
	private static final Logger f = LoggerFactory.getLogger(d.class);
	public static final String a = "MYSQL";
	public static final String b = "ORACLE";
	public static final String c = "POSTGRESQL";
	public static final String d = "SQLSERVER";
	public static String e = "";

	public static DbTableHandleI getTableHandle() throws SQLException, DBException {
		Object var0 = null;
		String var1 = getDatabaseType();
		byte var3 = -1;
		switch (var1.hashCode()) {
			case -1955532418 :
				if (var1.equals("ORACLE")) {
					var3 = 1;
				}
				break;
			case -1620389036 :
				if (var1.equals("POSTGRESQL")) {
					var3 = 3;
				}
				break;
			case 73844866 :
				if (var1.equals("MYSQL")) {
					var3 = 0;
				}
				break;
			case 912124529 :
				if (var1.equals("SQLSERVER")) {
					var3 = 2;
				}
		}

		switch (var3) {
			case 0 :
				var0 = new a();
				break;
			case 1 :
				var0 = new b();
				break;
			case 2 :
				var0 = new com.huizhixin.smart.modules.online.config.service.a.d();
				break;
			case 3 :
				var0 = new c();
		}

		return (DbTableHandleI) var0;
	}

	public static Connection getConnection() throws SQLException {
		DataSource var0 = (DataSource) SpringContextUtils.getApplicationContext().getBean(DataSource.class);
		return var0.getConnection();
	}

	public static String getDatabaseType() throws SQLException, DBException {
		DataSource var0 = (DataSource) SpringContextUtils.getApplicationContext().getBean(DataSource.class);
		return a(var0);
	}

	public static String a(DataSource var0) throws SQLException, DBException {
		if ("".equals(e)) {
			Connection var1 = var0.getConnection();

			try {
				DatabaseMetaData var2 = var1.getMetaData();
				String var3 = var2.getDatabaseProductName().toLowerCase();
				if (var3.indexOf("mysql") >= 0) {
					e = "MYSQL";
				} else if (var3.indexOf("oracle") >= 0) {
					e = "ORACLE";
				} else if (var3.indexOf("sqlserver") < 0 && var3.indexOf("sql server") < 0) {
					if (var3.indexOf("postgresql") < 0) {
						throw new DBException("数据库类型:[" + var3 + "]不识别!");
					}

					e = "POSTGRESQL";
				} else {
					e = "SQLSERVER";
				}
			} catch (Exception var7) {
				f.error(var7.getMessage(), var7);
			} finally {
				var1.close();
			}
		}

		return e;
	}

	public static String a(Connection var0) throws SQLException, DBException {
		if ("".equals(e)) {
			DatabaseMetaData var1 = var0.getMetaData();
			String var2 = var1.getDatabaseProductName().toLowerCase();
			if (var2.indexOf("mysql") >= 0) {
				e = "MYSQL";
			} else if (var2.indexOf("oracle") >= 0) {
				e = "ORACLE";
			} else if (var2.indexOf("sqlserver") < 0 && var2.indexOf("sql server") < 0) {
				if (var2.indexOf("postgresql") < 0) {
					throw new DBException("数据库类型:[" + var2 + "]不识别!");
				}

				e = "POSTGRESQL";
			} else {
				e = "SQLSERVER";
			}
		}

		return e;
	}

	public static String a(Session var0) throws SQLException, DBException {
		return getDatabaseType();
	}

	public static String a(String var0, String var1) {
		byte var3 = -1;
		switch (var0.hashCode()) {
			case -1955532418 :
				if (var0.equals("ORACLE")) {
					var3 = 0;
				}
				break;
			case -1620389036 :
				if (var0.equals("POSTGRESQL")) {
					var3 = 1;
				}
		}

		switch (var3) {
			case 0 :
				return var0.toUpperCase();
			case 1 :
				return var0.toLowerCase();
			default :
				return var0;
		}
	}

	public static Boolean a(String var0) {
		Connection var1 = null;
		ResultSet var2 = null;

		Boolean var7;
		try {
			String[] var3 = new String[]{"TABLE"};
			var1 = getConnection();
			DatabaseMetaData var4 = var1.getMetaData();
			String var5 = var4.getDatabaseProductName().toUpperCase();
			String var6 = a(var0, var5);
			var2 = var4.getTables((String) null, (String) null, var6, var3);
			if (var2.next()) {
				f.info("数据库表：【" + var0 + "】已存在");
				var7 = true;
				return var7;
			}

			var7 = false;
		} catch (SQLException var17) {
			throw new RuntimeException();
		} finally {
			try {
				if (var2 != null) {
					var2.close();
				}

				if (var1 != null) {
					var1.close();
				}
			} catch (SQLException var16) {
				f.error(var16.getMessage(), var16);
			}

		}

		return var7;
	}

	public static Map<String, Object> a(List<Map<String, Object>> var0) {
		HashMap var1 = new HashMap();

		for (int var2 = 0; var2 < var0.size(); ++var2) {
			var1.put(((Map) var0.get(var2)).get("column_name").toString(), var0.get(var2));
		}

		return var1;
	}

	public static String getDialect() throws SQLException, DBException {
		String var0 = getDatabaseType();
		return b(var0);
	}

	public static String b(String var0) throws SQLException, DBException {
		String var1 = "org.hibernate.dialect.MySQL5InnoDBDialect";
		byte var3 = -1;
		switch (var0.hashCode()) {
			case -1955532418 :
				if (var0.equals("ORACLE")) {
					var3 = 2;
				}
				break;
			case -1620389036 :
				if (var0.equals("POSTGRESQL")) {
					var3 = 1;
				}
				break;
			case 912124529 :
				if (var0.equals("SQLSERVER")) {
					var3 = 0;
				}
		}

		switch (var3) {
			case 0 :
				var1 = "org.hibernate.dialect.SQLServerDialect";
				break;
			case 1 :
				var1 = "org.hibernate.dialect.PostgreSQLDialect";
				break;
			case 2 :
				var1 = "org.hibernate.dialect.OracleDialect";
		}

		return var1;
	}

	public static String c(String var0) {
		return var0;
	}
}
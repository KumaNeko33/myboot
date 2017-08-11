package com.shuai.test.controller;

//import net.showcoo.CodeUtils;
//import net.showcoo.CommonAttributes;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.sql.*;
import java.util.*;

/**
 * 订正程序基类,封装了JDBC通用读、写操作
 * 
 * @author zengrijia
 *
 */
public abstract class BaseController {

	private static String URL;
	private static String USERNAME;
	private static String PASSWORD;
	protected static Boolean RUNING = false;
	protected static String USER_ID = "11460d0e2edd23fd2581bace905e05e4";
	protected static List<String> messages = new ArrayList<String>();

//	static {
//		try {
//			File shopXmlFile = new ClassPathResource(CommonAttributes.SHOP_PROPERTIES_PATH).getFile();
//			InputStream inputStream = new FileInputStream(shopXmlFile);
//			Properties props = new Properties();
//			props.load(inputStream);
//			URL = props.getProperty("jdbc.url");
//			USERNAME = props.getProperty("jdbc.username");
//			PASSWORD = props.getProperty("jdbc.password");
//			Class.forName(props.getProperty("jdbc.driver"));
//		} catch (IOException e) {
//			throw new IllegalArgumentException(e);
//		} catch (ClassNotFoundException e) {
//			throw new IllegalArgumentException(e);
//		}
//	}

	/**
	 * 获取MySql数据库链接对象
	 * 
	 * @return {@link Connection} 数据库链接对象
	 */
	protected Connection getConnection() {
		try {
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 获取某个数据库所有包含id字段的表信息
	 * 
	 * @param tableSchema
	 *            数据库名字
	 * @return 表信息
	 */
	protected List<String> getTableNames(String tableSchema) {
		Assert.notNull(tableSchema);
		StringBuilder build = new StringBuilder();
		build.append("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.COLUMNS ");
		build.append("WHERE TABLE_SCHEMA = '" + tableSchema + "' ");
		build.append("AND COLUMN_NAME = 'id' ");
		build.append("ORDER BY TABLE_NAME ASC");
		try {
			Set<String> tableNames = new HashSet<String>();
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(build.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				tableNames.add(rs.getString("TABLE_NAME"));
			}
			rs.close();
			pstmt.close();
			conn.close();
			return new ArrayList<String>(tableNames);
		} catch (SQLException e) {
			messages.add("程序出现异常, 请稍后再试...");
		}
		return Collections.emptyList();
	}

	/**
	 * 获取所有更新语句
	 * 
	 * @param tableName
	 *            表名
	 * @return 更新语句列表
	 */
//	protected List<String> getUpdateSqlList(String tableName) {
//		try {
//			String sql = "SELECT id FROM " + tableName + " WHERE uuid is null";
//			List<String> updateSqlList = new ArrayList<String>();
//			Connection conn = getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next() && RUNING) {
//				StringBuilder build = new StringBuilder();
//				build.append("UPDATE " + tableName + " ");
//				build.append("SET uuid = '" + CodeUtils.getUuid() + "' ");
//				build.append("WHERE id = " + rs.getLong("id"));
//				updateSqlList.add(build.toString());
//			}
//			rs.close();
//			pstmt.close();
//			conn.close();
//			return updateSqlList;
//		} catch (SQLException e) {
//			messages.add("程序出现异常, 请稍后再试...");
//		}
//		return Collections.emptyList();
//	}

	/**
	 * 批量执行新增、修改、删除操作
	 * 
	 * @param updateSqlList
	 */
	protected void executeBatchUpdate(List<String> updateSqlList) {
		try {
			Connection conn = getConnection();
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			int batchNum = 1000;
			for (int i = 0; i < updateSqlList.size(); i++) {
				stmt.addBatch(updateSqlList.get(i));
				if (i % batchNum == 0 && RUNING) {
					stmt.executeBatch();
					conn.commit();
				}
			}
			stmt.executeBatch();
			conn.commit();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			messages.add("程序出现异常, 请稍后再试...");
		}
	}

	protected List<String> buildAddScript(List<String> tableNames, String fieldName) {
		if (!CollectionUtils.isEmpty(tableNames)) {
			List<String> scriptList = new ArrayList<String>();
			for (String tableName : tableNames) {
				scriptList.add(buildAddScript(tableName, fieldName));
			}
			return scriptList;
		}
		return Collections.emptyList();
	}

	protected List<String> buildDropScript(List<String> tableNames, String fieldName) {
		if (!CollectionUtils.isEmpty(tableNames)) {
			List<String> scriptList = new ArrayList<String>();
			for (String tableName : tableNames) {
				scriptList.add(buildDropScript(tableName, fieldName));
			}
			return scriptList;
		}
		return Collections.emptyList();
	}

	protected String buildAddScript(String tableName, String fieldName) {
		StringBuilder build = new StringBuilder();
		build.append("ALTER TABLE `" + tableName + "` ");
		build.append("ADD COLUMN `" + fieldName + "` varchar(32) ");
		build.append("CHARACTER SET utf8 NULL COMMENT '特征码';");
		return build.toString();
	}

	protected String buildDropScript(String tableName, String fieldName) {
		StringBuilder build = new StringBuilder();
		build.append("ALTER TABLE `" + tableName + "` ");
		build.append("DROP COLUMN `" + fieldName + "`;");
		return build.toString();
	}
}
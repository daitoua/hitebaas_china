package com.hitebaas.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.hitebaas.entity.HiteBassConstant;


public class SQLiteHelper {
	public static Lock lock = new ReentrantLock(); 
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		String path = DataUtils.getRelativePath(HiteBassConstant.DB_TSC_PATH_1);
		System.out.println("sqlLite配置+" + path);
		DataUtils.findFile(path);
		
		Class.forName("org.sqlite.JDBC");
		String path2 = DataUtils.getRelativePath(HiteBassConstant.DB_TSC_PATH);
		System.out.println("sqlLite配置2+" + path2);

		Connection connection = DriverManager.getConnection("jdbc:sqlite:" + path2);
		lock.lock();
		return connection;
	}
	
	public static void close(Connection connection) {
		lock.unlock();
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
 
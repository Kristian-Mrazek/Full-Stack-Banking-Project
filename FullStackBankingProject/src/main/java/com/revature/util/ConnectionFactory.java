package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private static final String URL = "jdbc:postgresql://localhost/postgres";
	private static final String USERNAME = "im_a_user";
	private static final String PASSWORD = "Y0uC4n'tGu355Th15!";
	
	private static Connection conn;
	
	public static Connection getConnection() {
		
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}

}

package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.EmployeeAccount;
import com.revature.util.ConnectionFactory;

public class EmployeeDaoImpl implements EmployeeDao {
	
	/**
	 * Inserts a new employee account into the bank database.
	 * @param	account: the account to be added to the database
	 * @return	true if the insertion is successful, false if an error is encountered
	 */
	@Override
	public boolean insertEmployeeAccount(EmployeeAccount account) {
		
		String sql = "INSERT INTO employee_accounts (user_name, pass_word) VALUES (?, ?);";
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, account.getUsername());
			ps.setString(2, account.getPassword());
			
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Finds the employee account associated with a given (unique) username.
	 * @param 	the username used to find the account
	 * @return	the employee account associated with the given username
	 */
	@Override
	public EmployeeAccount selectEmployeeAccountByUsername(String username) {
		
		String sql = "SELECT * FROM employee_accounts WHERE user_name = ?;";
		EmployeeAccount account = null;
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				account = new EmployeeAccount(rs.getInt("employee_id"),
						rs.getString("user_name"),
						rs.getString("pass_word"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}

}

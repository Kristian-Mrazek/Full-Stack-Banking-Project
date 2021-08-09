package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.CustomerAccount;
import com.revature.util.ConnectionFactory;

public class CustomerDaoImpl implements CustomerDao {
	
	/**
	 * Inserts a new customer account into the bank database.
	 * @param	account: the account to be added to the database
	 * @return	true if the insertion is successful, false if an error is encountered
	 */
	@Override
	public boolean insertCustomerAccount(CustomerAccount account) {
		
		String sql = "INSERT INTO customer_accounts (user_name, pass_word) VALUES (?, ?);";
		
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
	 * Finds the customer account associated with a given (unique) username.
	 * @param 	the username used to find the account
	 * @return	the customer account associated with the given username
	 */
	@Override
	public CustomerAccount selectCustomerAccountByUsername(String username) {
		
		String sql = "SELECT * FROM customer_accounts WHERE user_name = ?;";
		CustomerAccount account = null;
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				account = new CustomerAccount(rs.getInt("customer_id"),
						rs.getString("user_name"),
						rs.getString("pass_word"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}
	
	/**
	 * Update the balance of an existing bank account in the database.
	 * @param	account: the bank account to be updated
	 */
	@Override
	public void updateCustomerAccount(CustomerAccount account) {
		
		String sql = "UPDATE customer_accounts SET user_name = ?, pass_word = ? WHERE customer_id = ?;";
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, account.getUsername());
			ps.setString(2, account.getPassword());
			ps.setInt(3, account.getId());
			
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Remove an existing customer account and all associated bank accounts from the database.
	 * @param	account: the customer account to be removed
	 */
	@Override
	public void deleteCustomerAccount(CustomerAccount account) {
		
		String sql = "DELETE FROM bank WHERE customer_id = ?;"
				+ "DELETE FROM customer_accounts WHERE customer_id = ?;";
		
		try (Connection conn = ConnectionFactory.getConnection();) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, account.getId());
			ps.setInt(2, account.getId());
			
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}

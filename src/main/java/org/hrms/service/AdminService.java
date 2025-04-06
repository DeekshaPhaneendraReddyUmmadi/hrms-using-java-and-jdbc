package org.hrms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hrms.dbconfig.DbConnection;
import org.hrms.model.Admin;

public class AdminService {

	public static boolean verify(Admin admin) {
		Admin dbInfo = null; // Initialize to null
		try (Connection con = DbConnection.getConnection();
				PreparedStatement ps = con
						.prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?")) {
			ps.setString(1, admin.getUsername());
			ps.setString(2, admin.getPassword());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) { // Check if there is a result
					dbInfo = new Admin();
					dbInfo.setUsername(rs.getString("username")); // Get the actual username from the ResultSet
					dbInfo.setPassword(rs.getString("password")); // Get the actual password from the ResultSet
				}
			}
		} catch (Exception ex) {
			System.err.println("Error while retrieving admin records: " + ex.getMessage());
			ex.printStackTrace();
		}

		return dbInfo != null && admin.getUsername().equals(dbInfo.getUsername())
				&& admin.getPassword().equals(dbInfo.getPassword());
	}

}
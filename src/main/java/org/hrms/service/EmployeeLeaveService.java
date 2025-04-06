package org.hrms.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.hrms.dbconfig.DbConnection;
import org.hrms.model.Leave;

public class EmployeeLeaveService {

	public static boolean saveLeave(Leave l) {
		String sql = "INSERT INTO employeeleave (employee_id, leave_type, start_date, end_date, leave_status) VALUES (?, ?, ?, ?, 'Pending')";

		// Validate the Leave object
		if (l == null || l.getEmployeeId() <= 0 || l.getLeaveType() == null || l.getStartDate() == null
				|| l.getEndDate() == null) {
			System.out.println("Invalid leave details provided.");
			return false; // Indicate failure
		}

		// Ensure start date is before end date
		if (l.getStartDate().after(l.getEndDate())) {
			System.out.println("Start date must be before end date.");
			return false; // Indicate failure
		}

		try (Connection con = DbConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, l.getEmployeeId());
			pstmt.setString(2, l.getLeaveType());
			pstmt.setDate(3, (Date) l.getStartDate());
			pstmt.setDate(4, (Date) l.getEndDate());

			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Leave record saved successfully.");
				return true; // Indicate success
			} else {
				System.out.println("Failed to save leave record.");
				return false; // Indicate failure
			}
		} catch (SQLException e) {
			// Use a logging framework in a real application
			System.err.println("Error saving leave record: " + e.getMessage());
			return false; // Indicate failure
		}
	}

	// Method to view all leave records
	public static List<Leave> viewAllLeavesBasedOnStatus(String status) {
		List<Leave> leaves = new ArrayList<>();
		String sql = "SELECT * FROM employeeleave WHERE leave_status = ?";

		try (Connection con = DbConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, status);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					leaves.add(new Leave(
							rs.getInt("employee_id"),
							rs.getString("leave_type"),
							rs.getDate("start_date"),
							rs.getDate("end_date"),
							rs.getString("leave_status")));
				}
			}
		} catch (SQLException e) {
			// Consider using a logging framework instead of printStackTrace
			e.printStackTrace();
		}
		return leaves;
	}

	public static List<Leave> viewAllLeavesBasedOnEmployeeId(int id) {
		List<Leave> leaves = new ArrayList<>();
		String sql = "SELECT * FROM employeeleave WHERE employee_id = ?";

		try (Connection con = DbConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					leaves.add(new Leave(
							rs.getInt("employee_id"),
							rs.getString("leave_type"),
							rs.getDate("start_date"),
							rs.getDate("end_date"),
							rs.getString("leave_status")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return leaves;
	}

	public static List<Leave> viewAllLeaves() {
		List<Leave> leaves = new ArrayList<>();
		String sql = "SELECT * FROM employeeleave";

		try (Connection con = DbConnection.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {

				leaves.add(new Leave(
						rs.getInt("employee_id"),
						rs.getString("leave_type"),
						rs.getDate("start_date"),
						rs.getDate("end_date"),
						rs.getString("leave_status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return leaves;
	}

	// Method to update the status of a leave record
	public static void updateLeaveStatus(int employeeId, String newStatus) {
		String sql = "UPDATE employeeleave SET leave_status = ? WHERE employee_id = ?";

		try (Connection con = DbConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, newStatus);
			pstmt.setInt(2, employeeId);
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Leave status updated successfully.");
			} else {
				System.out.println("Leave ID not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package org.hrms.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hrms.dbconfig.DbConnection;
import org.hrms.model.Attendance;

public class AttendanceService {
	public static int save(Attendance a) {
		int status = 0;
		try (Connection con = DbConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO attendance(employee_id, attendance_date, status) VALUES (?, ?, ?)")) {

			Date sqlDate = new java.sql.Date(a.getAttendanceDate().getTime());

			ps.setInt(1, a.getEmployeeId());
			ps.setDate(2, sqlDate);
			ps.setString(3, a.getStatus().name());
			status = ps.executeUpdate();
		} catch (Exception ex) {
			System.err.println("Error while inserting details: " + ex.getMessage());
			ex.printStackTrace();
		}
		return status;
	}

	public static List<Attendance> getAttendanceList() {
		List<Attendance> attendanceList = new ArrayList<>();

		try (Connection con = DbConnection.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM attendance")) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Attendance attendance = new Attendance();
					attendance.setAttendanceId(rs.getInt("attendance_id"));
					attendance.setEmployeeId(rs.getInt("employee_id"));
					attendance.setAttendanceDate(rs.getDate("attendance_date")); // java.sql.Date
					attendance.setStatus(Attendance.AttendanceStatus.valueOf(rs.getString("status"))); // Convert string
																										// to enum
					attendanceList.add(attendance);
				}
			}
		} catch (Exception ex) {
			System.err.println("Error while retrieving attendance records: " + ex.getMessage());
			ex.printStackTrace();
		}

		return attendanceList; // Return the list of attendance records
	}

	public static List<Attendance> getAttendanceListByDate(java.util.Date date, int employeeId) {
		List<Attendance> attendanceList = new ArrayList<>();

		try (Connection con = DbConnection.getConnection();
				PreparedStatement ps = con
						.prepareStatement("SELECT * FROM attendance WHERE attendance_date = ? and employee_id = ?")) {
			ps.setDate(1, (java.sql.Date) date);
			ps.setInt(2, employeeId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Attendance attendance = new Attendance();
					attendance.setAttendanceId(rs.getInt("attendance_id"));
					attendance.setEmployeeId(rs.getInt("employee_id"));
					attendance.setAttendanceDate(rs.getDate("attendance_date")); // java.sql.Date
					attendance.setStatus(Attendance.AttendanceStatus.valueOf(rs.getString("status"))); // Convert string
																										// to enum
					attendanceList.add(attendance);
				}
			}
		} catch (Exception ex) {
			System.err.println("Error while retrieving attendance records: " + ex.getMessage());
			ex.printStackTrace();
		}

		return attendanceList; // Return the list of attendance records
	}

	public static List<Attendance> getAttendanceListByStatus(String status, int employeeId) {
		List<Attendance> attendanceList = new ArrayList<>();

		try (Connection con = DbConnection.getConnection();
				PreparedStatement ps = con
						.prepareStatement("SELECT * FROM attendance WHERE status = ? and employee_id = ?")) {
			ps.setString(1, status);
			ps.setInt(2, employeeId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Attendance attendance = new Attendance();
					attendance.setAttendanceId(rs.getInt("attendance_id"));
					attendance.setEmployeeId(rs.getInt("employee_id"));
					attendance.setAttendanceDate(rs.getDate("attendance_date")); // java.sql.Date
					attendance.setStatus(Attendance.AttendanceStatus.valueOf(rs.getString("status"))); // Convert string
																										// to enum
					attendanceList.add(attendance);
				}
			}
		} catch (Exception ex) {
			System.err.println("Error while retrieving attendance records: " + ex.getMessage());
			ex.printStackTrace();
		}

		return attendanceList; // Return the list of attendance records
	}
}

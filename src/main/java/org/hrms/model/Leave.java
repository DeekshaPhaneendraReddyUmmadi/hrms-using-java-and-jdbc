package org.hrms.model;

import java.util.Date;

public class Leave {
	private int employeeId;
	private String leaveType;
	private Date startDate;
	private Date endDate;
	private String leave_status;

	public Leave() {
		super();
	}

	public Leave(int employeeId, String leaveType, Date date, Date date2) {
		super();
		this.employeeId = employeeId;
		this.leaveType = leaveType;
		this.startDate = (Date) date;
		this.endDate = (Date) date2;
	}

	public Leave(int employeeId, String leaveType, Date startDate, Date endDate, String leave_status) {
		super();
		this.employeeId = employeeId;
		this.leaveType = leaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leave_status = leave_status;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLeave_status() {
		return leave_status;
	}

	public void setLeave_status(String leave_status) {
		this.leave_status = leave_status;
	}

	@Override
	public String toString() {
		return "Leave [employeeId=" + employeeId + ", leaveType=" + leaveType + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", leave_status=" + leave_status + "]";
	}

}

package org.hrms.model;

import java.util.Date;

public class Attendance {

    public Attendance() {
        super();
    }

    public Attendance(int employeeId, Date attendanceDate, AttendanceStatus status) {
        super();
        this.employeeId = employeeId;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    private int attendanceId;
    private int employeeId;
    private Date attendanceDate;
    private AttendanceStatus status;

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public enum AttendanceStatus {
        PRESENT,
        ABSENT,
        LEAVE
    }

    @Override
    public String toString() {
        return "Attendance [attendanceId=" + attendanceId + ", employeeId=" + employeeId + ", attendanceDate="
                + attendanceDate + ", status=" + status + "]";
    }

}

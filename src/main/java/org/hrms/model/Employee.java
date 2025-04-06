package org.hrms.model;

public class Employee {
    private int employee_id;
    private String name;
    private String designation;
    private String department;
    private String contact_info;

    // Constructor
    public Employee() {
    }

    public Employee(int employee_id, String name, String designation, String department, String contact_info) {
        this.name = name;
        this.employee_id = employee_id;
        this.designation = designation;
        this.department = department;
        this.contact_info = contact_info;
    }

    // Public method to get employee details
    @Override
    public String toString() {
        return "Employee: [Name: " + name +
                ", Employee ID: " + employee_id +
                ", Designation: " + designation +
                ", Department: " + department +
                ", Contact Info: " + contact_info + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeId() {
        return employee_id;
    }

    public void setEmployeeId(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getContactInfo() {
        return contact_info;
    }

    public void setContactInfo(String contact_info) {
        this.contact_info = contact_info;
    }
}

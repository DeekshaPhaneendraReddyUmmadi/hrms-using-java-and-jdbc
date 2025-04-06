package org.hrms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hrms.dbconfig.DbConnection;
import org.hrms.model.Employee;

public class EmployeeService {

    public static int save(Employee e) {
        int status = 0;
        Employee emp = getEmployeeById(e.getEmployeeId());
        if (emp != null) {
            System.out.println("Employee details exist in the database.");
            return status;
        } else {
            try (Connection con = DbConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO Employee(employee_id, name, designation, department, contact_info) VALUES (?, ?, ?, ?, ?)")) {

                ps.setInt(1, e.getEmployeeId());
                ps.setString(2, e.getName());
                ps.setString(3, e.getDesignation());
                ps.setString(4, e.getDepartment());
                ps.setString(5, e.getContactInfo());
                status = ps.executeUpdate();
            } catch (Exception ex) {
                System.err.println("Error while inserting details: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return status;
    }

    public static int update(Employee e) {
        int status = 0;
        Employee emp = getEmployeeById(e.getEmployeeId());
        if (emp == null) {
            System.out.println("Employee details do not exist in the database.");
            return status;
        } else {
            try (Connection con = DbConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement(
                            "UPDATE Employee SET name = ?, designation = ?, department = ?, contact_info = ? WHERE employee_id = ?")) {

                ps.setString(1, e.getName());
                ps.setString(2, e.getDesignation());
                ps.setString(3, e.getDepartment());
                ps.setString(4, e.getContactInfo());
                ps.setInt(5, e.getEmployeeId()); // Set the employee_id in the WHERE clause

                status = ps.executeUpdate();
            } catch (Exception ex) {
                System.err.println("Error while updating details: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return status;
    }

    public static Employee deleteByName(String name) {
        int status = 0;
        Employee emp = getEmployeeByName(name);

        if (emp == null) {
            System.out.println("Employee details do not exist in the database.");
            return null;
        } else {
            try (Connection con = DbConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement("DELETE FROM Employee WHERE employee_id = ?")) {
                ps.setInt(1, emp.getEmployeeId());
                status = ps.executeUpdate();
                if (status > 0) {
                    System.out.print("Employee deleted successfully.	");
                } else {
                    System.out.print("Failed to delete employee.  	");
                }
            } catch (Exception ex) {
                System.err.println("Error while deleting details: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return emp;
    }

    public static int deleteById(int employee_id) {
        int status = 0;
        Employee emp = getEmployeeById(employee_id);

        if (emp == null) {
            System.out.println("Employee details do not exist in the database.");
            return status;
        } else {
            try (Connection con = DbConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement("DELETE FROM Employee WHERE employee_id = ?")) {
                ps.setInt(1, employee_id);
                status = ps.executeUpdate();
                if (status > 0) {
                    System.out.println("Employee deleted successfully.");
                } else {
                    System.out.println("Failed to delete employee.");
                }
            } catch (Exception ex) {
                System.err.println("Error while deleting details: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return status;
    }

    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        try (Connection con = DbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Employee")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee e = new Employee();
                    e.setEmployeeId(rs.getInt("employee_id"));
                    e.setName(rs.getString("name"));
                    e.setDesignation(rs.getString("designation"));
                    e.setDepartment(rs.getString("department"));
                    e.setContactInfo(rs.getString("contact_info"));
                    employees.add(e);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error while retrieving employees: " + ex.getMessage());
            ex.printStackTrace();
        }

        return employees; // Return the list of employees
    }

    public static Employee getEmployeeByName(String name) {
        Employee e = null;
        try (Connection con = DbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Employee WHERE name = ?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    e = new Employee();
                    e.setEmployeeId(rs.getInt("employee_id"));
                    e.setName(rs.getString("name"));
                    e.setDesignation(rs.getString("designation"));
                    e.setDepartment(rs.getString("department"));
                    e.setContactInfo(rs.getString("contact_info"));
                }
            }
        } catch (Exception ex) {
            System.err.println("Error while retrieving employee: " + ex.getMessage());
            ex.printStackTrace();
        }
        return e;
    }

    public static Employee getEmployeeById(int id) {
        Employee e = null;
        try (Connection con = DbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Employee WHERE employee_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    e = new Employee();
                    e.setEmployeeId(rs.getInt("employee_id"));
                    e.setName(rs.getString("name"));
                    e.setDesignation(rs.getString("designation"));
                    e.setDepartment(rs.getString("department"));
                    e.setContactInfo(rs.getString("contact_info"));
                }
            }
        } catch (Exception ex) {
            System.err.println("Error while retrieving employee: " + ex.getMessage());
            ex.printStackTrace();
        }
        return e;
    }

    public static List<Employee> getEmployeesByName(String name) {
        List<Employee> employees = new ArrayList<>();

        try (Connection con = DbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Employee WHERE name = ?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee e = new Employee();
                    e.setEmployeeId(rs.getInt("employee_id")); // Use column name for clarity
                    e.setName(rs.getString("name"));
                    e.setDesignation(rs.getString("designation"));
                    e.setDepartment(rs.getString("department"));
                    e.setContactInfo(rs.getString("contact_info"));
                    employees.add(e); // Add employee to the list
                }
            }
        } catch (Exception ex) {
            System.err.println("Error while retrieving employees: " + ex.getMessage());
            ex.printStackTrace();
        }

        return employees; // Return the list of employees
    }

    public static List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> employees = new ArrayList<>();

        try (Connection con = DbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Employee WHERE department = ?")) {
            ps.setString(1, department);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee e = new Employee();
                    e.setEmployeeId(rs.getInt("employee_id"));
                    e.setName(rs.getString("name"));
                    e.setDesignation(rs.getString("designation"));
                    e.setDepartment(rs.getString("department"));
                    e.setContactInfo(rs.getString("contact_info"));
                    employees.add(e);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error while retrieving employees: " + ex.getMessage());
            ex.printStackTrace();
        }

        return employees; // Return the list of employees
    }

}

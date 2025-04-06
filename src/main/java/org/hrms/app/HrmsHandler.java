package org.hrms.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.hrms.model.Attendance;
import org.hrms.model.Employee;
import org.hrms.model.Leave;
import org.hrms.service.AttendanceService;
import org.hrms.service.EmployeeLeaveService;
import org.hrms.service.EmployeeService;

public class HrmsHandler {
	public static void startingPoint() {
		Scanner scanner = new Scanner(System.in);
		Employee emp = null;
		Attendance a = null;

		int employeeId = 0;
		String status = null;
		Date specificDate = null;

		int choice;

		do {
			System.out.println("1. Mark Attendance\n"
					+ "2. Track Attendance\n"
					+ "3. Add Leave\n"
					+ "4. View Leave\n"
					+ "5. Update Leave Status\n"
					+ "6. Add Employee\n"
					+ "7. View Employee\n"
					+ "8. Update Employee\n"
					+ "9. Delete Employee\n"
					+ "0. Exit");

			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();

			switch (choice) {
				case 1:

					System.out.println("Mark Attendance selected.");
					System.out.println("1. For Present Date\n"
							+ "2. For Specific Date");
					int pOrS = scanner.nextInt();
					scanner.nextLine();
					if (pOrS == 1) {
						System.out.println("Enter employee id ");
						employeeId = scanner.nextInt();
						scanner.nextLine();
						System.out.println("Enter attendance status (present/leave/absent):");
						status = scanner.nextLine();

						// System.out.println("\n " + employeeId + " " + status);

						if (employeeId == 0 && status == null) {
							System.out.println("Please provide both employee ID and attendance status.");
							break;
						}

						if (status.equalsIgnoreCase("present")) {
							a = new Attendance(employeeId, new Date(), Attendance.AttendanceStatus.PRESENT);
						} else if (status.equalsIgnoreCase("leave")) {
							a = new Attendance(employeeId, new Date(), Attendance.AttendanceStatus.LEAVE);
						} else if (status.equalsIgnoreCase("absent")) {
							a = new Attendance(employeeId, new Date(), Attendance.AttendanceStatus.ABSENT);
						} else {
							System.out.println(
									"Invalid attendance status. Please enter 'present', 'leave', or 'absent'.");
							break; // Exit the case
						}

						AttendanceService.save(a);
					} else if (pOrS == 2) {

						System.out.println(
								"Enter employee id, attendance status (present/leave/absent) and date(yyyy-mm-dd):");
						System.out.print("Enter employee id: ");
						employeeId = scanner.nextInt();
						scanner.nextLine();
						System.out.print("Enter attendance status (present/leave/absent):");
						status = scanner.nextLine();
						System.out.print("Enter specific date: ");
						specificDate = dateFormate(scanner.nextLine());

						System.out.println(specificDate);

						if (employeeId == 0 && status == null && specificDate == null) {
							System.out.println("Please provide employee ID, attendance status and status.");
							break;
						}

						if (status.equalsIgnoreCase("present")) {
							a = new Attendance(employeeId, specificDate, Attendance.AttendanceStatus.PRESENT);
						} else if (status.equalsIgnoreCase("leave")) {
							a = new Attendance(employeeId, specificDate, Attendance.AttendanceStatus.LEAVE);
						} else if (status.equalsIgnoreCase("absent")) {
							a = new Attendance(employeeId, specificDate, Attendance.AttendanceStatus.ABSENT);
						} else {
							System.out.println(
									"Invalid attendance status. Please enter 'present', 'leave', or 'absent'.");
							break; // Exit the case
						}

						AttendanceService.save(a);
					}
					System.out.println("Attendance is saved.");
					System.out.println();
					break;
				case 2:
					System.out.println("Track Attendance selected.");

					System.out.println("Choose one of the below option to get track the attendance :\n"
							+ "1. View all attendance list \n"
							+ "2. Attandance date\n"
							+ "3. Status\n"
							+ "For a particular employee\n"
							+ "Enter option and employee id: ");

					System.out.print("Enter option: ");
					int in = scanner.nextInt();
					if (in > 1 && in <= 3) {
						System.out.print("Enter employee id: ");
						employeeId = scanner.nextInt();
						scanner.nextLine();
					}

					if (in == 0) {
						System.out.println("Please provide enough details.");
						break;
					}

					List<Attendance> attendanceList = null;
					if (in == 1) {
						System.out.println("All attendance records: ");
						attendanceList = AttendanceService.getAttendanceList();
					} else if (in == 2) {
						System.out.print("Enter specific date(yyyy-mm-dd): ");
						specificDate = dateFormate(scanner.nextLine());
						attendanceList = AttendanceService.getAttendanceListByDate(specificDate, employeeId);
					} else if (in == 3) {
						System.out.print("Enter attendance status (Pending/Approved/Rejected):");
						status = scanner.nextLine();
						attendanceList = AttendanceService.getAttendanceListByStatus(status.toUpperCase(), employeeId);
					}

					attendanceList.stream().forEach(i -> System.out.println(i));
					System.out.println();
					break;
				case 3:
					System.out.println("Add Leave selected.");

					System.out.print("Enter employee id: ");
					int id = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Enter leave type: ");
					String leavetype = scanner.nextLine();
					System.out.print("Enter start date: ");
					String startDate = scanner.nextLine();
					System.out.print("Enter end date: ");
					String endDate = scanner.nextLine();

					EmployeeLeaveService
							.saveLeave(new Leave(id, leavetype, dateFormate(startDate), dateFormate(endDate)));
					System.out.println("Leave is added");

					break;
				case 4:
					System.out.println("View Leave selected.");

					System.out.print("1. All employee leaves details\n"
							+ "2. Specific employee leaves details\n"
							+ "3. All employee leave details based on the specific status");
					System.out.print("Please select an option (1, 2 or 3): ");
					int opt = scanner.nextInt();
					scanner.nextLine();
					switch (opt) {
						case 1:
							System.out.println("Displaying all employee leaves details...");
							List<Leave> list1 = EmployeeLeaveService.viewAllLeaves();
							list1.stream().forEach(k -> System.out.println(k));
							break;
						case 2:
							System.out.println("Displaying all employee leaves based employee id");
							System.out.print("Enter employee id: ");
							List<Leave> list2 = EmployeeLeaveService.viewAllLeavesBasedOnEmployeeId(scanner.nextInt());
							list2.stream().forEach(k -> System.out.println(k));
							break;
						case 3:
							System.out.println("Displaying all employee leaves based on status...");
							System.out.print("Enter approved, leave or present: ");

							List<Leave> list3 = EmployeeLeaveService.viewAllLeavesBasedOnStatus(scanner.nextLine());
							if (!list3.isEmpty()) {
								list3.stream().forEach(k -> System.out.println(k));
							} else {
								System.out.println("Records not found.");
							}
							break;
						default:
							System.out.println("Invalid option selected. Please try again.");
							break;
					}
					System.out.println();
					break;
				case 5:
					System.out.println("Update Leave Status selected.\n "
							+ "Enter employee Id and status ('Pending','Approved','Rejected') in a single line with a space in betweeen");

					System.out.print("Enter employee id: ");
					employeeId = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Enter attendance status ('pending','approved','rejected'):");
					status = scanner.nextLine().toUpperCase();

					if (employeeId == 0 && status == null) {
						System.out.println("Please provide employee details.");
						break;
					}

					EmployeeLeaveService.updateLeaveStatus(employeeId, status);
					System.out.println("Employee status updated");
					System.out.println();
					break;
				case 6:
					System.out.println("Add Employee selected.");

					System.out.println("Enter employee id, name, designation, department and contact info: ");
					System.out.print("Entering format: employee_id,name,designation,department,contact info: ");
					scanner.nextLine();
					String[] emp1 = scanner.nextLine().split(",");

					// Check if the input has at least 5 parts
					if (emp1.length < 5) {
						System.out.println("Please provide all employee details.");
						return; // Use return to exit the method if not in a loop
					}

					try {
						// Trim whitespace from each part
						employeeId = Integer.parseInt(emp1[0].trim());
						String name = emp1[1].trim();
						String designation = emp1[2].trim();
						String department = emp1[3].trim();
						String contactInfo = emp1[4].trim();

						// Create a new Employee object
						emp = new Employee(employeeId, name, designation, department, contactInfo);
						EmployeeService.save(emp);
						System.out.println(emp.toString());
						System.out.println("Employee details added");
					} catch (NumberFormatException e) {
						System.out.println("Invalid employee ID. Please enter a valid integer.");
					} catch (Exception e) {
						System.out.println("An error occurred: " + e.getMessage());
					}

					break;
				case 7:
					System.out.println("View Employee selected.");

					System.out.println("Choose an option:");
					System.out.println("1. View All Employees");
					System.out.println("2. View Employee by ID");
					System.out.println("3. View Employee by Name");
					System.out.println("4. View Employee by Department");

					int viewChoice = scanner.nextInt();
					scanner.nextLine();

					switch (viewChoice) {
						case 1:
							List<Employee> allEmployees = EmployeeService.getAllEmployees(); // Call static method
							allEmployees.stream().forEach(k -> System.out.println(k));
							break;

						case 2:
							System.out.print("Enter Employee ID: ");
							employeeId = scanner.nextInt();
							scanner.nextLine();
							emp = EmployeeService.getEmployeeById(employeeId);
							if (emp != null) {
								System.out.println(emp);
							} else {
								System.out.println("Employee not found.");
							}
							break;

						case 3:
							System.out.print("Enter Employee Name: ");
							String employeeName = scanner.nextLine();
							List<Employee> employeesByName = EmployeeService.getEmployeesByName(employeeName);
							if (!employeesByName.isEmpty()) {
								employeesByName.stream().forEach(k -> System.out.println(k));
							} else {
								System.out.println("No employees found with that name.");
							}
							break;

						case 4:
							System.out.print("Enter Department Name: ");
							String departmentName = scanner.nextLine();
							List<Employee> employeesByDepartment = EmployeeService
									.getEmployeesByDepartment(departmentName);
							if (!employeesByDepartment.isEmpty()) {
								employeesByDepartment.stream().forEach(k -> System.out.println(k));
							} else {
								System.out.println("No employees found in that department.");
							}
							break;

						default:
							System.out.println("Invalid choice. Please select a valid option.");
							break;
					}
					System.out.println();
					break;

				case 8:
					System.out.println("Update Employee selected.");
					emp = new Employee();
					String yOrN = null;

					System.out.print("Enter employee id for the update :");
					employeeId = scanner.nextInt();
					scanner.nextLine();
					emp.setEmployeeId(employeeId);

					Employee tempEmp = EmployeeService.getEmployeeById(employeeId);

					System.out.println(tempEmp);

					System.out.print("Would you like to update the name (y or n): ");
					yOrN = scanner.nextLine().toLowerCase();
					if (yOrN.equals("y")) {
						System.out.print("Enter new name: ");
						String name = scanner.nextLine();
						emp.setName(name);
					} else {
						emp.setName(tempEmp.getName());
					}

					System.out.print("Would you like to update the designation (y or n): ");
					yOrN = scanner.nextLine().toLowerCase();
					if (yOrN.equals("y")) {
						System.out.print("Enter new designation: ");
						String designation = scanner.nextLine();
						emp.setDesignation(designation);
					} else {
						emp.setDesignation(tempEmp.getDesignation());
					}

					System.out.print("Would you like to update the department (y or n): ");
					yOrN = scanner.nextLine().toLowerCase();
					if (yOrN.equals("y")) {
						System.out.print("Enter new department: ");
						String department = scanner.nextLine();
						emp.setDepartment(department);
					} else {
						emp.setDepartment(tempEmp.getDepartment());
					}

					System.out.print("Would you like to update the contact info (y or n): ");
					yOrN = scanner.nextLine().toLowerCase();
					if (yOrN.equals("y")) {
						System.out.print("Enter new contact info: ");
						String contactInfo = scanner.nextLine();
						emp.setContactInfo(contactInfo);
					} else {
						emp.setContactInfo(tempEmp.getContactInfo());
					}

					EmployeeService.update(emp);
					System.out.println("Employee details are updated.");
					System.out.println("Updated detials: " + EmployeeService.getEmployeeById(employeeId));
					System.out.println();
					break;
				case 9:
					System.out.println("Delete Employee selected.");

					System.out.print("Enter employee id: ");
					EmployeeService.deleteById(scanner.nextInt());
					System.out.println("Employee details deleted");
					System.out.println();
					break;
				case 0:
					System.out.println("Exiting the program.");
					System.out.println();
					break;
				default:
					System.out.println("Invalid choice. Please try again.\n");
			}
		} while (choice != 0);

		scanner.close();
	}

	private static Date dateFormate(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilDate = null;
		java.sql.Date sqlDate = null;
		try {
			utilDate = dateFormat.parse(dateString);
			sqlDate = new java.sql.Date(utilDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sqlDate;
	}
}
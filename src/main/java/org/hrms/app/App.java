package org.hrms.app;

import java.util.Scanner;

import org.hrms.model.Admin;
import org.hrms.service.AdminService;

public class App {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean isAuthenticated = false;

        while (!isAuthenticated) {
            System.out.println("WELCOME BACK ADMIN!");
            System.out.print("Enter your Username: ");
            String username = in.nextLine();
            System.out.print("Enter your Password: ");
            String password = in.nextLine();

            if (AdminService.verify(new Admin(username, password))) {
                isAuthenticated = true;
                HrmsHandler.startingPoint();
                isAuthenticated = true;
            } else {
                System.out.println("Your credentials are not correct!! Please try again.");
            }
        }

        in.close();
    }
}

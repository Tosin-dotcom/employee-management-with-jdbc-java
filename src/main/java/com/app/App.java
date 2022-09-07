package com.app;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String dbURL = "jdbc:mysql://localhost:3306/staffinformations";
        String dbUsername = "root";
        String dbPassword = "Iyiola@123";
        String query = "";
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            System.out.println("Connection established");
            System.out.println("Hello there");
            System.out.println("Welcome to this Employee Management System");

            Statement stat = conn.createStatement();

            while (true) {
                System.out.println(
                        "Enter number to continue. Please -1 to end program \n1. Add Employee \n2.Get number of Employee \n3. Print all employee");

                String ans = scanner.nextLine();

                if (ans.equals("-1")) {
                    break;
                }

                switch (ans) {
                    case "1":
                        query = "INSERT INTO employee VALUES ";
                        try {
                            System.out.println("Enter number of employee to add");
                            int num = scanner.nextInt();
                            for (int i = 0; i < num; i++) {

                                scanner.nextLine();

                                System.out.println("Enter Staff name: ");
                                String name = scanner.nextLine();

                                System.out.println("Enter role in firm: ");
                                String role = scanner.nextLine();

                                System.out.println("Enter age: ");
                                int age = scanner.nextInt();

                                System.out.println("Enter date joined: ");
                                String input = scanner.next();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate dateJoined = LocalDate.parse(input, formatter);

                                query += "(NULL, '" + name + "', " + age + ", '" + role + "', '" + dateJoined + "')";
                                System.out.println(query);
                                stat.execute(query);

                            }

                        } catch (Exception e) {
                            System.out.println("Error" + e);
                        }
                        System.out.println("Data successfully Uploaded");
                        break;

                    case "2":
                        query = "SELECT COUNT(*) FROM employee";
                        ResultSet data = stat.executeQuery(query);
                        data.next();
                        int count = data.getInt(1);
                        System.out.println("Number of staffs: " + count);
                        break;

                    case "3":
                        query = "SELECT * FROM employee";
                        ResultSet staffs = stat.executeQuery(query);
                        System.out.printf("%-3s %-20s %-20s %-4s %-15s\n", "ID", "Name", "Role", "Age", "Date Joined");
                        while (staffs.next()) {
                            int eid = staffs.getInt("employee_id");
                            String name = staffs.getString("name");
                            String role = staffs.getString("role");
                            int age = staffs.getInt("age");
                            Date date = staffs.getDate("date_joined");
                            System.out.printf("%-3s %-20s %-20s %-4d %-15s\n", eid, name, role, age, date.toString());

                        }
                        break; 

                    default:
                        break;

                }

            }

            conn.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        scanner.close();
    }
}

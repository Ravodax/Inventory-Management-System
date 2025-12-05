package com.ravodax.inventory;

import com.ravodax.inventory.ConsoleApp.*;
import com.ravodax.inventory.service.UserDAO;
import com.ravodax.inventory.model.User;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Login: ");
        String log = scanner.nextLine();
        System.out.print("Enter Password: ");
        String pasw = scanner.nextLine();
        for (int i = 0; i != users.size(); ++i) {
            if (users.get(i).getUsername().equals(log)) {
                if (users.get(i).getPassword().equals(pasw)) {
                    System.out.print("login!");
                    if (users.get(i).getRole().getId() == 3) {
                        ConsoleUsers v = new ConsoleViewer(users.get(i).getId());
                        ConsoleApp app = new ConsoleApp(v);
                    }
                    if (users.get(i).getRole().getId() == 2) {
                        ConsoleUsers v = new ConsoleManager(users.get(i).getId());
                        ConsoleApp app = new ConsoleApp(v);
                    }
                    if (users.get(i).getRole().getId() == 1) {
                        ConsoleUsers v = new ConsoleAdmin(users.get(i).getId());
                        ConsoleApp app = new ConsoleApp(v);
                    }

                }
                else {
                    System.out.print("NO login!");
                }
            }
        }
        scanner.close();
    }
}
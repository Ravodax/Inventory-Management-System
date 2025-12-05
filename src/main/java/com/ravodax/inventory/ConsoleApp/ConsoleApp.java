package com.ravodax.inventory.ConsoleApp;
import com.ravodax.inventory.ConsoleApp.ConsoleUsers;
import com.ravodax.inventory.model.User;

import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    public ConsoleApp(ConsoleUsers users) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            users.printCommand();
            String input = scanner.nextLine();
            if (input.equals("0")) {
                System.out.print("exit");
                break;
            }
            else {
                users.funcStart(input, scanner);
            }
        }
        scanner.close();
    }
}

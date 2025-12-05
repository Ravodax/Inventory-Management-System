package com.ravodax.inventory.ConsoleApp;

import java.util.Scanner;

abstract public class ConsoleUsers {
    long id;
    ConsoleUsers(long id) {this.id = id;}
    public abstract void printCommand();
    public abstract void funcStart(String name, Scanner scanner);
}

package com.ravodax.inventory.ConsoleApp;

import com.ravodax.inventory.model.Product;
import com.ravodax.inventory.service.ProductDAO;

import java.util.List;
import java.util.Scanner;

public class ConsoleViewer extends ConsoleUsers {
    public ConsoleViewer(long id) {
        super(id);
    }

    @Override
    public void printCommand() {
        System.out.println("------------------");
        System.out.println("Print product");
        System.out.println("------------------: ");
    }

    @Override
    public void funcStart(String name, Scanner scanner) {
        if (name.equals("Print product")) {
            printProduct();
        }
    }

    public void printProduct() {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();
        System.out.println("array products:");
        products.forEach(System.out::println);
    }
}

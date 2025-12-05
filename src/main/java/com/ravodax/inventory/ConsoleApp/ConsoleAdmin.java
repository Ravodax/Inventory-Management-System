package com.ravodax.inventory.ConsoleApp;

import com.ravodax.inventory.model.Category;
import com.ravodax.inventory.model.Product;
import com.ravodax.inventory.service.CategoryDAO;
import com.ravodax.inventory.service.ProductDAO;

import java.util.Scanner;

public class ConsoleAdmin extends ConsoleManager{
    public ConsoleAdmin(long id) {
        super(id);
    }
    @Override
    public void printCommand() {
        super.printCommand();
        System.out.println("------------------");
        System.out.println("Edit product: ");
        System.out.println("Add product: ");
        System.out.println("------------------: ");
    }
    @Override
    public void funcStart(String name, Scanner scanner) {
        super.funcStart(name, scanner);
        if (name.equals("Edit product")) {
            editProduct(scanner);
        }
        if (name.equals("Add product")) {
            addProduct(scanner);
        }
    }
    public void editProduct(Scanner scanner) {
        ProductDAO dao = new ProductDAO();
        System.out.println("Enter id: ");
        String input = scanner.nextLine();
        Product newProduct = dao.getProductById(Integer.parseInt(input));
        System.out.println("Enter price: ");
        input = scanner.nextLine();
        newProduct.setPrice(Double.valueOf(input));
        System.out.println("Enter quantity: ");
        input = scanner.nextLine();
        newProduct.setQuantity(Integer.parseInt(input));
        boolean updated = dao.updateProduct(newProduct);
        System.out.println("status: " + updated);

    }

    public void addProduct(Scanner scanner) {
        ProductDAO dao = new ProductDAO();
        CategoryDAO daoCat = new CategoryDAO();
        System.out.println("Enter name category: ");
        String input = scanner.nextLine();
        long catId1 = daoCat.getOrCreateCategoryId(input);
        Category cat = new Category(catId1, input);
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter price: ");
        String price = scanner.nextLine();

        Product newProduct = new Product(1, name, cat, 0, Double.valueOf(price));
        dao.addProduct(newProduct);

    }
}

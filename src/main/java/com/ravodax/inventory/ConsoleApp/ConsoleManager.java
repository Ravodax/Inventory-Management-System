package com.ravodax.inventory.ConsoleApp;

import com.ravodax.inventory.model.Category;
import com.ravodax.inventory.model.Product;
import com.ravodax.inventory.service.InventoryTransactionDAO;
import com.ravodax.inventory.service.ProductDAO;

import java.util.Scanner;

public class ConsoleManager extends ConsoleViewer{
    public ConsoleManager(long id) {
        super(id);
    }
    @Override
    public void printCommand() {
        super.printCommand();
        System.out.println("------------------");
        System.out.println("+product ");
        System.out.println("-product ");
        System.out.println("Print trans");
        System.out.println("------------------: ");
    }

    @Override
    public void funcStart(String name, Scanner scanner) {
        super.funcStart(name, scanner);
        if (name.equals("+product")) {
            addQuantityProduct(scanner);
        }
        if (name.equals("-product")) {
            delQuantityProduct(scanner);
        }
        if (name.equals("Print trans")) {
            printInventoryTrans();
        }
        if (name.equals("Print profit")) {
            printIncomeAndExpenses();
        }
    }

    public void addQuantityProduct(Scanner scanner) {
        ProductDAO dao = new ProductDAO();
        System.out.println("Enter id: ");
        String input = scanner.nextLine();
        Product newProduct = dao.getProductById(Integer.parseInt(input));

        System.out.println("Enter quantity: +");
        input = scanner.nextLine();
        newProduct.setQuantity(newProduct.getQuantity() + Integer.parseInt(input));
        boolean updated = dao.updateProduct(newProduct);
        System.out.println("status: " + updated);
        InventoryTransactionDAO it = new InventoryTransactionDAO();
        it.addTransaction("RESTOCK", newProduct.getId(), id, Integer.parseInt(input), newProduct.getPrice());
    }

    public void delQuantityProduct(Scanner scanner) {
        ProductDAO dao = new ProductDAO();
        System.out.println("Enter id: ");
        String input = scanner.nextLine();
        Product newProduct = dao.getProductById(Integer.parseInt(input));

        System.out.println("Enter quantity: -");
        input = scanner.nextLine();
        if ((newProduct.getQuantity() - Integer.parseInt(input)) < 0) {
            System.out.println("error: -");
            return;
        }
        newProduct.setQuantity(newProduct.getQuantity() - Integer.parseInt(input));
        boolean updated = dao.updateProduct(newProduct);
        System.out.println("status: " + updated);
        InventoryTransactionDAO it = new InventoryTransactionDAO();
        it.addTransaction("SALE", newProduct.getId(), id, Integer.parseInt(input), newProduct.getPrice());
    }

    public void printInventoryTrans() {
        InventoryTransactionDAO it = new InventoryTransactionDAO();
        it.printAllTransactions();
    }

    public void printIncomeAndExpenses() {
        InventoryTransactionDAO it = new InventoryTransactionDAO();
        it.printIncomeAndExpenses();
    }
}

package com.ravodax.inventory;

import com.ravodax.inventory.model.*;


import java.util.Arrays;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // --- Создание категорий ---
        Category electronics = new Category(1, "Electronics");
        Category food = new Category(2, "Food");

        // --- Создание товаров ---
        Product laptop = new Product(1, "Laptop", electronics, 10, 1200.0);
        Product phone = new Product(2, "Smartphone", electronics, 5, 800.0);
        Product apple = new Product(3, "Apple", food, 50, 1.2);

        System.out.println("PRODUCTS:");
        System.out.println(laptop);
        System.out.println(phone);
        System.out.println(apple);
        System.out.println();

        // --- Создание ролей ---
        Role adminRole = new Role(1, "ADMIN");

        // --- Создание пользователя ---
        User admin = new User(1, "admin", "12345", adminRole);

        System.out.println("USER:");
        System.out.println(admin);
        System.out.println();

        // --- Создание элементов заказа ---
        OrderItem item1 = new OrderItem(laptop, 1);
        OrderItem item2 = new OrderItem(apple, 10);

        // --- Создание списка товаров в заказе ---
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(item1);
        orderItems.add(item2);

        // --- Создание заказа ---
        Order order = new Order(1, admin, orderItems, OrderStatus.CREATED);

        System.out.println("ORDER:");
        System.out.println(order);

        // --- Подробный вывод ---
        System.out.println("\nORDER ITEMS:");
        orderItems.forEach(System.out::println);

        System.out.println("\nTotal price: " + order.getTotalPrice());
        System.out.println("Order status: " + order.getStatus());
    }
}
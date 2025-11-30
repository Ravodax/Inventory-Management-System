package com.ravodax.inventory;

import com.ravodax.inventory.model.*;
import com.ravodax.inventory.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final RoleService roleService;
    private final UserService userService;
    private final OrderService orderService;

    public Main(CategoryService categoryService,
                ProductService productService,
                RoleService roleService,
                UserService userService,
                OrderService orderService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.roleService = roleService;
        this.userService = userService;
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // --- Создание категорий ---
        Category electronics = categoryService.createCategory(new Category(0, "Electronics"));
        Category food = categoryService.createCategory(new Category(0, "Food"));

        // --- Создание товаров ---
        Product laptop = productService.createProduct(new Product(0, "Laptop", electronics, 10, 1200.0));
        Product phone = productService.createProduct(new Product(0, "Smartphone", electronics, 5, 800.0));
        Product apple = productService.createProduct(new Product(0, "Apple", food, 50, 1.2));

        // --- Создание ролей ---
        Role adminRole = roleService.createRole(new Role(0, "ADMIN"));

        // --- Создание пользователя ---
        User admin = userService.createUser(new User(0, "admin", "12345", adminRole));

        // --- Создание заказа ---
        OrderItem item1 = new OrderItem(laptop, 1);
        OrderItem item2 = new OrderItem(apple, 10);

        Order order = new Order();
        order.setUser(admin);
        order.setItems(Arrays.asList(item1, item2));
        order.setStatus(OrderStatus.CREATED);

        orderService.createOrder(order);

        // --- Вывод ---
        System.out.println("CATEGORIES:");
        categoryService.getAllCategories().forEach(System.out::println);

        System.out.println("\nPRODUCTS:");
        productService.getAllProducts().forEach(System.out::println);

        System.out.println("\nUSERS:");
        userService.getAllUsers().forEach(System.out::println);

        System.out.println("\nORDERS:");
        orderService.getAllOrders().forEach(System.out::println);
    }
}

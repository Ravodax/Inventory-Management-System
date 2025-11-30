package com.ravodax.inventory.model;


import java.util.List;

public class Order {
    private long id;
    private User user;
    private List<OrderItem> items;
    private double totalPrice;
    private OrderStatus status;

    public Order() {}

    public Order(long id, User user, List<OrderItem> items, OrderStatus status) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.status = status;
        this.totalPrice = calculateTotal();
    }

    private double calculateTotal() {
        return items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) {
        this.items = items;
        this.totalPrice = calculateTotal();
    }

    public double getTotalPrice() { return totalPrice; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", items=" + items +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
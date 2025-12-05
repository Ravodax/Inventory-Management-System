package com.ravodax.inventory.model;


import java.util.List;

public class Order {
    private long id;
    private User user;
    private double totalPrice;
    private OrderStatus status;

    public Order() {}

    public Order(long id, User user, double totalPrice, OrderStatus status) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public double getTotalPrice() { return totalPrice; }

    public OrderStatus getStatus() { return status; }
    public void getTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setStatus(OrderStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
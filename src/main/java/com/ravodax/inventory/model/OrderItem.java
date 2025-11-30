package com.ravodax.inventory.model;

public class OrderItem {
    private Product product;
    private int quantity;
    private double pricePerUnit;
    private double totalPrice;

    public OrderItem() {}

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.pricePerUnit = product.getPrice();
        this.totalPrice = quantity * pricePerUnit;
    }

    public Product getProduct() { return product; }
    public void setProduct(Product product) {
        this.product = product;
        this.pricePerUnit = product.getPrice();
        this.totalPrice = quantity * pricePerUnit;
    }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = quantity * pricePerUnit;
    }

    public double getPricePerUnit() { return pricePerUnit; }

    public double getTotalPrice() { return totalPrice; }

    @Override
    public String toString() {
        return "OrderItem{" +
                "product=" + product.getName() +
                ", quantity=" + quantity +
                ", pricePerUnit=" + pricePerUnit +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
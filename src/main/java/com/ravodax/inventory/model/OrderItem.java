package com.ravodax.inventory.model;

public class OrderItem {
    private long id;
    private long orderId;
    private long productId;
    private int quantity;
    private double pricePerUnit;
    private double totalPrice;

    public OrderItem() {}

    public OrderItem(long orderId, long productId, int quantity, double pricePerUnit) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = quantity * pricePerUnit;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }

    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = quantity * this.pricePerUnit;
    }

    public double getPricePerUnit() { return pricePerUnit; }
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = quantity * this.pricePerUnit;
    }

    public double getTotalPrice() { return totalPrice; }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", pricePerUnit=" + pricePerUnit +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

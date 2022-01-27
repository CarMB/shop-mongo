package org.iesfm.shop.exceptions;

public class OrderNotFoundException extends Exception{
    private int orderId;

    public OrderNotFoundException(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }
}

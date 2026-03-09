package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Order {

    private String id;
    private List<Product> products;
    private Long orderTime;
    private String author;

    @Setter
    private String status;

    private static final String WAITING_PAYMENT = "WAITING_PAYMENT";
    private static final String FAILED = "FAILED";
    private static final String CANCELLED = "CANCELLED";
    private static final String SUCCESS = "SUCCESS";

    public Order(String id, List<Product> products, Long orderTime, String author) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Products cannot be empty");
        }

        this.id = id;
        this.products = products;
        this.orderTime = orderTime;
        this.author = author;
        this.status = WAITING_PAYMENT;
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Products cannot be empty");
        }

        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid status");
        }

        this.id = id;
        this.products = products;
        this.orderTime = orderTime;
        this.author = author;
        this.status = status;
    }

    public void setStatus(String status) {
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        this.status = status;
    }

    private boolean isValidStatus(String status) {
        return WAITING_PAYMENT.equals(status) ||
                FAILED.equals(status) ||
                CANCELLED.equals(status) ||
                SUCCESS.equals(status);
    }
}
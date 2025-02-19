package com.fossil.tradeshow.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Orders {

    @Id
    private String id;
    private String emailId;
    private List<CartItem> items;
    private double totalUsd;
    private double totalInr;
    private double totalSgd;
    private double totalHkd;
    private double totalJpy;
    private double totalAud;
    private String orderDate;

    public double getTotalUsd() {
        return totalUsd;
    }

    public void setTotalUsd(double totalUsd) {
        this.totalUsd = totalUsd;
    }

    public double getTotalInr() {
        return totalInr;
    }

    public void setTotalInr(double totalInr) {
        this.totalInr = totalInr;
    }

    public double getTotalSgd() {
        return totalSgd;
    }

    public void setTotalSgd(double totalSgd) {
        this.totalSgd = totalSgd;
    }

    public double getTotalJpy() {
        return totalJpy;
    }

    public void setTotalJpy(double totalJpy) {
        this.totalJpy = totalJpy;
    }

    public double getTotalAud() {
        return totalAud;
    }

    public void setTotalAud(double totalAud) {
        this.totalAud = totalAud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalHkd() {
        return totalHkd;
    }

    public void setTotalHkd(double totalHkd) {
        this.totalHkd = totalHkd;
    }
}

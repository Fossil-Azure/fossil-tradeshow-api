package com.fossil.tradeshow.model;

public class OrderChange {
    private String product;
    private String sku;
    private int oldQuantity;
    private int newQuantity;
    private String change;
    private String imageUrl;
    private String highlightClass;

    public OrderChange(String product, String sku, int oldQuantity, int newQuantity, String change, String imageUrl, String highlightClass) {
        this.product = product;
        this.sku = sku;
        this.oldQuantity = oldQuantity;
        this.newQuantity = newQuantity;
        this.change = change;
        this.imageUrl = imageUrl;
        this.highlightClass = highlightClass != null ? highlightClass : "black"; // ✅ Ensure non-null value
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(int oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public int getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHighlightClass() {
        return highlightClass;
    }

    public void setHighlightClass(String highlightClass) {
        this.highlightClass = highlightClass;
    }
}

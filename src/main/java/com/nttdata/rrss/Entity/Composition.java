package com.nttdata.rrss.Entity;

public class Composition {
    private String assetName;
    private double assetPercentage;
    private String assetSymbol;
    private double quantity;
    private double assetPrice;

    public Composition() {
    }

    public String getAssetSymbol() {
        return assetSymbol;
    }

    public void setAssetSymbol(String assetSymbol) {
        this.assetSymbol = assetSymbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getAssetPrice() {
        return assetPrice;
    }

    public void setAssetPrice(double assetPrice) {
        this.assetPrice = assetPrice;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public double getAssetPercentage() {
        return assetPercentage;
    }

    public void setAssetPercentage(double assetPercentage) {
        this.assetPercentage = assetPercentage;
    }
}

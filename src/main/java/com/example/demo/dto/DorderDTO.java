package com.example.demo.dto;

import com.example.demo.entity.Product;

public class DorderDTO {
    private int doNum;
    private int onum;
    private int pNum;
    private int doCount;
    private int doPrice;
    private Product product;

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getDoNum() {
        return this.doNum;
    }

    public void setDoNum(int doNum) {
        this.doNum = doNum;
    }

    public int getOnum() {
        return this.onum;
    }

    public void setOnum(int onum) {
        this.onum = onum;
    }

    public int getpNum() {
        return this.pNum;
    }

    public void setpNum(int pNum) {
        this.pNum = pNum;
    }

    public int getDoCount() {
        return this.doCount;
    }

    public void setDoCount(int doCount) {
        this.doCount = doCount;
    }

    public int getDoPrice() {
        return this.doPrice;
    }

    public void setDoPrice(int doPrice) {
        this.doPrice = doPrice;
    }

}

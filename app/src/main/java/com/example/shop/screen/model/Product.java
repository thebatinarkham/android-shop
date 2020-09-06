package com.example.shop.screen.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private String productId;
    private String productName;
    private String productDes;
    private int productPrice;
    private String productCategory;
    private String productTag;
    private String productLocation;
    private String productImage1;
    private String productImage2;
    private String productImage3;


    public Product(){}

    public Product(String productName, String productDes, String productImage1) {
        this.productName = productName;
        this.productDes = productDes;
        this.productImage1 = productImage1;
    }

    public Product(String productId, String productName, String productDes, int productPrice,
                   String productCategory, String productTag,
                   String productLocation, String productImage1, String productImage2, String productImage3) {
        this.productId = productId;
        this.productName = productName;
        this.productDes = productDes;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productTag = productTag;
        this.productLocation = productLocation;
        this.productImage1 = productImage1;
        this.productImage2 = productImage2;
        this.productImage3 = productImage3;
    }

    protected Product(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        productDes = in.readString();
        productPrice = in.readInt();
        productCategory = in.readString();
        productTag = in.readString();
        productLocation = in.readString();
        productImage1 = in.readString();
        productImage2 = in.readString();
        productImage3 = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(productName);
        dest.writeString(productDes);
        dest.writeInt(productPrice);
        dest.writeString(productCategory);
        dest.writeString(productTag);
        dest.writeString(productLocation);
        dest.writeString(productImage1);
        dest.writeString(productImage2);
        dest.writeString(productImage3);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDes() {
        return productDes;
    }

    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductTag() {
        return productTag;
    }

    public void setProductTag(String productTag) {
        this.productTag = productTag;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }

    public String getProductImage1() {
        return productImage1;
    }

    public void setProductImage1(String productImage1) {
        this.productImage1 = productImage1;
    }

    public String getProductImage2() {
        return productImage2;
    }

    public void setProductImage2(String productImage2) {
        this.productImage2 = productImage2;
    }

    public String getProductImage3() {
        return productImage3;
    }

    public void setProductImage3(String productImage3) {
        this.productImage3 = productImage3;
    }


}

package com.example.shop.screen.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cart implements Parcelable {
    private String ProductName;
    private String ProductId;
    private int ProductPrice;
    private String ProductDes;
    private String ProductImage1;
    private int ProductQty;

    public Cart() {   }

    public Cart(String productId, String productName, int productPrice, String productDes, String productImage1, int productQty) {
        ProductId = productId;
        ProductName = productName;
        ProductPrice = productPrice;
        ProductDes = productDes;
        ProductImage1 = productImage1;
        ProductQty = productQty;
    }


    protected Cart(Parcel in) {
        ProductName = in.readString();
        ProductId = in.readString();
        ProductPrice = in.readInt();
        ProductDes = in.readString();
        ProductImage1 = in.readString();
        ProductQty = in.readInt();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ProductName);
        dest.writeString(ProductId);
        dest.writeInt(ProductPrice);
        dest.writeString(ProductDes);
        dest.writeString(ProductImage1);
        dest.writeInt(ProductQty);
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductDes() {
        return ProductDes;
    }

    public void setProductDes(String productDes) {
        ProductDes = productDes;
    }

    public String getProductImage1() {
        return ProductImage1;
    }

    public void setProductImage1(String productImage1) {
        ProductImage1 = productImage1;
    }

    public int getProductQty() {
        return ProductQty;
    }

    public void setProductQty(int productQty) {
        ProductQty = productQty;
    }
}

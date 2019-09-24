package com.abhishek.buynsell;

public class Post {
    private  String nameOfProduct,paymentType,price,productId,postImage,profilePic;

    public Post(String nameOfProduct, String paymentType, String price, String productId, String postImage, String profilePic) {
        this.nameOfProduct = nameOfProduct;
        this.paymentType = paymentType;
        this.price = price;
        this.productId = productId;
        this.postImage = postImage;
        this.profilePic =profilePic;
    }


    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public void setNameOfProduct(String nameOfProduct) {
        this.nameOfProduct = nameOfProduct;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}

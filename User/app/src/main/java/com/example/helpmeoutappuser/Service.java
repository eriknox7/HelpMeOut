package com.example.helpmeoutappuser;

public class Service {
    private String id, storeName, category, ownerName, phone, address, price, specialField, imageUrl;

    public Service() {}

    public Service(String id, String storeName, String category, String ownerName, String phone, String address, String price, String specialField, String imageUrl) {
        this.id = id;
        this.storeName = storeName;
        this.category = category;
        this.ownerName = ownerName;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.specialField = specialField;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public String getStoreName() { return storeName; }
    public String getCategory() { return category; }
    public String getOwnerName() { return ownerName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getPrice() { return price; }
    public String getSpecialField() { return specialField; }
    public String getImageUrl() { return imageUrl; }
}

package com.example.helpmeoutapp;

import java.util.Map;

public class CafeModel {
    private String id, category, storeName, ownerName, phone, address, gmapUrl, imageUrl, openingTime, closingTime;
    private float rating;
    private Map<String, String> dynamicFields; // Extra fields

    public CafeModel() {}

    public CafeModel(String id, String category, String storeName, String ownerName, String phone, String address,
                     String gmapUrl, String imageUrl, String openingTime, String closingTime, float rating, Map<String, String> dynamicFields) {
        this.id = id;
        this.category = category; // New field
        this.storeName = storeName;
        this.ownerName = ownerName;
        this.phone = phone;
        this.address = address;
        this.gmapUrl = gmapUrl;
        this.imageUrl = imageUrl;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.rating = rating;
        this.dynamicFields = dynamicFields;
    }

    public String getId() { return id; }
    public String getCategory() { return category; } // New Getter
    public String getStoreName() { return storeName; }
    public String getOwnerName() { return ownerName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getGmapUrl() { return gmapUrl; }
    public String getImageUrl() { return imageUrl; }
    public String getOpeningTime() { return openingTime; }
    public String getClosingTime() { return closingTime; }
    public float getRating() { return rating; }
    public Map<String, String> getDynamicFields() { return dynamicFields; }

    public void setId(String id) { this.id = id; }
    public void setCategory(String category) { this.category = category; } // New Setter
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setGmapUrl(String gmapUrl) { this.gmapUrl = gmapUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setOpeningTime(String openingTime) { this.openingTime = openingTime; }
    public void setClosingTime(String closingTime) { this.closingTime = closingTime; }
    public void setRating(float rating) { this.rating = rating; }
    public void setDynamicFields(Map<String, String> dynamicFields) { this.dynamicFields = dynamicFields; }
}

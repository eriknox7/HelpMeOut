package com.example.helpmeoutappuser;

import java.util.Map;

public class ServiceModel {
    private String id, storeName, ownerName, phone, address, gmapUrl, imageUrl, openingTime, closingTime;
    private float rating;
    private Map<String, String> dynamicFields; // Holds extra fields based on category

    public ServiceModel() {}

    public ServiceModel(String id, String storeName, String ownerName, String phone, String address,
                        String gmapUrl, String imageUrl, String openingTime, String closingTime,
                        float rating, Map<String, String> dynamicFields) {
        this.id = id;
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
}

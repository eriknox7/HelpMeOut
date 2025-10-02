package com.example.helpmeoutappuser;

import java.util.Map;

public class CyberCafeModel {
    private String id, storeName, ownerName, phone, address, gmapUrl, imageUrl, services;
    private float rating;
    private Map<String, String> dynamicFields; // Store extra fields dynamically

    public CyberCafeModel() {}

    public CyberCafeModel(String id, String storeName, String ownerName, String phone, String address,
                          String gmapUrl, String imageUrl, String services, float rating, Map<String, String> dynamicFields) {
        this.id = id;
        this.storeName = storeName;
        this.ownerName = ownerName;
        this.phone = phone;
        this.address = address;
        this.gmapUrl = gmapUrl;
        this.imageUrl = imageUrl;
        this.services = services;
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
    public String getServices() { return services; }
    public float getRating() { return rating; }
    public Map<String, String> getDynamicFields() { return dynamicFields; }
}

package com.example.phonebook.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "address",
        foreignKeys = @ForeignKey(entity = Contact.class,
                parentColumns = "id",
                childColumns = "contactId",
                onDelete = ForeignKey.CASCADE))
public class Address {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long contactId;
    private String detail;
    private String district;
    private String province;
    private String country;

    public Address(long contactId, String country, String detail, String district, String province) {
        this.contactId = contactId;
        this.country = country;
        this.detail = detail;
        this.district = district;
        this.province = province;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}

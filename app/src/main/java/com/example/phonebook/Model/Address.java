package com.example.phonebook.Model;

import androidx.annotation.NonNull;
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
    private String ward;
    private String type;

    public Address(long contactId, String detail, String district, String province, String type, String ward) {
        this.contactId = contactId;
        this.detail = detail;
        this.district = district;
        this.province = province;
        this.type = type;
        this.ward = ward;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return detail + "\n" + ward + "\n" + district + "\n" + province;
    }
}

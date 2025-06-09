package com.example.phonebook.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "nick_name",
        foreignKeys = @ForeignKey(entity = Contact.class,
                parentColumns = "id",
                childColumns = "contactId",
                onDelete = ForeignKey.CASCADE))

public class NickName {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long contactId;
    private String number;
    private String type;

    public NickName(long contactId, int id, String number, String type) {
        this.contactId = contactId;
        this.id = id;
        this.number = number;
        this.type = type;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

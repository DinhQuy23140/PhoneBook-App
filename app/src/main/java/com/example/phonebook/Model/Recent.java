package com.example.phonebook.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "recent",
foreignKeys = @ForeignKey(entity = Contact.class,
        parentColumns = "id",
        childColumns = "contactId",
        onDelete = ForeignKey.CASCADE))
public class Recent {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int contactId;
    private int otherContactId;
    private String time;
    private String type;

    public Recent(int contactId, int otherContactId, String time, String type) {
        this.contactId = contactId;
        this.otherContactId = otherContactId;
        this.time = time;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getOtherContactId() {
        return otherContactId;
    }

    public void setOtherContactId(int otherContactId) {
        this.otherContactId = otherContactId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

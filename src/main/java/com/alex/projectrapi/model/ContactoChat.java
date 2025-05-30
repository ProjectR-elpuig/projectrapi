package com.alex.projectrapi.model;

import java.util.Date;

public class ContactoChat {
    private Integer contactid;
    private String name;
    private String phoneNumber;
    private byte[] img;
    private String lastMessage;
    private Date lastMessageDate;
    private Boolean isBlocked;

    public ContactoChat(Integer contactid, String name, String phoneNumber, byte[] img, String lastMessage, Date lastMessageDate, Boolean isBlocked) {
        this.contactid = contactid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.img = img;
        this.lastMessage = lastMessage;
        this.lastMessageDate = lastMessageDate;
        this.isBlocked = isBlocked;
    }

    public Integer getContactid() {
        return contactid;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public byte[] getImg() {
        return img;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public Boolean getBlocked() { return isBlocked; }
}

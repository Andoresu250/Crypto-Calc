package com.andoresu.cryptocalc.core.contact;

import java.io.Serializable;

public class ContactModel implements Serializable {
    public String name;
    public String phone;

    public ContactModel(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}

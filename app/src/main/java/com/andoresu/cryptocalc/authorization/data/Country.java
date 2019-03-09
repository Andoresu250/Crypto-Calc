package com.andoresu.cryptocalc.authorization.data;

public class Country {

    public String name;
    public String code;
    public boolean selected = false;

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Country ){
            return code.equals(((Country) obj).code);
        }
        return false;
    }
}

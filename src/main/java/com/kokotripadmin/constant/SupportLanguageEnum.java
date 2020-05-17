package com.kokotripadmin.constant;

public enum SupportLanguageEnum {

    Korean(1);


    private final int id;

    private SupportLanguageEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}

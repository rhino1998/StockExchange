package com.stockexchange.transport;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Register extends Credentials implements Serializable {

    @JsonProperty
    private String name;

    @JsonCreator
    private Register() {
    }

    public Register(String name, String username, String pw) {
        super(username, pw);
        this.name = name;

    }

    public String getName() {
        return name;
    }

}

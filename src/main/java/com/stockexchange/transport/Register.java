package com.stockexchange.transport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class Register extends Credentials implements Serializable {
    @JsonProperty
    private String name;

    @JsonCreator
    private Register() {
    }

    /**
     * Creates a new Register object.
     *
     * @param name DOCUMENT ME!
     * @param username DOCUMENT ME!
     * @param pw DOCUMENT ME!
     */
    public Register(String name, String username, String pw) {
        super(username, pw);
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getName() {
        return name;
    }
}

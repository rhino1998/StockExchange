package com.stockexchange.transport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class Credentials implements Serializable {
    /**
         *
         */



    // @JsonProperty private static final long serialVersionUID = -7752851460576934357L;
    @JsonProperty
    private String username;
    @JsonProperty
    private String password;

    @JsonCreator
    protected Credentials() {
    }

    /**
     * Creates a new Credentials object.
     *
     * @param username DOCUMENT ME!
     * @param pw DOCUMENT ME!
     */
    public Credentials(String username, String pw) {
        this.password = pw;
        this.username = username;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getUsername() {
        return username;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getPassword() {
        return password;
    }
}

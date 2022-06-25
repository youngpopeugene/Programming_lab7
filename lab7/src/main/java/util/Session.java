package main.java.util;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Session implements Serializable {
    public String username;
    public String password;


    public Session(String aUsername, String aPassword) {
        username = aUsername;
        password = aPassword;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            if (password != null) {
                byte[] messageDigest = md.digest(password.getBytes());
                BigInteger no = new BigInteger(1, messageDigest);
                String hashtext = no.toString(16);
                password = hashtext;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}

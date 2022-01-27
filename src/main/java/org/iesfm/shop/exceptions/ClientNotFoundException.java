package org.iesfm.shop.exceptions;

public class ClientNotFoundException extends Exception {
    private String nif;

    public ClientNotFoundException(String nif) {
        this.nif = nif;
    }

    public String getNif() {
        return nif;
    }
}

package org.iesfm.shop.exceptions;

public class ArticleNotFoundException extends Exception{
    private int id;

    public ArticleNotFoundException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

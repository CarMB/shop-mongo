package org.iesfm.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Item {
    private int articleId;
    private String articleName;
    private int quantity;

    @JsonCreator
    public Item(
            @JsonProperty("articleId") int articleId,
            @JsonProperty("articleName") String articleName,
            @JsonProperty("quantity") int quantity) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.quantity = quantity;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return articleId == item.articleId && quantity == item.quantity && Objects.equals(articleName, item.articleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, articleName, quantity);
    }

    @Override
    public String toString() {
        return "Item{" +
                "articleId=" + articleId +
                ", articleName='" + articleName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

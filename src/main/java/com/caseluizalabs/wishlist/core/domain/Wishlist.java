package com.caseluizalabs.wishlist.core.domain;
import java.util.List;

public class Wishlist {
    private String id;
    private String clientId;
    private List<String> productIds;

    public Wishlist(String clientId, List<String> productIds) {
        this.clientId = clientId;
        this.productIds = productIds;
    }

    public Wishlist() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
}

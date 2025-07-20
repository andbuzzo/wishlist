package com.caseluizalabs.wishlist.dataprovider.repository.entity;

import com.caseluizalabs.wishlist.core.domain.Wishlist;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
@Document(collection = "wishlist")
public class WishlistEntity {
    @Id
    private String id;
    @Field("clientId")
    @Indexed(unique = true)
    private String clientId;
    @Field("productIds")
    private List<String> productIds;


    public static WishlistEntity fromDomain(Wishlist wishlist) {
        WishlistEntity doc = new WishlistEntity();
        doc.id = wishlist.getId();
        doc.clientId = wishlist.getClientId();
        doc.productIds = wishlist.getProductIds();
        return doc;
    }

    public Wishlist toDomain() {
        Wishlist wishList = new Wishlist();
        wishList.setId(this.id);
        wishList.setClientId(this.clientId);
        wishList.setProductIds(this.productIds);
        return wishList;
    }
}

package com.caseluizalabs.wishlist.core.port;

import com.caseluizalabs.wishlist.core.domain.Wishlist;

import java.util.Optional;

public interface WishlistRepository {
    void save(Wishlist wishList);
    Optional<Wishlist> findByClientId(String clientId);
    boolean isProductInWishlist(String clientId, String productId);
    void deleteByClientId(String clientId);

}

package com.caseluizalabs.wishlist.core.usecase;

public interface IsProductInWishlistUseCase {
    boolean execute(String clientId, String productId);
}

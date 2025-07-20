package com.caseluizalabs.wishlist.core.usecase;

public interface RemoveProductFromWishlistUseCase {
    void execute(String clientId, String productId);
}

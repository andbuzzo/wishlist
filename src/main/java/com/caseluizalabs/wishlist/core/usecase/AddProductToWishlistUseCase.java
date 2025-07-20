package com.caseluizalabs.wishlist.core.usecase;

public interface AddProductToWishlistUseCase {
    void execute(String clientId, String productId);
}

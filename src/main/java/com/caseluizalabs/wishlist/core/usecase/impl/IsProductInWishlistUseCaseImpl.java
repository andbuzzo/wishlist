package com.caseluizalabs.wishlist.core.usecase.impl;

import com.caseluizalabs.wishlist.core.port.WishlistRepository;
import com.caseluizalabs.wishlist.core.usecase.IsProductInWishlistUseCase;


public class IsProductInWishlistUseCaseImpl implements IsProductInWishlistUseCase {

    private final WishlistRepository wishListRepository;

    public IsProductInWishlistUseCaseImpl(WishlistRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    @Override
    public boolean execute(String clientId, String productId) {
        return wishListRepository.isProductInWishlist(clientId, productId);
    }
}

package com.caseluizalabs.wishlist.core.usecase.impl;

import com.caseluizalabs.wishlist.core.exception.WishlistEmptyException;
import com.caseluizalabs.wishlist.core.exception.WishlistNotFoundException;
import com.caseluizalabs.wishlist.core.port.WishlistRepository;
import com.caseluizalabs.wishlist.core.usecase.FindAllProductsInWishlistUseCase;

import java.util.List;

import static com.caseluizalabs.wishlist.core.common.Messages.EMPTY_WISHLIST_MESSAGE;
import static com.caseluizalabs.wishlist.core.common.Messages.WISHLIST_NOTFOUND_MESSAGE;

public class FindAllProductsInWishlistUseCaseImpl implements FindAllProductsInWishlistUseCase {

    private final WishlistRepository wishListRepository;

    public FindAllProductsInWishlistUseCaseImpl(WishlistRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    @Override
    public List<String> execute(String clientId) {
        return wishListRepository.findByClientId(clientId)
                .map(wishList -> {
                    if (wishList.getProductIds() == null || wishList.getProductIds().isEmpty()) {
                        throw new WishlistEmptyException(EMPTY_WISHLIST_MESSAGE);
                    }
                    return wishList.getProductIds();
                }).orElseThrow(() -> new WishlistNotFoundException(WISHLIST_NOTFOUND_MESSAGE));
    }
}

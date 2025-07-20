package com.caseluizalabs.wishlist.core.usecase.impl;

import com.caseluizalabs.wishlist.core.domain.Wishlist;
import com.caseluizalabs.wishlist.core.exception.ProductAlreadyInWishlistException;
import com.caseluizalabs.wishlist.core.exception.WishlistLimitExceededException;
import com.caseluizalabs.wishlist.core.port.WishlistRepository;
import com.caseluizalabs.wishlist.core.usecase.AddProductToWishlistUseCase;
import com.caseluizalabs.wishlist.core.usecase.IsProductInWishlistUseCase;

import java.util.ArrayList;

import static com.caseluizalabs.wishlist.core.common.Config.MAX_WISHLIST_PRODUCTS;
import static com.caseluizalabs.wishlist.core.common.Messages.WISHLIST_LIMIT_PRODUCT_EXCEEDED_MESSAGE;
import static com.caseluizalabs.wishlist.core.common.Messages.WISHLIST_PRODUCT_EXISTS_MESSAGE;

public class AddProductToWishlistUseCaseImpl implements AddProductToWishlistUseCase {

    private final WishlistRepository wishListRepository;
    private final IsProductInWishlistUseCase isProductInWishlistUseCase;

    public AddProductToWishlistUseCaseImpl(WishlistRepository wishlistRepository,
                                           IsProductInWishlistUseCase isProductInWishlistUseCase) {
        this.wishListRepository = wishlistRepository;
        this.isProductInWishlistUseCase = isProductInWishlistUseCase;
    }
    @Override
    public void execute(String clientId, String productId) {
        if (isProductInWishlistUseCase.execute(clientId, productId)) {
            throw new ProductAlreadyInWishlistException(WISHLIST_PRODUCT_EXISTS_MESSAGE);
        }

        var wishlist = wishListRepository.findByClientId(clientId)
                .orElseGet(() -> new Wishlist(clientId, new ArrayList<>()));

        if (wishlist.getProductIds().size() >= MAX_WISHLIST_PRODUCTS) {
            throw new WishlistLimitExceededException(WISHLIST_LIMIT_PRODUCT_EXCEEDED_MESSAGE);
        }

        wishlist.getProductIds().add(productId);
        wishListRepository.save(wishlist);
    }
}

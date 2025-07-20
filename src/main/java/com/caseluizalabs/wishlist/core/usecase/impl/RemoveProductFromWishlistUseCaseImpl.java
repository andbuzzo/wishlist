package com.caseluizalabs.wishlist.core.usecase.impl;

import com.caseluizalabs.wishlist.core.domain.Wishlist;
import com.caseluizalabs.wishlist.core.exception.ProductNotInWishlistException;
import com.caseluizalabs.wishlist.core.exception.WishlistNotFoundException;
import com.caseluizalabs.wishlist.core.port.WishlistRepository;
import com.caseluizalabs.wishlist.core.usecase.IsProductInWishlistUseCase;
import com.caseluizalabs.wishlist.core.usecase.RemoveProductFromWishlistUseCase;

import static com.caseluizalabs.wishlist.core.common.Messages.PRODUCT_NOT_FOUND_IN_WISHLIST;
import static com.caseluizalabs.wishlist.core.common.Messages.WISHLIST_NOTFOUND_MESSAGE;
public class RemoveProductFromWishlistUseCaseImpl implements RemoveProductFromWishlistUseCase {

    private final WishlistRepository wishListRepository;
    private final IsProductInWishlistUseCase isProductInWishlistUseCase;

    public RemoveProductFromWishlistUseCaseImpl(WishlistRepository wishListRepository, IsProductInWishlistUseCase isProductInWishlistUseCase) {
        this.wishListRepository = wishListRepository;
        this.isProductInWishlistUseCase = isProductInWishlistUseCase;
    }
    @Override
    public void execute(String clientId, String productId) {
        Wishlist wishList = wishListRepository.findByClientId(clientId)
                .orElseThrow(() -> new WishlistNotFoundException(WISHLIST_NOTFOUND_MESSAGE));

        if (!isProductInWishlistUseCase.execute(clientId, productId)) {
            throw new ProductNotInWishlistException(PRODUCT_NOT_FOUND_IN_WISHLIST);
        }

        wishList.getProductIds().remove(productId);
        wishListRepository.save(wishList);

    }
}

package com.caseluizalabs.wishlist.core.usecase;

import java.util.List;
public interface FindAllProductsInWishlistUseCase {
    List<String> execute(String clientId);
}

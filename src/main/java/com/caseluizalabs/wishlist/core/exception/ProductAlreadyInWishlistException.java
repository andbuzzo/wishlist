package com.caseluizalabs.wishlist.core.exception;

public class ProductAlreadyInWishlistException extends RuntimeException{

    public ProductAlreadyInWishlistException(String message) {
        super(message);
    }

}

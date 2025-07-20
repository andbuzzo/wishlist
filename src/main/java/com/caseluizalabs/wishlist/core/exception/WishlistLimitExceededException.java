package com.caseluizalabs.wishlist.core.exception;

public class WishlistLimitExceededException extends RuntimeException{

    public WishlistLimitExceededException(String message) {
        super(message);
    }

}

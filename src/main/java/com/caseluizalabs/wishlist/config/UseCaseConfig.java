package com.caseluizalabs.wishlist.config;

import com.caseluizalabs.wishlist.core.port.WishlistRepository;
import com.caseluizalabs.wishlist.core.usecase.AddProductToWishlistUseCase;
import com.caseluizalabs.wishlist.core.usecase.FindAllProductsInWishlistUseCase;
import com.caseluizalabs.wishlist.core.usecase.IsProductInWishlistUseCase;
import com.caseluizalabs.wishlist.core.usecase.RemoveProductFromWishlistUseCase;
import com.caseluizalabs.wishlist.core.usecase.impl.AddProductToWishlistUseCaseImpl;
import com.caseluizalabs.wishlist.core.usecase.impl.FindAllProductsInWishlistUseCaseImpl;
import com.caseluizalabs.wishlist.core.usecase.impl.IsProductInWishlistUseCaseImpl;
import com.caseluizalabs.wishlist.core.usecase.impl.RemoveProductFromWishlistUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public AddProductToWishlistUseCase addProductToWishlistUseCase(WishlistRepository wishlistRepository,
                                                                   IsProductInWishlistUseCase isProductInWishlistUseCase) {
        return new AddProductToWishlistUseCaseImpl(wishlistRepository, isProductInWishlistUseCase);
    }
    @Bean
    public FindAllProductsInWishlistUseCase findAllProductsInWishlistUseCase(WishlistRepository wishlistRepository) {
        return new FindAllProductsInWishlistUseCaseImpl(wishlistRepository);
    }
    @Bean
    public IsProductInWishlistUseCase isProductInWishlistUseCase(WishlistRepository wishlistRepository) {
        return new IsProductInWishlistUseCaseImpl(wishlistRepository);
    }
    @Bean
    public RemoveProductFromWishlistUseCase removeProductFromWishListUseCase(WishlistRepository wishlistRepository,
                                                                             IsProductInWishlistUseCase isProductInWishlistUseCase) {
        return new RemoveProductFromWishlistUseCaseImpl(wishlistRepository, isProductInWishlistUseCase);
    }

}

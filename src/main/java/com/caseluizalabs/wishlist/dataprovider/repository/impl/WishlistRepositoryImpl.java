package com.caseluizalabs.wishlist.dataprovider.repository.impl;

import com.caseluizalabs.wishlist.core.domain.Wishlist;
import com.caseluizalabs.wishlist.core.port.WishlistRepository;
import com.caseluizalabs.wishlist.dataprovider.repository.WishlistMongoRepository;
import com.caseluizalabs.wishlist.dataprovider.repository.entity.WishlistEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WishlistRepositoryImpl implements WishlistRepository {

    private final WishlistMongoRepository wishListMongoRepository;

    public WishlistRepositoryImpl(WishlistMongoRepository wishListMongoRepository) {
        this.wishListMongoRepository = wishListMongoRepository;
    }

    @Override
    public void save(Wishlist wishList) {
        wishListMongoRepository.save(WishlistEntity.fromDomain(wishList));
    }

    @Override
    public Optional<Wishlist> findByClientId(String clientId) {
        return wishListMongoRepository.findByClientId(clientId)
                .map(WishlistEntity::toDomain);
    }

    @Override
    public boolean isProductInWishlist(String clientId, String productId) {
        return wishListMongoRepository.existsByClientIdAndProductIdsContains(clientId, productId);
    }

    @Override
    public void deleteByClientId(String clientId) {
        wishListMongoRepository.deleteByClientId(clientId);
    }
}

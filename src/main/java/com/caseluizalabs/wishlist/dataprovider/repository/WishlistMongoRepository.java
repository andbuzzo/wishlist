package com.caseluizalabs.wishlist.dataprovider.repository;

import com.caseluizalabs.wishlist.dataprovider.repository.entity.WishlistEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
public interface WishlistMongoRepository extends MongoRepository<WishlistEntity, String> {
    Optional<WishlistEntity> findByClientId(String clientId);
    boolean existsByClientIdAndProductIdsContains(String clientId, String productId);
    void deleteByClientId(String clientId);
}

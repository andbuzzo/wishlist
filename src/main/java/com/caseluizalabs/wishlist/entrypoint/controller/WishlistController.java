package com.caseluizalabs.wishlist.entrypoint.controller;

import com.caseluizalabs.wishlist.core.usecase.AddProductToWishlistUseCase;
import com.caseluizalabs.wishlist.core.usecase.FindAllProductsInWishlistUseCase;
import com.caseluizalabs.wishlist.core.usecase.IsProductInWishlistUseCase;
import com.caseluizalabs.wishlist.core.usecase.RemoveProductFromWishlistUseCase;
import com.caseluizalabs.wishlist.entrypoint.dto.WishlistRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
@Tag(name = "Wishlist", description = "Operations related to the customer's wishlist")
public class WishlistController {

    private final IsProductInWishlistUseCase isProductInWishlistUseCase;
    private final AddProductToWishlistUseCase addProductToWishlistUseCase;
    private final RemoveProductFromWishlistUseCase removeProductFromWishListUseCase;
    private final FindAllProductsInWishlistUseCase findAllProductsInWishlistUseCase;

    public WishlistController(IsProductInWishlistUseCase isProductInWishlistUseCase, AddProductToWishlistUseCase addProductToWishlistUseCase, RemoveProductFromWishlistUseCase removeProductFromWishListUseCase, FindAllProductsInWishlistUseCase findAllProductsInWishlistUseCase) {
        this.isProductInWishlistUseCase = isProductInWishlistUseCase;
        this.addProductToWishlistUseCase = addProductToWishlistUseCase;
        this.removeProductFromWishListUseCase = removeProductFromWishListUseCase;
        this.findAllProductsInWishlistUseCase = findAllProductsInWishlistUseCase;
    }

    @Operation(summary = "Check if a product exists in a client's wishlist")
    @GetMapping("/{clientId}/product/{productId}/exists")
    public ResponseEntity<Boolean> isProductInWishlist(@PathVariable @NotBlank String clientId,
                                                       @PathVariable @NotBlank String productId) {
        boolean exists = isProductInWishlistUseCase.execute(clientId, productId);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Add a product to a client's wishlist")
    @PostMapping
    public ResponseEntity<Void> addProductToWishlist(@Valid @RequestBody WishlistRequest request) {
        addProductToWishlistUseCase.execute(request.getClientId(), request.getProductId());
        URI location = URI.create(String.format("/api/v1/wishlist/%s/products/%s", request.getClientId(), request.getProductId()));
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Remove a product from a client's wishlist")
    @DeleteMapping
    public ResponseEntity<Void> removeProductFromWishlist(@Valid @RequestBody WishlistRequest request) {
        removeProductFromWishListUseCase.execute(request.getClientId(), request.getProductId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Retrieve all products in a client's wishlist")
    @GetMapping("/{clientId}")
    public ResponseEntity<List<String>> getWishlist(@PathVariable @NotBlank String clientId) {
        return ResponseEntity.ok(findAllProductsInWishlistUseCase.execute(clientId));
    }

}

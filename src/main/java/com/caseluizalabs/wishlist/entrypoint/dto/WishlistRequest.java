package com.caseluizalabs.wishlist.entrypoint.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
public class WishlistRequest {

    @NotBlank
    @Schema(description = "ID of the client", example = "client123")
    private String clientId;
    @NotBlank
    @Schema(description = "ID of the product", example = "product456")
    private String productId;

    public WishlistRequest(String clientId, String productId) {
        this.clientId = clientId;
        this.productId = productId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

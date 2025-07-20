package com.caseluizalabs.wishlist.bdd.steps;

import com.caseluizalabs.wishlist.core.domain.Wishlist;
import com.caseluizalabs.wishlist.core.port.WishlistRepository;
import com.caseluizalabs.wishlist.entrypoint.dto.WishlistRequest;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import java.util.ArrayList;
import java.util.List;

public class AddProductToWishlistSteps {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private WishlistRepository wishlistRepository;

    private String clientId;
    private String productId;
    private WishlistRequest productRequest;
    private ResponseSpec responseSpec;

    @Before
    public void clearWishlist() {
        String clientId = "client123";
        if (wishlistRepository.findByClientId(clientId).isPresent()) {
            wishlistRepository.deleteByClientId(clientId);
        }
    }

    @Given("a client with ID {string}")
    public void aClientWithId(String id) {
        this.clientId = id;
        if (wishlistRepository.findByClientId(clientId).isEmpty()) {
            Wishlist wishlist = new Wishlist();
            wishlist.setClientId(clientId);
            wishlist.setProductIds(new ArrayList<>());
            wishlistRepository.save(wishlist);
        }
    }

    @And("a product with ID {string}")
    public void aProductWithId(String id) {
        this.productId = id;
    }

    @When("the client adds the product to the wishlist")
    public void addProductToWishlist() {

        productRequest = new WishlistRequest(clientId, productId);
        responseSpec = webTestClient.post()
                .uri("/api/v1/wishlist")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productRequest)
                .exchange()
                .expectStatus().isCreated();
    }

    @Then("the wishlist should contain the product")
    public void wishlistShouldContainProduct() {
        webTestClient.get()
                .uri("/api/v1/wishlist/{clientId}", clientId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[?(@ == '" + productId + "')]").exists();
    }

    @When("the client tries to add the same product again")
    public void addSameProductAgain() {
        responseSpec = webTestClient.post()
                .uri("/api/v1/wishlist")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productRequest)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Then("an exception should be thrown indicating the product already exists")
    public void exceptionThrownForExistingProduct() {
        responseSpec.expectBody()
                .jsonPath("$.message").isEqualTo("Product is already in the wishlist.");
    }

    @Given("a client with ID {string} and the wishlist contains 20 products")
    public void clientWishlistHas20Products(String clientId) {
        this.clientId = clientId;
        wishlistRepository.deleteByClientId(clientId);
        List<String> products = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            products.add("prod" + i);
        }
        Wishlist wishlist = new Wishlist(clientId, products);
        wishlistRepository.save(wishlist);
    }

    @When("the client tries to add the product with ID {string} to the wishlist")
    public void clientTriesToAddProductToWishlist(String productId) {
        this.productId = productId;
        WishlistRequest request = new WishlistRequest(clientId, productId);
        responseSpec = webTestClient.post()
                .uri("/api/v1/wishlist")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange();
    }

    @Then("an exception should be thrown indicating the wishlist limit has been reached")
    public void exceptionThrownWishlistLimitReached() {
        responseSpec.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Wishlist limit of 20 products has been reached.");
    }
}

package com.caseluizalabs.wishlist.bdd.steps;

import com.caseluizalabs.wishlist.core.domain.Wishlist;
import com.caseluizalabs.wishlist.core.port.WishlistRepository;
import com.caseluizalabs.wishlist.core.usecase.impl.IsProductInWishlistUseCaseImpl;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class ProductExistsIntoWishlistSteps {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private IsProductInWishlistUseCaseImpl isProductInWishlistUseCase;

    private String clientId;
    private String productId;
    private boolean result;

    @Before
    public void clearWishlistForClient() {
        wishlistRepository.deleteByClientId("client123");
        wishlistRepository.deleteByClientId("client999");
    }
    @Given("a wishlist exists for client with ID {string}")
    public void aWishlistExistsForClientWithId(String clientId) {
        this.clientId = clientId;
        var wishlist = new Wishlist(clientId, List.of());
        wishlistRepository.save(wishlist);
    }

    @And("the product with ID {string} is in the client's wishlist")
    public void theProductWithIdIsInTheClientSWishlist(String productId) {
        this.productId = productId;
        var wishlist = wishlistRepository.findByClientId(clientId).orElse(null);
        if (wishlist == null) {
            wishlist = new Wishlist(clientId, List.of(productId));
        } else {
            wishlist.getProductIds().add(productId);
        }
        wishlistRepository.save(wishlist);
    }

    @And("the product with ID {string} is not in the client's wishlist")
    public void theProductWithIdIsNotInTheClientSWishlist(String productId) {
        this.productId = productId;
    }

    @Given("no wishlist exists for client with ID {string}")
    public void noWishlistExistsForClientWithId(String clientId) {
        this.clientId = clientId;
    }

    @When("I check if the product is in the wishlist for client {string}")
    public void iCheckIfTheProductIsInTheWishlistForClient(String clientId) {
        result = isProductInWishlistUseCase.execute(clientId, productId);
    }

    @Then("the result should be true")
    public void theResultShouldBeTrue() {
        Assertions.assertTrue(result);
    }
    @When("I check if the product is not in the wishlist for client {string}")
    public void iCheckIfTheProductIsNotInTheWishlistForClient(String clientId) {
        result = isProductInWishlistUseCase.execute(clientId, productId);
    }

    @Then("the result should be false")
    public void theResultShouldBeFalse() {
        Assertions.assertFalse(result);
    }
}

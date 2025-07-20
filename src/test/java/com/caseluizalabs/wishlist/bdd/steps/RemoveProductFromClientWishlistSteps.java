package com.caseluizalabs.wishlist.bdd.steps;
import com.caseluizalabs.wishlist.core.domain.Wishlist;
import com.caseluizalabs.wishlist.core.exception.ProductNotInWishlistException;
import com.caseluizalabs.wishlist.core.exception.WishlistNotFoundException;
import com.caseluizalabs.wishlist.core.port.WishlistRepository;
import com.caseluizalabs.wishlist.core.usecase.RemoveProductFromWishlistUseCase;
import io.cucumber.java.en.*;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RemoveProductFromClientWishlistSteps {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private RemoveProductFromWishlistUseCase removeProductFromWishlistUseCase;

    private String clientId;
    private String productId;
    private Exception thrownException;

    @Given("a wishlist exists for client {string}")
    public void aWishlistExistsForClientWithID(String clientId) {
        this.clientId = clientId;
        Wishlist wishlist = new Wishlist(clientId, new ArrayList<>());
        wishlistRepository.save(wishlist);
    }

    @And("the product {string} is in the client's wishlist")
    public void theProductIsInTheClientsWishlist(String productId) {
        this.productId = productId;
        Wishlist wishlist = wishlistRepository.findByClientId(clientId).orElseThrow();
        wishlist.getProductIds().add(productId);
        wishlistRepository.save(wishlist);
    }

    @And("the product {string} is not in the client's wishlist")
    public void theProductIsNotInTheClientsWishlist(String productId) {
        this.productId = productId;
        Wishlist wishlist = wishlistRepository.findByClientId(clientId).orElseThrow();
        wishlist.getProductIds().remove(productId); // Ensure not present
        wishlistRepository.save(wishlist);
    }

    @Given("no wishlist exists for client {string}")
    public void noWishlistExistsForClientWithID(String clientId) {
        this.clientId = clientId;
        wishlistRepository.deleteByClientId(clientId);
    }

    @Given("a wishlist with products exists for client {string}")
    public void aWishlistWithProductsExistsForClient(String clientId) {
        this.clientId = clientId;
        List<String> products = new ArrayList<>();
        products.add("productABC");
        Wishlist wishlist = new Wishlist(clientId, products);
        wishlistRepository.save(wishlist);
    }

    @And("the product {string} is not in the client wishlist")
    public void theProductIsNotInTheClientWishlist(String productId) {
        this.productId = productId;
    }

    @When("I remove the product with ID {string} from the wishlist for client {string}")
    public void iRemoveTheProductFromTheWishlist(String productId, String clientId) {
        this.productId = productId;
        this.clientId = clientId;
        try {
            removeProductFromWishlistUseCase.execute(clientId, productId);
        } catch (Exception e) {
            this.thrownException = e;
        }
    }

    @When("I try to remove the product with ID {string} from the wishlist for client {string}")
    public void iTryToRemoveTheProductFromTheWishlist(String productId, String clientId) {
        iRemoveTheProductFromTheWishlist(productId, clientId);
    }

    @Then("the wishlist should not contain the product {string}")
    public void theWishlistShouldNotContainTheProduct(String productId) {
        Wishlist wishlist = wishlistRepository.findByClientId(clientId).orElseThrow();
        Assertions.assertFalse(wishlist.getProductIds().contains(productId));
    }

    @Then("a ProductNotInWishlistException should be thrown")
    public void aProductNotInWishlistExceptionShouldBeThrown() {
        Assertions.assertTrue(thrownException instanceof ProductNotInWishlistException);
    }

    @Then("a WishlistNotFoundException should be thrown")
    public void aWishlistNotFoundExceptionShouldBeThrown() {
        Assertions.assertTrue(thrownException instanceof WishlistNotFoundException);
    }

}

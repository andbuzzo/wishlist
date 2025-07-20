package com.caseluizalabs.wishlist.bdd.steps;

import com.caseluizalabs.wishlist.core.domain.Wishlist;
import com.caseluizalabs.wishlist.core.exception.WishlistEmptyException;
import com.caseluizalabs.wishlist.core.exception.WishlistNotFoundException;
import com.caseluizalabs.wishlist.core.port.WishlistRepository;
import com.caseluizalabs.wishlist.core.usecase.FindAllProductsInWishlistUseCase;
import com.caseluizalabs.wishlist.core.usecase.impl.FindAllProductsInWishlistUseCaseImpl;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.List;
public class FindAllProductsInWishlistSteps {
    private final WishlistRepository wishlistRepository;
    private final FindAllProductsInWishlistUseCase useCase;

    private String clientId;
    private List<String> result;
    private Exception exception;

    public FindAllProductsInWishlistSteps(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
        this.useCase = new FindAllProductsInWishlistUseCaseImpl(wishlistRepository);
    }

    @Given("a new client with ID {string}")
    public void aClientWithId(String id) {
        this.clientId = id;
    }

    @And("the wishlist contains the products {string} and {string}")
    public void theWishlistContainsProducts(String product1, String product2) {
        var wishlist = wishlistRepository.findByClientId(clientId).orElse(new Wishlist());
        wishlist.setClientId(clientId);
        wishlist.setProductIds(List.of(product1, product2));
        wishlistRepository.save(wishlist);
    }

    @And("the wishlist is empty")
    public void theWishlistIsEmpty() {
        wishlistRepository.save(new Wishlist(clientId, List.of()));
    }

    @When("the client requests all products in the wishlist")
    public void theClientRequestsAllProductsInTheWishlist() {
        try {
            result = useCase.execute(clientId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the response should contain {string} and {string}")
    public void theResponseShouldContain(String product1, String product2) {
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.contains(product1));
        Assertions.assertTrue(result.contains(product2));
    }

    @When("an error {string} should be returned")
    public void anErrorShouldBeReturned(String expectedMessage) {
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(expectedMessage, exception.getMessage());

        if (expectedMessage.equals("Wishlist is empty.")) {
            Assertions.assertTrue(exception instanceof WishlistEmptyException);
        } else if (expectedMessage.equals("Wishlist not found.")) {
            Assertions.assertTrue(exception instanceof WishlistNotFoundException);
        }
    }
}

Feature: Remove product from client's wishlist

  Scenario: Successfully remove a product from the wishlist
    Given a wishlist exists for client "client123"
    And the product "productABC" is in the client's wishlist
    When I remove the product with ID "productABC" from the wishlist for client "client123"
    Then the wishlist should not contain the product "productABC"

  Scenario: Attempt to remove a product that is not in the wishlist
    Given a wishlist with products exists for client "client123"
    And the product "productXYZ" is not in the client wishlist
    When I try to remove the product with ID "productXYZ" from the wishlist for client "client123"
    Then a ProductNotInWishlistException should be thrown

  Scenario: Attempt to remove a product from a non-existing wishlist
    Given no wishlist exists for client "client999"
    When I try to remove the product with ID "productABC" from the wishlist for client "client999"
    Then a WishlistNotFoundException should be thrown
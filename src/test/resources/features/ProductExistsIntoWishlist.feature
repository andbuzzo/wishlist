Feature: Check if a product is in the client's wishlist

  Scenario: Product is in the client's wishlist
    Given a wishlist exists for client with ID "client123"
    And the product with ID "productABC" is in the client's wishlist
    When I check if the product is in the wishlist for client "client123"
    Then the result should be true

  Scenario: Product is not in the client's wishlist
    Given a wishlist exists for client with ID "client123"
    And the product with ID "productXYAZ" is not in the client's wishlist
    When I check if the product is not in the wishlist for client "client123"
    Then the result should be false
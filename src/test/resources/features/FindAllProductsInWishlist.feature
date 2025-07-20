Feature: Find all products in a client's wishlist

  Scenario: Successfully retrieve all products from wishlist
    Given a new client with ID "client123"
    And the wishlist contains the products "productABC" and "productXYZ"
    When the client requests all products in the wishlist
    Then the response should contain "productABC" and "productXYZ"

  Scenario: Wishlist is empty
    Given a new client with ID "client123"
    And the wishlist is empty
    When the client requests all products in the wishlist
    Then an error "Wishlist is empty." should be returned

  Scenario: Wishlist does not exist for client
    Given a new client with ID "nonexistentClient"
    When the client requests all products in the wishlist
    Then an error "Wishlist not found." should be returned
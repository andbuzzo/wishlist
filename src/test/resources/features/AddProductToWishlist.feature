Feature: Add product to wishlist

  Scenario: Successfully add product to client wishlist
    Given a client with ID "client123"
    And a product with ID "productABC"
    When the client adds the product to the wishlist
    Then the wishlist should contain the product

  Scenario: Fail to add a product that already exists in the wishlist
    Given a client with ID "client123"
    And a product with ID "productABC"
    When the client adds the product to the wishlist
    Then the wishlist should contain the product
    When the client tries to add the same product again
    Then an exception should be thrown indicating the product already exists

  Scenario: Fail to add product when wishlist has reached maximum limit
      Given a client with ID "client123" and the wishlist contains 20 products
      When the client tries to add the product with ID "productXYZ" to the wishlist
      Then an exception should be thrown indicating the wishlist limit has been reached
@smoke @cart
Feature: Shopping Cart
  As a user
  I want to manage items in my cart
  So that I can prepare for checkout

  Scenario: Add product to cart
    Given I am on the home page
    When I search for "laptop"
    And I click on the first product
    And I add the product to the cart
    Then the cart should show the product

  Scenario: Add multiple different products to cart
    Given I am on the home page
    When I add product "laptop" to cart
    And I add product "phone" to cart
    Then the cart should contain "2" items

  Scenario: Add same product multiple times
    Given my cart has a product
    When I add the same product again
    Then the cart quantity should increase

@regression @cart
  Scenario: Update quantity
    Given my cart has a product
    When I update quantity to 2
    Then cart quantity should reflect 2

  Scenario: Update quantity to maximum
    Given my cart has a product
    When I update quantity to maximum allowed
    Then cart quantity should reflect maximum

  Scenario: Update quantity with invalid value
    Given my cart has a product
    When I attempt to update quantity to "0"
    Then I should see cart quantity validation error

  Scenario: Update quantity with negative value
    Given my cart has a product
    When I attempt to update quantity to "-1"
    Then I should see cart quantity validation error

  Scenario: Remove item
    Given my cart has a product
    When I remove the product
    Then the cart should be empty

  Scenario: Remove one item from multiple items
    Given my cart has "2" different products
    When I remove one product
    Then the cart should contain "1" item

  Scenario: Clear entire cart
    Given my cart has multiple products
    When I clear the entire cart
    Then the cart should be empty

  Scenario: View cart total calculation
    Given my cart has products
    When I view the cart
    Then I should see correct cart total

  Scenario: Continue shopping from cart
    Given my cart has a product
    When I click continue shopping
    Then I should be redirected to home page

@regression @products
Feature: Product Details Page
  As a user
  I want to view product details
  So that I can make an informed purchase decision

  Scenario: Open product details and verify information
    Given I am on the home page
    When I search for "laptop"
    And I click on the first product
    Then I should see product title, price, and add-to-cart button

  Scenario: View product with all details
    Given I opened a product from search results
    Then I should see product description
    And I should see product specifications
    And I should see product images

  Scenario: Add product to cart with quantity
    Given I opened a product from search results
    When I add quantity "3" to cart
    Then I should see success message with quantity "3"

  Scenario: Add product with invalid quantity zero
    Given I opened a product from search results
    When I attempt to add quantity "0" to cart
    Then I should see product quantity validation error

  Scenario: Add product with invalid quantity negative
    Given I opened a product from search results
    When I attempt to add quantity "-1" to cart
    Then I should see product quantity validation error

  Scenario: Add product with quantity exceeding stock
    Given I opened a product from search results
    When I attempt to add quantity exceeding stock limit
    Then I should see stock limit validation message

  Scenario: Navigate back to search results
    Given I opened a product from search results
    When I navigate back to search results
    Then I should see the search results page

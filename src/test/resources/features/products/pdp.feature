@regression @products @predefined
Feature: Product Details Page
  As a user
  I want to view product details
  So that I can make an informed purchase decision

  Background:
    Given I open url "https://nop-qa.portnov.com"
    When I type "laptop" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".product-item" to be present
    When I click on element with css ".product-title a"
    Then I wait for element with css ".product-name h1" to be present

  @predefined1
  Scenario: Open product details and verify information
    Then element with css ".product-name h1" should be displayed
    And element with css ".product-price span" should be displayed
    And element with id "add-to-cart-button" should be present

  @predefined2
  Scenario: View product with all details
    Then element with css ".product-name h1" should be displayed

  @predefined3
  Scenario: Add product to cart with quantity
    When I clear element with id "addtocart_EnteredQuantity"
    And I type "3" into element with id "addtocart_EnteredQuantity"
    And I click on element with id "add-to-cart-button"
    Then I wait for element with css ".bar-notification.success" to be present
    And element with css ".bar-notification.success" should be displayed

  @predefined4
  Scenario: Add product with invalid quantity zero
    When I clear element with id "addtocart_EnteredQuantity"
    And I type "0" into element with id "addtocart_EnteredQuantity"
    And I click on element with id "add-to-cart-button"
    Then I wait for 2 seconds

  @predefined5
  Scenario: Add product with invalid quantity negative
    When I clear element with id "addtocart_EnteredQuantity"
    And I type "-1" into element with id "addtocart_EnteredQuantity"
    And I click on element with id "add-to-cart-button"
    Then I wait for 2 seconds

  @predefined6
  Scenario: Add product with quantity exceeding stock
    When I clear element with id "addtocart_EnteredQuantity"
    And I type "9999" into element with id "addtocart_EnteredQuantity"
    And I click on element with id "add-to-cart-button"
    Then I wait for 2 seconds

  @predefined7
  Scenario: Navigate back to search results
    When I navigate back
    Then I wait for element with css ".product-item" to be present
    And I should see at least 1 elements with xpath "//div[@class='product-item']"

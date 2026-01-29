@smoke @cart @predefined
Feature: Shopping Cart
  As a user
  I want to manage items in my cart
  So that I can prepare for checkout

  Background:
    Given I open url "https://nop-qa.portnov.com"

  @predefined1
  Scenario: Add product to cart
    When I type "laptop" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".product-item" to be present
    When I click on element with css ".product-title a"
    Then I wait for element with id "add-to-cart-button" to be present
    When I click on element with id "add-to-cart-button"
    Then I wait for 3 seconds
    When I click on element with id "topcartlink"
    Then I wait for element with css ".cart-item-row" to be present
    And element with css ".cart-item-row" should be displayed

  @predefined2
  Scenario: Add multiple different products to cart
    When I type "laptop" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".product-item" to be present
    When I click on element with css ".product-title a"
    Then I wait for element with id "add-to-cart-button" to be present
    When I click on element with id "add-to-cart-button"
    Then I wait for 3 seconds
    When I open url "https://nop-qa.portnov.com"
    And I type "phone" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".product-item" to be present
    When I click on element with css ".product-title a"
    Then I wait for element with id "add-to-cart-button" to be present
    When I click on element with id "add-to-cart-button"
    Then I wait for 3 seconds
    When I click on element with id "topcartlink"
    Then I should see 2 elements with xpath "//tr[@class='cart-item-row']"

  @regression @cart @predefined
  @predefined3
  Scenario: Update quantity
    When I type "laptop" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".product-item" to be present
    When I click on element with css ".product-title a"
    Then I wait for element with id "add-to-cart-button" to be present
    When I click on element with id "add-to-cart-button"
    Then I wait for 3 seconds
    When I click on element with id "topcartlink"
    Then I wait for element with id "itemquantity" to be present
    When I clear element with id "itemquantity"
    And I type "2" into element with id "itemquantity"
    And I click on element with id "updatecart"
    Then I wait for 2 seconds

  @predefined4
  Scenario: Update quantity with invalid value zero
    When I type "laptop" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".product-item" to be present
    When I click on element with css ".product-title a"
    Then I wait for element with id "add-to-cart-button" to be present
    When I click on element with id "add-to-cart-button"
    Then I wait for 3 seconds
    When I click on element with id "topcartlink"
    Then I wait for element with id "itemquantity" to be present
    When I clear element with id "itemquantity"
    And I type "0" into element with id "itemquantity"
    And I click on element with id "updatecart"
    Then I wait for 2 seconds

  @predefined5
  Scenario: Remove item from cart
    When I type "laptop" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".product-item" to be present
    When I click on element with css ".product-title a"
    Then I wait for element with id "add-to-cart-button" to be present
    When I click on element with id "add-to-cart-button"
    Then I wait for 3 seconds
    When I click on element with id "topcartlink"
    Then I wait for element with css "button.remove-btn" to be present
    When I click on element with css "button.remove-btn"
    Then I wait for element with css ".no-data" to be present
    And element with css ".no-data" should be displayed

  @predefined6
  Scenario: View cart total
    When I type "laptop" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".product-item" to be present
    When I click on element with css ".product-title a"
    Then I wait for element with id "add-to-cart-button" to be present
    When I click on element with id "add-to-cart-button"
    Then I wait for 3 seconds
    When I click on element with id "topcartlink"
    Then I wait for element with css ".order-total .value-summary" to be present
    And element with css ".order-total .value-summary" should be displayed

@e2e @account
Feature: Order History
  As a registered user
  I want to view my order history
  So that I can track my purchases

  Scenario: Order appears in order history
    Given I completed a purchase
    When I navigate to order history
    Then the last order should be visible

  Scenario: View order details from history
    Given I completed a purchase
    And I navigate to order history
    When I click on an order
    Then I should see order details
    And I should see order items
    And I should see order total

  Scenario: Verify order status in history
    Given I completed a purchase
    When I navigate to order history
    Then I should see order status

  Scenario: View multiple orders in history
    Given I completed multiple purchases
    When I navigate to order history
    Then I should see all orders listed
    And orders should be sorted by date

  Scenario: Reorder from order history
    Given I completed a purchase
    And I navigate to order history
    When I click reorder
    Then the products should be added to cart

  Scenario: Download invoice from order history
    Given I completed a purchase
    And I navigate to order history
    When I click download invoice
    Then invoice should be downloaded

  Scenario: Order history empty for new user
    Given I am logged in
    And I have no previous orders
    When I navigate to order history
    Then I should see empty order history message

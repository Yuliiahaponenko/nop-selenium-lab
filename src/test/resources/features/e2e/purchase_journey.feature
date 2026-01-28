@e2e
Feature: Complete Purchase Journey
  As a user
  I want to complete a full purchase flow
  So that I can buy products end-to-end

  Scenario: Complete purchase journey
    Given I am logged in
    When I search for "laptop"
    And I click on the first product
    And I add the product to the cart
    And I complete checkout with valid billing and shipping details
    And I navigate to order history
    Then the last order should be visible

@e2e @checkout
Feature: Registered User Checkout
  As a registered user
  I want to complete checkout
  So that I can purchase products

  Scenario: Registered checkout and order confirmation
    Given I am logged in
    And my cart has a product
    When I complete checkout with valid billing and shipping details
    Then I should see confirmation and an order number

  Scenario: Registered checkout with saved address
    Given I am logged in
    And my cart has a product
    And I have a saved address
    When I select saved address for checkout
    And I complete checkout
    Then I should see confirmation and an order number

  Scenario: Registered checkout with new address
    Given I am logged in
    And my cart has a product
    When I add new billing address during checkout
    And I complete checkout with new address
    Then I should see confirmation and an order number

  Scenario: Registered checkout with different shipping address
    Given I am logged in
    And my cart has a product
    When I use different address for shipping
    And I complete checkout
    Then I should see confirmation and an order number

  Scenario: Registered checkout without accepting terms
    Given I am logged in
    And my cart has a product
    When I attempt checkout without accepting terms and conditions
    Then I should see terms acceptance validation error

  Scenario: Registered checkout review order details
    Given I am logged in
    And my cart has a product
    When I proceed to order review
    Then I should see correct order summary
    And I should see correct product details
    And I should see correct total amount

@regression @checkout
Feature: Guest Checkout
  As a guest user
  I want to checkout without registering
  So that I can complete my purchase quickly

  Scenario: Guest checkout with valid address
    Given my cart has a product
    When I checkout as a guest with valid address details
    Then I should reach the order confirmation step

  Scenario: Guest checkout with missing required fields
    Given my cart has a product
    When I attempt checkout as guest with missing required fields
    Then I should see validation errors for required fields

  Scenario: Guest checkout with invalid email format
    Given my cart has a product
    When I attempt checkout as guest with invalid email "invalid-email"
    Then I should see checkout email format validation error

  Scenario: Guest checkout with invalid phone number
    Given my cart has a product
    When I attempt checkout as guest with invalid phone "abc"
    Then I should see phone validation error

  Scenario: Guest checkout with invalid zip code
    Given my cart has a product
    When I attempt checkout as guest with invalid zip code "abc"
    Then I should see zip code validation error

  Scenario: Guest checkout with missing city
    Given my cart has a product
    When I attempt checkout as guest with missing city
    Then I should see city validation error

  Scenario: Guest checkout with missing address
    Given my cart has a product
    When I attempt checkout as guest with missing address
    Then I should see address validation error

  Scenario: Guest checkout navigation through all steps
    Given my cart has a product
    When I proceed through billing address step
    And I proceed through shipping method step
    And I proceed through payment method step
    And I proceed through payment information step
    And I confirm the order
    Then I should reach the order confirmation step

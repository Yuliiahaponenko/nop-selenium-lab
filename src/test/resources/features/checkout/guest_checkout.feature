@regression @checkout @predefined
Feature: Guest Checkout
  As a guest user
  I want to checkout without registering
  So that I can complete my purchase quickly

  Background:
    Given I open url "https://nop-qa.portnov.com"
    When I type "laptop" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".product-item" to be present
    When I click on element with css ".product-title a"
    Then I wait for element with id "add-to-cart-button" to be present
    When I click on element with id "add-to-cart-button"
    Then I wait for 3 seconds
    When I click on element with id "topcartlink"
    Then I wait for element with id "checkout" to be present
    When I check checkbox with id "termsofservice"
    And I click on element with id "checkout"
    Then I wait for 3 seconds

  @predefined1
  Scenario: Guest checkout with valid address
    When I type "John" into element with id "BillingNewAddress_FirstName"
    And I type "Doe" into element with id "BillingNewAddress_LastName"
    And I type "john.guest@test.com" into element with id "BillingNewAddress_Email"
    And I select "United States" from dropdown with id "BillingNewAddress_CountryId"
    And I wait for 2 seconds
    And I select "New York" from dropdown with id "BillingNewAddress_StateProvinceId"
    And I type "New York" into element with id "BillingNewAddress_City"
    And I type "123 Main St" into element with id "BillingNewAddress_Address1"
    And I type "10001" into element with id "BillingNewAddress_ZipPostalCode"
    And I type "555-1234" into element with id "BillingNewAddress_PhoneNumber"
    And I click on element with css "button.new-address-next-step-button"
    Then I wait for 3 seconds
    When I click on element with id "shipping-method-next-step-button"
    Then I wait for 3 seconds
    When I click on element with id "payment-method-next-step-button"
    Then I wait for 3 seconds
    When I click on element with id "payment-info-next-step-button"
    Then I wait for 3 seconds
    When I click on element with css "button.confirm-order-next-step-button"
    Then I wait for 5 seconds
    And I should see page url contains "completed"

  @predefined2
  Scenario: Guest checkout with missing required fields
    When I click on element with css "button.new-address-next-step-button"
    Then I wait for 2 seconds
    And I should see page url contains "billingaddress"

  @predefined3
  Scenario: Guest checkout with invalid email
    When I type "John" into element with id "BillingNewAddress_FirstName"
    And I type "Doe" into element with id "BillingNewAddress_LastName"
    And I type "invalid-email" into element with id "BillingNewAddress_Email"
    And I select "United States" from dropdown with id "BillingNewAddress_CountryId"
    And I type "New York" into element with id "BillingNewAddress_City"
    And I type "123 Main St" into element with id "BillingNewAddress_Address1"
    And I type "10001" into element with id "BillingNewAddress_ZipPostalCode"
    And I type "555-1234" into element with id "BillingNewAddress_PhoneNumber"
    And I click on element with css "button.new-address-next-step-button"
    Then I wait for 2 seconds

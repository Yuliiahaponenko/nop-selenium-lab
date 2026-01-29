@e2e @predefined
Feature: Complete Purchase Journey
  As a user
  I want to complete a full purchase flow
  So that I can buy products end-to-end

  @predefined1
  Scenario: Complete purchase journey from login to checkout
    Given I open url "https://nop-qa.portnov.com/login"
    Then I wait for 2 seconds
    And I wait for element with id "Email" to be present
    When I type "testuser@example.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I wait for 1 seconds
    And I click on element with css "button.button-1.login-button"
    Then I wait for 3 seconds
    And I wait for element with xpath "//a[text()='Log out']" to be present
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
    Then I wait for 5 seconds
    And I should see page url contains "checkout"

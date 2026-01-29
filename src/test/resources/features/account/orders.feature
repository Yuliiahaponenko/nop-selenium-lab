@e2e @account @predefined
Feature: Order History
  As a registered user
  I want to view my order history
  So that I can track my purchases

  Background:
    Given I open url "https://nop-qa.portnov.com/login"
    Then I wait for 2 seconds
    And I wait for element with id "Email" to be present
    When I type "testuser@example.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I wait for 1 seconds
    And I click on element with css "button.button-1.login-button"
    Then I wait for 3 seconds
    And I wait for element with xpath "//a[text()='Log out']" to be present

  @predefined1
  Scenario: View order history page
    When I open url "https://nop-qa.portnov.com/customer/orders"
    Then I wait for 3 seconds
    And I should see page url contains "orders"

  @predefined2
  Scenario: Navigate to order history
    When I open url "https://nop-qa.portnov.com/customer/orders"
    Then I wait for 3 seconds
    And I should see page url contains "customer/orders"

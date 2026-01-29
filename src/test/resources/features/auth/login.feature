@smoke @auth @predefined
Feature: User Login
  As a user
  I want to login to my account
  So that I can access my account features

  Background:
    Given I open url "https://nop-qa.portnov.com/login"
    Then I wait for 2 seconds
    And I wait for element with id "Email" to be present

  @predefined1
  Scenario: Valid login
    When I type "testuser@example.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I wait for 1 seconds
    And I click on element with css "button.button-1.login-button"
    Then I wait for 3 seconds
    And I wait for element with xpath "//a[text()='Log out']" to be present
    And element with xpath "//a[text()='Log out']" should be displayed

  @predefined2
  Scenario: Invalid login with wrong password
    When I type "testuser@example.com" into element with id "Email"
    And I type "WrongPassword123" into element with id "Password"
    And I wait for 1 seconds
    And I click on element with css "button.button-1.login-button"
    Then I wait for 3 seconds
    And I should see page url contains "login"
    And element with xpath "//a[text()='Log out']" should not be present

  @predefined3
  Scenario: Invalid login with non-existent email
    When I type "nonexistent@example.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I wait for 1 seconds
    And I click on element with css "button.button-1.login-button"
    Then I wait for 3 seconds
    And I should see page url contains "login"
    And element with xpath "//a[text()='Log out']" should not be present

  @predefined4
  Scenario: Login with empty email
    When I type "Test123!" into element with id "Password"
    And I wait for 1 seconds
    And I click on element with css "button.button-1.login-button"
    Then I wait for 1 seconds
    And element with id "Email" should be present

  @predefined5
  Scenario: Login with empty password
    When I type "testuser@example.com" into element with id "Email"
    And I wait for 1 seconds
    And I click on element with css "button.button-1.login-button"
    Then I wait for 1 seconds
    And element with id "Password" should be present

  @predefined6
  Scenario: Login with invalid email format
    When I type "invalid-email" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I wait for 1 seconds
    And I click on element with css "button.button-1.login-button"
    Then I wait for 2 seconds

  @predefined7
  Scenario: Login with remember me checked
    When I type "testuser@example.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I check checkbox with id "RememberMe"
    Then checkbox with id "RememberMe" should be selected
    When I wait for 1 seconds
    And I click on element with css "button.button-1.login-button"
    Then I wait for 3 seconds
    And I wait for element with xpath "//a[text()='Log out']" to be present
    And element with xpath "//a[text()='Log out']" should be displayed

@smoke @auth @predefined
Feature: User Logout
  As a logged-in user
  I want to logout
  So that I can secure my session

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
  Scenario: Logout successfully
    When I click on link text "Log out"
    Then I wait for element with xpath "//a[text()='Log in']" to be present
    And element with xpath "//a[text()='Log in']" should be displayed

  @predefined2
  Scenario: Logout and verify session cleared
    When I click on link text "Log out"
    Then I wait for element with xpath "//a[text()='Log in']" to be present
    And element with xpath "//a[text()='Log in']" should be displayed
    And element with xpath "//a[text()='Log out']" should not be present

  @predefined3
  Scenario: Verify logout link only visible when logged in
    Then element with xpath "//a[text()='Log out']" should be displayed
    And element with xpath "//a[text()='Log in']" should not be present

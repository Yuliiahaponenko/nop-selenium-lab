@smoke @auth @predefined
Feature: User Logout
  As a logged-in user
  I want to logout
  So that I can secure my session

  @predefined1
  Scenario: Logout successfully from main page
    Given I open url "https://nop-qa.portnov.com/login"
    When I wait for element with id "Email" to be present
    Then I type "testuser@example.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I click on element with css "button.button-1.login-button"
    Then I wait for element with xpath "//a[text()='Log out']" to be present
    When I click on link text "Log out"
    Then I am on the home page

  @predefined2
  Scenario: Verify logout link is displayed after login
    Given I open url "https://nop-qa.portnov.com/login"
    Then I wait for element with id "Email" to be present
    When I type "testuser@example.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I click on element with css "button.button-1.login-button"
    And I wait for element with xpath "//a[text()='Log out']" to be present
    Then element with xpath "//a[text()='Log out']" should be displayed
    Then I am on the home page

  @predefined3
  Scenario: Logout link visible on account page
    Given I open url "https://nop-qa.portnov.com/login"
    When I wait for element with id "Email" to be present
    Then I type "testuser@example.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I click on element with css "button.button-1.login-button"
    And I wait for element with xpath "//a[text()='Log out']" to be present
    When I open url "https://nop-qa.portnov.com/customer/info"
    And I wait for element with xpath "//a[text()='Log out']" to be present
    Then I am on the home page

  @predefined4
  Scenario: Logout after searching for products
    Given I open url "https://nop-qa.portnov.com/login"
    When I wait for element with id "Email" to be present
    Then I type "testuser@example.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I click on element with css "button.button-1.login-button"
    And I wait for element with xpath "//a[text()='Log out']" to be present
    When I type "laptop" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    And I wait for element with xpath "//a[text()='Log out']" to be present
    Then element with xpath "//a[text()='Log out']" should be displayed

  @predefined5
  Scenario: Logout link not visible when not logged in
    Given I open url "https://nop-qa.portnov.com"
    Then element with xpath "//a[text()='Log out']" should not be present

  @predefined6
  Scenario: Logout link not visible on login page when not authenticated
    Given I open url "https://nop-qa.portnov.com/login"
    When element with xpath "//a[text()='Log out']" should not be present
    Then element with id "Email" should be present
    And element with id "Password" should be present

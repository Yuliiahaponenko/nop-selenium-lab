@regression @auth @predefined
Feature: User Registration
  As a new user
  I want to register an account
  So that I can access the site features

  Background:
    Given I open url "https://nop-qa.portnov.com/register"
    Then I wait for element with id "FirstName" to be present

  @predefined1
  Scenario: Register new user with unique email
    # Note: Update timestamp in email before each run to avoid duplicate email error
    When I type "John" into element with id "FirstName"
    And I type "Doe" into element with id "LastName"
    And I type "testuser.unique.20260129130500@test.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I type "Test123!" into element with id "ConfirmPassword"
    And I wait for 1 seconds
    And I click on element with css "#register-button"
    Then I wait for element with css ".result" to be present
    And element with css ".result" should contain text "Your registration completed"

  @predefined2
  Scenario: Register with password mismatch
    When I type "Test" into element with id "FirstName"
    And I type "User" into element with id "LastName"
    And I type "testuser.unique.20260129130500@test.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I type "Different123!" into element with id "ConfirmPassword"
    And I click on element with css "#register-button"
    Then I wait for 2 seconds
    And I should see page url contains "register"

  @predefined3
  Scenario: Register with invalid email format
    When I type "Test" into element with id "FirstName"
    And I type "User" into element with id "LastName"
    And I type "testuser.unique.2" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I type "Test123!" into element with id "ConfirmPassword"
    And I click on element with css "#register-button"
    Then I wait for 2 seconds
    And I should see page url contains "register"

  @predefined4
  Scenario: Register with weak password
    When I type "Test" into element with id "FirstName"
    And I type "User" into element with id "LastName"
    And I type "testuser.unique.20260129130500@test.com" into element with id "Email"
    And I type "123" into element with id "Password"
    And I type "123" into element with id "ConfirmPassword"
    And I click on element with css "#register-button"
    Then I wait for 2 seconds
    And I should see page url contains "register"

  @predefined5
  Scenario: Register with existing email
    When I type "Test" into element with id "FirstName"
    And I type "User" into element with id "LastName"
    And I type "testuser.unique.20260129130500@test.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I type "Test123!" into element with id "ConfirmPassword"
    And I click on element with css "#register-button"
    Then I wait for 2 seconds
    And I should see page url contains "register"

  @predefined6
  Scenario: Register with missing first name
    When I type "User" into element with id "LastName"
    And I type "testuser.unique.20260129130500@test.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I type "Test123!" into element with id "ConfirmPassword"
    And I click on element with css "#register-button"
    Then element with id "FirstName" should be present

  @predefined7
  Scenario: Register with missing last name
    When I type "Test" into element with id "FirstName"
    And I type "testuser.unique.20260129130500@test.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I type "Test123!" into element with id "ConfirmPassword"
    And I click on element with css "#register-button"
    Then element with id "LastName" should be present

  @predefined8
  Scenario: Register with all valid fields including gender
    # Note: Update timestamp in email before each run to avoid duplicate email error
    When I click on element with id "gender-male"
    And I type "John" into element with id "FirstName"
    And I type "Doe" into element with id "LastName"
    And I type "testuser.withgender.20260129130600@test.com" into element with id "Email"
    And I type "Test123!" into element with id "Password"
    And I type "Test123!" into element with id "ConfirmPassword"
    And I click on element with css "#register-button"
    Then I wait for element with css ".result" to be present
    And element with css ".result" should contain text "Your registration completed"

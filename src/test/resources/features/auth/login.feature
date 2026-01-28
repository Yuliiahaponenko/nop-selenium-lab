@smoke @auth
Feature: User Login
  As a user
  I want to login to my account
  So that I can access my account features

  Scenario: Valid login
    Given I am on the home page
    When I login with valid credentials
    Then I should see my account area

  Scenario: Invalid login with wrong password
    Given I am on the home page
    When I login with email "testuser@example.com" and password "WrongPassword123"
    Then I should see a validation error message

  Scenario: Invalid login with non-existent email
    Given I am on the home page
    When I login with email "nonexistent@example.com" and password "Test123!"
    Then I should see a validation error message

  Scenario: Login with empty email
    Given I am on the home page
    When I attempt to login with empty email and password "Test123!"
    Then I should see email validation error

  Scenario: Login with empty password
    Given I am on the home page
    When I attempt to login with email "testuser@example.com" and empty password
    Then I should see password validation error

  Scenario: Login with invalid email format
    Given I am on the home page
    When I attempt to login with email "invalid-email" and password "Test123!"
    Then I should see email format validation error

  Scenario: Login with remember me checked
    Given I am on the home page
    When I login with valid credentials and check remember me
    Then I should see my account area
    And remember me should be checked

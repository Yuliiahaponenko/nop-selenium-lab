@regression @auth
Feature: User Registration
  As a new user
  I want to register an account
  So that I can access the site features

  Scenario: Register new user with unique email
    Given I am on the home page
    When I register with valid required fields and a unique email
    Then account should be created successfully

  Scenario: Register with password mismatch
    Given I am on the home page
    When I register with email "test@example.com" password "Test123!" and confirm password "Different123!"
    Then I should see password mismatch error

  Scenario: Register with invalid email format
    Given I am on the home page
    When I register with invalid email format "notanemail"
    Then I should see email format validation error

  Scenario: Register with weak password
    Given I am on the home page
    When I register with weak password "123"
    Then I should see password strength validation error

  Scenario: Register with existing email
    Given I am on the home page
    When I register with existing email "testuser@example.com"
    Then I should see email already exists error

  Scenario: Register with missing first name
    Given I am on the home page
    When I register with missing first name
    Then I should see first name validation error

  Scenario: Register with missing last name
    Given I am on the home page
    When I register with missing last name
    Then I should see last name validation error

  Scenario: Register with all valid fields including gender
    Given I am on the home page
    When I register with gender "male" first name "John" last name "Doe" email and password
    Then account should be created successfully

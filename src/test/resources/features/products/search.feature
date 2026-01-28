@smoke @products
Feature: Product Search
  As a user
  I want to search for products
  So that I can find what I'm looking for

  Scenario: Search returns results
    Given I am on the home page
    When I search for "laptop"
    Then I should see a list of products

  Scenario: Search with no results
    Given I am on the home page
    When I search for "nonexistentproductxyz123"
    Then I should see a no results message

  Scenario: Search with empty string
    Given I am on the home page
    When I search for empty string
    Then I should see search validation message

  Scenario: Search with special characters
    Given I am on the home page
    When I search for "!@#$%^&*()"
    Then I should see a no results message or validation message

  Scenario: Search with partial product name
    Given I am on the home page
    When I search for "lap"
    Then I should see search results containing "lap"

  Scenario: Search case insensitive
    Given I am on the home page
    When I search for "LAPTOP"
    Then I should see a list of products

  Scenario: Search with multiple words
    Given I am on the home page
    When I search for "laptop computer"
    Then I should see search results

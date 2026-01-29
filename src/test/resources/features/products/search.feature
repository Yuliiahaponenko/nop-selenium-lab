@smoke @products @predefined
Feature: Product Search
  As a user
  I want to search for products
  So that I can find what I'm looking for

  Background:
    Given I open url "https://nop-qa.portnov.com"

  @predefined1
  Scenario: Search returns results
    When I type "laptop" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".product-item" to be present
    And I should see at least 1 elements with xpath "//div[@class='product-item']"

  @predefined2
  Scenario: Search with no results
    When I type "nonexistentproductxyz123" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".no-result" to be present
    And element with css ".no-result" should be displayed

  @predefined3
  Scenario: Search with empty string
    When I click on element with css "button[type='submit'].search-box-button"
    Then I wait for 2 seconds

  @predefined4
  Scenario: Search with special characters
    When I type "!@#$%^&*()" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for 2 seconds

  @predefined5
  Scenario: Search with partial product name
    When I type "lap" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".product-item" to be present
    And I should see at least 1 elements with xpath "//div[@class='product-item']"

  @predefined6
  Scenario: Search case insensitive
    When I type "LAPTOP" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for element with css ".product-item" to be present
    And I should see at least 1 elements with xpath "//div[@class='product-item']"

  @predefined7
  Scenario: Search with multiple words
    When I type "laptop computer" into element with id "small-searchterms"
    And I click on element with css "button[type='submit'].search-box-button"
    Then I wait for 2 seconds

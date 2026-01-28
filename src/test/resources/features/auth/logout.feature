@smoke @auth
Feature: User Logout
  As a logged-in user
  I want to logout
  So that I can secure my session

  Scenario: Logout successfully
    Given I am logged in
    When I logout
    Then I should be logged out

  Scenario: Logout and verify session cleared
    Given I am logged in
    When I logout
    Then I should be logged out
    And I should not see account information
    And I should see login link

  Scenario: Attempt logout when not logged in
    Given I am on the home page
    And I am not logged in
    Then logout link should not be visible

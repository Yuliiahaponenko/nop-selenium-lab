@smoke @navigation @predefined
Feature: Home Page Link Verification
  As a user
  I want to verify all links on the home page
  So that I can ensure proper navigation

  Background:
    Given I open url "https://nop-qa.portnov.com"
    Then I wait for 2 seconds

  @predefined1
  Scenario: Verify header navigation links are present
    Then element with xpath "//a[@class='ico-register']" should be present
    And element with xpath "//a[@class='ico-login']" should be present
    And element with xpath "//a[@class='ico-wishlist']" should be present
    And element with xpath "//a[@class='ico-cart']" should be present

  @predefined2
  Scenario: Verify header navigation links are displayed
    Then element with xpath "//a[@class='ico-register']" should be displayed
    And element with xpath "//a[@class='ico-login']" should be displayed
    And element with xpath "//a[@class='ico-wishlist']" should be displayed
    And element with xpath "//a[@class='ico-cart']" should be displayed

  @predefined3
  Scenario: Verify main category menu links
    Then element with xpath "//a[contains(@href, '/computers')]" should be present
    And element with xpath "//a[contains(@href, '/electronics')]" should be present
    And element with xpath "//a[contains(@href, '/apparel')]" should be present
    And element with xpath "//a[contains(@href, '/digital-downloads')]" should be present
    And element with xpath "//a[contains(@href, '/books')]" should be present
    And element with xpath "//a[contains(@href, '/jewelry')]" should be present
    And element with xpath "//a[contains(@href, '/gift-cards')]" should be present

  @predefined4
  Scenario: Verify computers subcategory links
    When I click on element with xpath "//a[contains(@href, '/computers')]"
    Then I wait for 1 seconds
    And element with xpath "//a[contains(@href, '/desktops')]" should be displayed
    And element with xpath "//a[contains(@href, '/notebooks')]" should be displayed
    And element with xpath "//a[contains(@href, '/software')]" should be displayed

  @predefined5
  Scenario: Verify electronics subcategory links
    When I click on element with xpath "//a[contains(@href, '/electronics')]"
    Then I wait for 1 seconds
    And element with xpath "//a[contains(@href, '/camera-photo')]" should be displayed
    And element with xpath "//a[contains(@href, '/cell-phones')]" should be displayed
    And element with xpath "//a[contains(@href, '/others')]" should be displayed

  @predefined6
  Scenario: Verify apparel subcategory links
    When I click on element with xpath "//a[contains(@href, '/apparel')]"
    Then I wait for 1 seconds
    And element with xpath "//a[contains(@href, '/shoes')]" should be displayed
    And element with xpath "//a[contains(@href, '/clothing')]" should be displayed
    And element with xpath "//a[contains(@href, '/accessories')]" should be displayed

  @predefined7
  Scenario: Verify footer information links
    Then element with xpath "//a[contains(@href, '/sitemap')]" should be present
    And element with xpath "//a[contains(@href, '/shipping-returns')]" should be present
    And element with xpath "//a[contains(@href, '/privacy-notice')]" should be present
    And element with xpath "//a[contains(@href, '/conditions-of-use')]" should be present
    And element with xpath "//a[contains(@href, '/about-us')]" should be present
    And element with xpath "//a[contains(@href, '/contactus')]" should be present

  @predefined8
  Scenario: Verify footer customer service links
    Then element with xpath "//a[contains(@href, '/search')]" should be present
    And element with xpath "//a[contains(@href, '/news')]" should be present
    And element with xpath "//a[contains(@href, '/blog')]" should be present
    And element with xpath "//a[contains(@href, '/recentlyviewedproducts')]" should be present
    And element with xpath "//a[contains(@href, '/compareproducts')]" should be present
    And element with xpath "//a[contains(@href, '/newproducts')]" should be present

  @predefined9
  Scenario: Verify footer my account links
    Then element with xpath "//a[contains(@href, '/customer/info')]" should be present
    And element with xpath "//a[contains(@href, '/order/history')]" should be present
    And element with xpath "//a[contains(@href, '/customer/addresses')]" should be present
    And element with xpath "//a[contains(@href, '/cart')]" should be present
    And element with xpath "//a[contains(@href, '/wishlist')]" should be present
    And element with xpath "//a[contains(@href, '/vendor/apply')]" should be present

  @predefined10
  Scenario: Verify search functionality is present
    Then element with id "small-searchterms" should be present
    And element with css "button[type='submit'].search-box-button" should be present

  @predefined11
  Scenario: Click and verify register link navigation
    When I click on element with xpath "//a[@class='ico-register']"
    Then I wait for 2 seconds
    And I should see page url contains "register"
    And element with id "FirstName" should be present

  @predefined12
  Scenario: Click and verify login link navigation
    When I click on element with xpath "//a[@class='ico-login']"
    Then I wait for 2 seconds
    And I should see page url contains "login"
    And element with id "Email" should be present

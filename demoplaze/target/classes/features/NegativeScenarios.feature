@negative
Feature: Negative Scenarios on Demoblaze
  As a user
  I want to verify error handling for invalid actions

  @existing-username
  Scenario: Sign up with an existing username
    Given I am on the Demoblaze homepage
    When I click on the Sign up button in the header
    And I enter an existing username and any password in the signup form
    And I click on the Sign Up button with existing username
    Then an error message should be displayed for existing username

  Scenario: Purchase with expired credit card
    Given I am logged in to Demoblaze
    And I have two products in my cart
    When I proceed to checkout with an expired credit card
    Then an error message should be displayed for expired card

  Scenario: Add the same product twice to the cart
    Given I am logged in to Demoblaze
    When I add a product to the cart twice
    Then the cart should update the quantity or show a message

  Scenario: Log in with incorrect credentials
    Given I am on the Demoblaze homepage
    When I click on the Log in button in the header
    And I enter incorrect credentials in the login form
    And I click on the Log In button
    Then an error message should be displayed for incorrect credentials 
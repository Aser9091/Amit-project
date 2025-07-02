Feature: User Sign Up on Demoblaze
  As a new user
  I want to sign up on Demoblaze
  So that I can create an account

  Scenario: Verify that User Can Sign Up Successfully
    Given I am on the Demoblaze homepage
    When I click on the Sign up button in the header
    And I enter a new username and password in the signup form
    And I click on the Sign Up button
    Then a sign up success message should be displayed: "Sign up successful." 
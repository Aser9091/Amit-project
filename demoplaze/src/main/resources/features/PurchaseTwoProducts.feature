Feature: Purchase Two Products on Demoblaze
  As a user
  I want to purchase two products
  So that I can complete an order successfully

  Scenario: Verify that Two Products Are Purchased Successfully
    Given I am logged in to Demoblaze
    When I add the first laptop to the cart
    And I validate that the first product is added to the cart
    And I add the second laptop to the cart
    And I validate that the second product is added to the cart
    And I go to the cart
    Then both products should be visible in the cart with their titles and prices
    And the total amount should be calculated correctly
    When I proceed to checkout and fill in the required details
    And I click on the Purchase button
    Then a purchase success message should be displayed: "Thank you for your purchase!" 
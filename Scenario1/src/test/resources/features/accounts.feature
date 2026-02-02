Feature: Create Account

  Scenario: Create Account
    Given I add money to my account
    Given login and access accounts page
    When I click on create new account card
    Then Verify popup to create the account appears
    When I fill information for the new account
    And I click on create account button
    Then Verify created account is present
Feature: Transfer
  Scenario: Transfer
    Given login and access transfer page
    And I capture initial balance of "savings" account
    When I select transfer to own account
    And I fill in transfer form with "savings" account, 1 amount and proceed
    Then Verify confirmation window appears with transfer details
    When I click to proceed with transfer
    Then Verify success transfer page appears
    When I access accounts page
    Then verify "savings" account balance increased
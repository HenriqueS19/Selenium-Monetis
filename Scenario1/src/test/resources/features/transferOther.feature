Feature: Transfer to Other Account
  Scenario Outline: Make a transfer to other account
    Given login and access transfer page for other account
    When I select transfer to other account
    And I fill in transfer form with "<target>" target, <amount> amount and proceed
    Then Verify confirmation window appears with transfer details for other account
    When I click to proceed with transfer to other account
    Then Verify success transfer page appears for other account
    When I access accounts page for other account
    Then Verify "checking" account balance decreased
    When I access transactions page
    Then Verify new transaction with "-<amount>€" appears on the list

    Examples:
      | target                     | amount |
      | PT50000201231234567890154  | 1      |
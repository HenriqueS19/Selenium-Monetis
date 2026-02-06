@regression
Feature: Make multiple payments Test
  Scenario Outline: Make multiple payments
    Given login and access payments page
    When I make a payment with the following data
      | ACCOUNT | REFERENCE | ENTITY | AMOUNT | CATEGORY |
      | <ACCOUNT> | <REFERENCE> | <ENTITY> | <AMOUNT> | <CATEGORY> |
    When I click to proceed with payment
    When I click to proceed with payment again
    Then Verify success payment page appears
    When I access the payments transactions page
    Then Verify new transaction appears with "<CATEGORY>" category and <AMOUNT> amount

    Examples:

      | ACCOUNT | REFERENCE | ENTITY | AMOUNT | CATEGORY |
      | checking | INV-123455 | 12345 | 1 | house |
      | savings | INV-543211 | 12333 | 1 | car |
      | savings | INV-121212 | 22123 | 1 | bills |
      | checking | INV-212121 | 33221 | 1 | mobile |

Feature: User Login

   Scenario: User Login
     Given access the login page "https://monetis-delta.vercel.app/login"
     When I Fill in username from hook and password "thisIsMyPassword!1"
     And I click on the login button
     Then Verify user is on dashboard


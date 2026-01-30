Feature: User Login

   Scenario: User Login
     Given access the login page "https://monetis-delta.vercel.app/login"
     When I Fill in username "testingaccount@test.com" and password "testingPassword!1"
     And I click on the login button
     Then Verify user is on dashboard


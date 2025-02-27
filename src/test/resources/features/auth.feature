Feature: User Authentication and Registration
  As a system user,
  I want to be able to log in and register,
  To access the application's functionality.

  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I enter a valid email "e@e" and password "123123123"
    And I press the login button
    Then I see the user dashboard

  Scenario: Registration with a password shorter than 8 characters
    Given I am on the registration page
    When I enter registration details:
      | FirstName | LastName | DateOfBirth | Email            | Password | ConfirmPassword |
      | John      | Doe      | 01.01.2001  | john@example.com | Pass     | Pass            |
    And I press the register button
    Then I see an error message "Minimum 8 characters"

  Scenario: Registration with mismatched passwords
    Given I am on the registration page
    When I enter registration details:

      | FirstName | LastName | DateOfBirth | Email            | Password | ConfirmPassword |
      | John      | Doe      | 01.01.2001  | john@example.com | Password123 | Password321    |
    And I press the register button
    Then I see an error message "Passwords must match"
@currency-conversion
Feature: Validate Currency Conversion API functionality

  The Currency Conversion API should return accurate and valid responses for currency conversion rates.
  This includes returning a successful status, providing a specific USD to AED price range,
  ensuring a certain number of currency pairs are returned,
  and matching the expected JSON schema for the response.

  Scenario: Check USD to AED price range is within specified limits
    Given I send a request to the Currency Conversion API for Base code "USD"
    Then the response should contain a status field with value "success"
    And the API should return a price for "AED" within the range of 3.6 to 3.7

  Scenario: Verify the number of currency pairs returned by the API
    Given I send a request to the Currency Conversion API for Base code "USD"
    Then the API response should match the expected JSON schema
    And the API should return exactly 162 currency pairs

  Scenario: Check for Non Existent Currency keys
    Given I send a request to the Currency Conversion API for Base code "ABC"
    Then the result is "error" and error-type should be "unsupported-code"


  Scenario: Check for 404 when no Currency Symbol is provided
    Given I send a request to the Currency Conversion API for Base code ""
    Then the response status should be 404

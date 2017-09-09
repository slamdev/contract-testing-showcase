Feature: Contracts API

  Background:
    Given baseUri for "LEGACY" contract is "https://httpbin.org"
    And baseUri for "NEW" contract is "https://httpbin.org"
    And exclude headers from verification: Date, X-Processed-Time

  Scenario: Verify some endpoint
    Given content type is "application/json"
    And queryParam "my" is "query"
    And request body:
    """
{
  "sample":{
    "json":[
      "cool",
      "object"
    ]
  }
}
    """
    And header Authorization with value "Bearer ASD"
    When the client performs POST request for contract on "anything"
    Then verify contract requests are same

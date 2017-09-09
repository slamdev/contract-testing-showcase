Feature: Wikipedia API

  Background:
    Given baseUri is "https://en.wikipedia.org/w"

  Scenario: List of pages should be returned
    When the client performs GET request on "api.php?action=query&titles=Main%20Page&prop=revisions&rvprop=content&format=json"
    Then status code is 200
    And response contains property "query.pages" of type "MAP"
    And header "content-type" equal to "application/json; charset=utf-8"

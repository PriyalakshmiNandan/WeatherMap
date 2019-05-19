Feature: WeatherMan Forecast

  Scenario: Analyse and display the information about the Weather in the specified City
    Given User enters the City Name
    When User requests for Weather data for the City during runtime
    Then Display the Weather Information
$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/WeatherManForecast.feature");
formatter.feature({
  "name": "WeatherMan Forecast",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "Analyse and display the information about the Weather in the specified City",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "User enters the City Name",
  "keyword": "Given "
});
formatter.match({
  "location": "WeatherManForecast.userEntersTheCityName()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User requests for Weather data for the City during runtime",
  "keyword": "When "
});
formatter.match({
  "location": "WeatherManForecast.userRequestsForWeatherDataForTheCityDuringRuntime()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Display the Weather Information",
  "keyword": "Then "
});
formatter.match({
  "location": "WeatherManForecast.displayTheWeatherInformation()"
});
formatter.result({
  "status": "passed"
});
});
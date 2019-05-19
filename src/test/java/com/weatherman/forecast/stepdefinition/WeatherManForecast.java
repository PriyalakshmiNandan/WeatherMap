package com.weatherman.forecast.stepdefinition;
import com.weatherman.forecast.config.CommonDef;
import com.weatherman.forecast.library.ParseJSONResponse;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class WeatherManForecast {
    public CommonDef commInst;
    public ParseJSONResponse parseInst;
    public WeatherManForecast(CommonDef comm, ParseJSONResponse parse)
    {
        this.commInst = comm;
        this.parseInst=parse;
    }
    @Given("^User enters the City Name$")
    public void userEntersTheCityName()
    {
        commInst.city= System.getProperty("city","Lacombe");
        commInst.country=System.getProperty("country","CA");
    }

    @When("^User requests for Weather data for the City during runtime$")
    public void userRequestsForWeatherDataForTheCityDuringRuntime()
    {
        commInst.response=given().queryParam("q",commInst.city+","+commInst.country).queryParam("appid",commInst.appID).when().get(commInst.base_URL);
    }

    @Then("^Display the Weather Information$")
    public void displayTheWeatherInformation()
    {
        JsonPath jsonPath=  commInst.response.then().extract().response().jsonPath(); //.getJsonObject("list");
        //JSONArray jsonObj= (JSONArray) json;
        ArrayList<HashMap<String,Object>> json=  jsonPath.getJsonObject("list");
        Integer count= (Integer) jsonPath.get("cnt");
        for(int i=0; i<count.intValue();i++) {
            parseInst.parseMainDataMap(json.get(i));
            parseInst.parseWeatherDataMap(json.get(i));
        }
        parseInst.analyseTempData();
        parseInst.analyseWeatherData();
    }

}

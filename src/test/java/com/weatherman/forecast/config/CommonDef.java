package com.weatherman.forecast.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.ValidatableResponse;
public class CommonDef {
    public String base_URL= "https://samples.openweathermap.org/data/2.5/forecast/hourly"; //?q=London,us&appid=b6907d289e10d714a6e88b30761fae22
    public Response response =null;
    public RequestSpecification request = new RequestSpecBuilder().setContentType(ContentType.JSON).build();;
    public ValidatableResponse json;
    public String city;
    public String country;
    final public String appID= "1abc39f45cb3ea2ead2ebc5ff177e0908c9";//"b6907d289e10d714a6e88b30761fae22";
}

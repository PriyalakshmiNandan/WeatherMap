package com.weatherman.forecast.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin={"pretty","html:target/cucumber-html-report.html"},features="src/test/resources/features",glue="com.weatherman.forecast.stepdefinition",strict=true,monochrome=true)
public class TestRunner {
}

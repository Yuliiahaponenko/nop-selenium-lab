package com.nopcommerce.bdd.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/auth/login.feature",
    glue = {"com.nopcommerce.bdd.steps", "com.nopcommerce.bdd.hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/login",
        "json:target/cucumber-reports/login/Cucumber.json",
        "junit:target/cucumber-reports/login/Cucumber.xml",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true
)
public class LoginFeatureRunner extends AbstractTestNGCucumberTests {
}

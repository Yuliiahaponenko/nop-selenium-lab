package com.nopcommerce.bdd.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/account/orders.feature",
    glue = {"com.nopcommerce.bdd.steps", "com.nopcommerce.bdd.hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/orders",
        "json:target/cucumber-reports/orders/Cucumber.json",
        "junit:target/cucumber-reports/orders/Cucumber.xml",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true
)
public class OrdersFeatureRunner extends AbstractTestNGCucumberTests {
}

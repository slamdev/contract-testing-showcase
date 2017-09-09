package com.github.slamdev.contracts;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "json:build/cucumber.json", "lv.ctco.cukes.core.formatter.CukesJsonFormatter:build/cucumber2.json"},
        features = "classpath:features",
        glue = {"lv.ctco.cukes", "com.github.slamdev.contracts.steps"},
        strict = true
)
public class TestRunner {
}

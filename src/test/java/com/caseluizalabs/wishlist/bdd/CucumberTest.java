package com.caseluizalabs.wishlist.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features"},
        glue = {"com.caseluizalabs.wishlist.bdd"},
        plugin = {"pretty"})
@SpringBootTest
public class CucumberTest {

}

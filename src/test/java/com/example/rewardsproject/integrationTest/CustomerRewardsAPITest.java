package com.example.rewardsproject.integrationTest;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerRewardsAPITest {

    @Value("http://localhost:${local.server.port}")
    private String SERVICE_URI;

    @Test
    public void getRewardsFromExistingCustomer(){
        when().get(SERVICE_URI + "/rewards/1").prettyPeek()
                .then().assertThat()
                .statusCode(200)
                .body("customerId", Matchers.equalTo(1))
                .body("lastFirstMonthPoints", Matchers.equalTo(30))
                .body("lastSecondMonthPoints", Matchers.equalTo(60))
                .body("lastThirdMonthPoints", Matchers.equalTo(20))
                .body("totalPoints", Matchers.equalTo(110));
    }

    @Test
    public void getRewardsFromNoneExistingCustomer(){
        when().get(SERVICE_URI + "/rewards/4").prettyPeek()
                .then().assertThat()
                .statusCode(404)
                .body("errorCode",Matchers.equalTo(404));
    }





}

package com.example.rewardsproject.unitTest;

import com.example.rewardsproject.controller.RewardsController;
import com.example.rewardsproject.dao.CustomerRepository;
import com.example.rewardsproject.entity.CustomerEntity;
import com.example.rewardsproject.service.RewardsService;
import com.example.rewardsproject.vo.Rewards;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.anyLong;

public class CustomerRewardsAPIUnitTest {

    @Mock
    RewardsService rewardsService;

    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    public void configMock(){
        MockitoAnnotations.openMocks(this);
        RestAssuredMockMvc.standaloneSetup(new RewardsController(rewardsService, customerRepository));
    }

    @Test
    public void testGetRewardsByCustomerId(){
        Mockito.when(customerRepository.findByCustomerId(anyLong()))
                .thenReturn(Optional.of(new CustomerEntity(1l, "Customer1")));
        Mockito.when(rewardsService.getRewardsByCustomerId(anyLong()))
                .thenReturn(new Rewards(1, 30, 60, 20, 110));

        given().accept("application/json").get("rewards/1").peek()
                .then().assertThat()
                .statusCode(200)
                .body(Matchers.equalTo("{\"customerId\":1,\"lastFirstMonthPoints\":30,\"lastSecondMonthPoints\":60,\"lastThirdMonthPoints\":20,\"totalPoints\":110}"));

    }

    @Test
    public void testGetRewardsWithNoneExistCustomer(){
        Mockito.when(customerRepository.findByCustomerId(anyLong()))
                .thenReturn(Optional.empty());
        given().accept("application/json").get("rewards/4").peek()
                .then().assertThat()
                .statusCode(404)
                .body("errorCode",Matchers.equalTo(404));
    }


}

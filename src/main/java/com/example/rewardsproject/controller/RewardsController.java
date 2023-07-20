package com.example.rewardsproject.controller;

import com.example.rewardsproject.dao.CustomerRepository;
import com.example.rewardsproject.entity.CustomerEntity;
import com.example.rewardsproject.exception.CustomerNotFoundException;
import com.example.rewardsproject.service.RewardsService;
import com.example.rewardsproject.vo.Rewards;
import com.example.rewardsproject.vo.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
public class RewardsController {
    private static Logger logger = LoggerFactory.getLogger(RewardsController.class);

    //@Autowired
    RewardsService rewardsService;
   // @Autowired
    CustomerRepository customerRepository;

    @Autowired
    public RewardsController(RewardsService rewardsService, CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
        this.rewardsService = rewardsService;
    }

    @CrossOrigin(origins = "null")
    @GetMapping("/rewards/{customerId}")
    public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable("customerId") Long customerId) {
        CustomerEntity customerEntity = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("can't find the customer"));
        Rewards customerRewards = rewardsService.getRewardsByCustomerId(customerId);
        return new ResponseEntity<>(customerRewards, HttpStatusCode.valueOf(200));
    }



    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerUserNotFound(Exception ex){
        logger.error("Can't find customer");
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}

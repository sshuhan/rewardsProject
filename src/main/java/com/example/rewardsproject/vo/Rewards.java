package com.example.rewardsproject.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rewards {

    @NotBlank
    private long customerId;

    private long lastFirstMonthPoints;
    private long lastSecondMonthPoints;
    private long lastThirdMonthPoints;
    private long totalPoints;
}
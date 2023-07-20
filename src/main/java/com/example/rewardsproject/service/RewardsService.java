package com.example.rewardsproject.service;

import com.example.rewardsproject.vo.Rewards;

public interface RewardsService {
    public Rewards getRewardsByCustomerId(Long customerId);
}

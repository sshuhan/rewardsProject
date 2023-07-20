package com.example.rewardsproject.service;

import com.example.rewardsproject.dao.TransactionRepository;
import com.example.rewardsproject.entity.TransactionEntity;
import com.example.rewardsproject.util.Constants;
import com.example.rewardsproject.vo.Rewards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardsServiceImpl implements RewardsService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Rewards getRewardsByCustomerId(Long customerId) {
        Timestamp lastFirstMonthTimeStamp = getPreviousTimeStamp(Constants.DAYS_PER_MONTH);
        Timestamp lastSecondMonthTimeStamp = getPreviousTimeStamp(Constants.DAYS_PER_MONTH * 2);
        Timestamp lastThirdMonthTimeStamp = getPreviousTimeStamp(Constants.DAYS_PER_MONTH * 3);

        List<TransactionEntity> lastFirstMonthTransaction = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, lastFirstMonthTimeStamp, Timestamp.from(Instant.now()));
        List<TransactionEntity> lastSecondMonthTransaction = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimeStamp, lastFirstMonthTimeStamp);
        List<TransactionEntity> lastThirdMonthTransaction = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimeStamp, lastSecondMonthTimeStamp);

        long lastFirstMonthPoints = calculatePoints(lastFirstMonthTransaction);
        long lastSecondMonthPoints = calculatePoints(lastSecondMonthTransaction);
        long lastThirdMonthPoints = calculatePoints(lastThirdMonthTransaction);

        Rewards customerRewards = new Rewards();
        customerRewards.setCustomerId(customerId);
        customerRewards.setLastFirstMonthPoints(lastFirstMonthPoints);
        customerRewards.setLastSecondMonthPoints(lastSecondMonthPoints);
        customerRewards.setLastThirdMonthPoints(lastThirdMonthPoints);
        customerRewards.setTotalPoints(lastFirstMonthPoints + lastSecondMonthPoints + lastThirdMonthPoints);
        return customerRewards;
    }

    private long calculatePoints(List<TransactionEntity> lastFirstMonthTransaction){
        return lastFirstMonthTransaction.stream().map(tx -> calculatePointsBasedOnOneTransaction(tx))
                .collect(Collectors.summingLong(r -> r.longValue()));
    }

    private long calculatePointsBasedOnOneTransaction(TransactionEntity tx){
        double transactionAmount = tx.getTransactionAmount();
        long totalPoints = 0;
        if(transactionAmount >= Constants.SECOND_REWARD_LEVEL){
            totalPoints += Math.round(transactionAmount - Constants.SECOND_REWARD_LEVEL) * 2;
            transactionAmount -= (transactionAmount - Constants.SECOND_REWARD_LEVEL);
        }
        if(transactionAmount >= Constants.FIRST_REWARD_LEVEL){
            totalPoints += Math.round(transactionAmount - Constants.FIRST_REWARD_LEVEL);
        }
        return totalPoints;
    }




    private Timestamp getPreviousTimeStamp(int days){
        return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
    }


}

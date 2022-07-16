package edu.uni.lodz.system.akademia.pilkarska.domain.model.monthlySubscription;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MonthlySubscriptionService {
   private final MonthlySubscriptionRepository monthlySubscriptionRepository;

    public MonthlySubscription saveMonthlySubscription(MonthlySubscription monthlySubscription){
      return monthlySubscriptionRepository.save(monthlySubscription);
    }
}

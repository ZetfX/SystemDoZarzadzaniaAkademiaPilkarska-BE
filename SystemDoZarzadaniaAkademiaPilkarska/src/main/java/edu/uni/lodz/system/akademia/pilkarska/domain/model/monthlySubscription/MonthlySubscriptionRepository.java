package edu.uni.lodz.system.akademia.pilkarska.domain.model.monthlySubscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MonthlySubscriptionRepository extends JpaRepository<MonthlySubscription,Long> {
}

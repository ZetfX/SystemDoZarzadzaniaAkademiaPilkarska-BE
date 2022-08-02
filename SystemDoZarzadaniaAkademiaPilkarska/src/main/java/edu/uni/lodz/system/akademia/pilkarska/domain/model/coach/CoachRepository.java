package edu.uni.lodz.system.akademia.pilkarska.domain.model.coach;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.object.Object;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.player.Player;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
interface CoachRepository extends JpaRepository<Coach, Long> {

    @Query(value = "SELECT c from Coach c where c.user.academy = :academy")
    Optional<Set<Coach>> getCoachesByAcademy(@Param("academy") Academy academy);

    @Query(value =  "SELECT c from Coach c where c.user = :user")
    Optional<Coach> getCoachByUser(@Param("user")User user);


    @Query(value = "SELECT c from Coach c where c.trainingGroup = :trainingGroup")
    Optional<Set<Coach>> getCoachesByTrainingGroup(@Param("trainingGroup") TrainingGroup trainingGroup);
}

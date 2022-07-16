package edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
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
interface TrainingGroupRepository extends JpaRepository<TrainingGroup,Long> {

    @Query(value = "SELECT tg from TrainingGroup tg where tg.academy = :academy")
    Optional<Set<TrainingGroup>> getTrainingGroupsByAcademy(@Param("academy") Academy academy);

    Optional<TrainingGroup> findByGroupName(String groupName);
}

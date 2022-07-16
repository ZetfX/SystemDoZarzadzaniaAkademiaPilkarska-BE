package edu.uni.lodz.system.akademia.pilkarska.domain.model.player;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.Set;

@Repository
interface PlayerRepository extends JpaRepository<Player,Long> {
    @Query(value = "SELECT p from Player p where p.user.academy = :academy")
    Optional<Set<Player>> getPlayersByAcademy(@Param("academy")Academy academy);

    @Query(value = "SELECT p from Player p where p.trainingGroup = :trainingGroup")
    Optional<Set<Player>> getPlayersByTrainingGroup(@Param("trainingGroup")TrainingGroup trainingGroup);

}

package edu.uni.lodz.system.akademia.pilkarska.domain.model.grade;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.player.Player;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PlayerGradeRepository extends JpaRepository<PlayerGrade, Long> {

    @Query(value = "SELECT g from PlayerGrade g where g.player = :player")
    Optional<Set<PlayerGrade>> getPlayerGradesByPlayer(@Param("player") Player Player);
}

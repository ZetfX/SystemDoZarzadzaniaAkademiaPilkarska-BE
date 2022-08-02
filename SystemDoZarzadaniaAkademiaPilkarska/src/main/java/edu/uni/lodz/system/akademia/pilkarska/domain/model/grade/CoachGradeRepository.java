package edu.uni.lodz.system.akademia.pilkarska.domain.model.grade;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface CoachGradeRepository extends JpaRepository<CoachGrade, Long> {
    @Query(value = "SELECT p from Player p where p.user.academy = :academy")
    Optional<Set<Player>> getPlayersByAcademy(@Param("academy") Academy academy);

}

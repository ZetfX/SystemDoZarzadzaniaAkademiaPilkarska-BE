package edu.uni.lodz.system.akademia.pilkarska.domain.model.income;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query(value = "SELECT i from Income i where i.academy = :academy")
    Optional<Set<Income>> getIncomesByAcademy(@Param("academy") Academy academy);
}

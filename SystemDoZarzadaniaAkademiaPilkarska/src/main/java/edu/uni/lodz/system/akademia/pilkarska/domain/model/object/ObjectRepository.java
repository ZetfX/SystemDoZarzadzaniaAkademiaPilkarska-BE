package edu.uni.lodz.system.akademia.pilkarska.domain.model.object;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.object.Object;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
interface ObjectRepository extends JpaRepository<Object, Long> {

    @Query(value = "SELECT o from Object o where o.academy = :academy")
    Optional<Set<Object>> getObjectsByAcademy(@Param("academy") Academy academy);
}

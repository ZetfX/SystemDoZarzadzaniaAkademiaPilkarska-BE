package edu.uni.lodz.system.akademia.pilkarska.domain.model.event;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.object.Object;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT e from Event e where e.academy = :academy")
    Optional<Set<Event>> getEventsByAcademy(@Param("academy") Academy academy);
}

package edu.uni.lodz.system.akademia.pilkarska.domain.model.academy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
interface AcademyRepository extends JpaRepository<Academy, Long> {
    Optional<Academy> findByAcademyName(String academyName);
}

package edu.uni.lodz.system.akademia.pilkarska.domain.model.user;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
interface UserRepository extends JpaRepository<User,Long> {

 Optional<User> findByEmail(String email);
 @Modifying
 @Query(value = "Update User u set u.academy = :academy where u.email = :email")
 void addAcademyToExistingUser(@Param("academy")Academy academy, @Param("email") String email);

}

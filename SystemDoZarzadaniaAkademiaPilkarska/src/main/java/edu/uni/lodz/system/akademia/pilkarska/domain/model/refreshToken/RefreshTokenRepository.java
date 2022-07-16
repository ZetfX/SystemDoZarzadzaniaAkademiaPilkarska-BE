package edu.uni.lodz.system.akademia.pilkarska.domain.model.refreshToken;

import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.User;
import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Override
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);
}

package edu.uni.lodz.system.akademia.pilkarska.domain.model.refreshToken;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.TokenRefreshException;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwtRefreshExpirationMs}")
    private Long jwtRefreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userService.findUserById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken=refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }


    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now()) <0){
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getToken(),"Refresh token was expired. Please make a new signin request");
        }
        return refreshToken;
    }


    @Transactional
    public int deleteUserById(long userId){
        return refreshTokenRepository.deleteByUser(userService.findUserById(userId).get());
    }
}

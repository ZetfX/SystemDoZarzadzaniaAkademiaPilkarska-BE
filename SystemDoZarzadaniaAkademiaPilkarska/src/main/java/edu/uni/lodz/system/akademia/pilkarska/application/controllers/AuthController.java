package edu.uni.lodz.system.akademia.pilkarska.application.controllers;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.TokenRefreshException;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.NewPasswordRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.RefreshTokenRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.SignInRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.SignUpRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.JwtResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.TokenRefreshResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.services.AuthService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.refreshToken.RefreshToken;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.refreshToken.RefreshTokenService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.User;
import edu.uni.lodz.system.akademia.pilkarska.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;


    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    private final JwtUtils jwtUtils;


    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User userDetails = (User) authentication.getPrincipal();
        String role = String.valueOf(userDetails.getAuthorities().stream().findFirst().get());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt,refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getSurname(),
                userDetails.getEmail(),
                role,userDetails.getAcademy()));
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.registerAcademyAdmin(signUpRequest));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request){
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken:: getUser)
                .map(user ->{
                    String token =jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token,requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,"Refresh token is not in database!"));
    }

    @PostMapping("changePassword/{userId}")
    public ResponseEntity<?> changePassword(@Valid @RequestBody NewPasswordRequest newPassword, @PathVariable("userId") Long userId)
    {
       return ResponseEntity.ok(authService.changePassword(newPassword,userId));
    }
}


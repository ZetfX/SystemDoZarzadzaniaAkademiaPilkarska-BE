package edu.uni.lodz.system.akademia.pilkarska.domain.model.user;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.FieldTakenException;
import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.NotFoundException;
import edu.uni.lodz.system.akademia.pilkarska.application.generators.PassGenerator;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.NewPasswordRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.SignUpResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.senders.EmailSender;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static String USER_NOT_FOUND_MSG = "Użytkownik o podanym emailu:%s nie istnieje";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }


    public SignUpResponse signUpAcademyAdmin(User user) {
        validateUserData(user.getEmail());
        encryptPassword(user);
        userRepository.save(user);
        return new SignUpResponse("Użytkownik został zarejestrowany pomyslnie");
    }


    public void validateUserData(String email) {
        isEmailTaken(email);
    }

    public User generatePasswordAndSaveUser(User user) {
        validateUserData(user.getEmail());
        String password = new PassGenerator().generatePassword();
        new EmailSender().sendEmail(user.getEmail(), password);
        user.setPassword(password);
        encryptPassword(user);
        userRepository.save(user);
        return user;
    }

    public boolean hasEmailChanged(String prevEmail, String newEmail) {
        return !prevEmail.equals(newEmail);
    }

    public void addAcademyToExistingUser(Academy academy, String email) {
        userRepository.addAcademyToExistingUser(academy, email);
    }

    private void isEmailTaken(String email) {
        if (findUserByEmail(email).isPresent()) {
            throw new FieldTakenException("Email zajęty");
        }
    }

    public User getUserById(Long userId) {
        if (userId != null) {
            return findUserById(userId).orElseThrow(() -> new NotFoundException("Brak Użytkownika o takim id"));
        }
        return null;
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    private Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public void encryptPassword(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }

    public User changePassword(NewPasswordRequest newPassword, Long userId) {
        User user = getUserById(userId);
        user.setPassword(newPassword.getNewPassword());
        encryptPassword(user);
        userRepository.save(user);
        return user;
    }
}

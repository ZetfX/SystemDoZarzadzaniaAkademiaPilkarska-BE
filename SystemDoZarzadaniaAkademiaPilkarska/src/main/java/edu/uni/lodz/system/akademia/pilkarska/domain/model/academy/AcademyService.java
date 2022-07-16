package edu.uni.lodz.system.akademia.pilkarska.domain.model.academy;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.FieldTakenException;
import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.NotFoundException;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateAcademyRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AcademyResponse;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AcademyService {

    private final AcademyRepository academyRepository;
    private final UserService userService;

    public AcademyResponse createAcademy(CreateAcademyRequest createAcademyRequest) {
        if (academyRepository.findByAcademyName(createAcademyRequest.getAcademyName()).isPresent()) {
            throw new FieldTakenException("Ta nazwa akademii jest już zajęta");
        }
        academyRepository.save(Academy.builder()
                .academyName(createAcademyRequest.getAcademyName())
                .build());
        Academy academy = academyRepository.findByAcademyName(createAcademyRequest.getAcademyName()).get();
        userService.addAcademyToExistingUser(academy, createAcademyRequest.getLoggedUserEmail());
        return new AcademyResponse(academy);
    }

    public Academy getAcademyById(Long academyId)
    {
        return academyRepository.findById(academyId).orElseThrow(() -> new NotFoundException("Brak akademii o takim Id"));
    }
}


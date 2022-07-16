package edu.uni.lodz.system.akademia.pilkarska.domain.model.object;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.NotFoundException;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditObjectRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditObjectResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.DeleteResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.GetObjectsResponse;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.AcademyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObjectService {
    private final ObjectRepository objectRepository;
    private final AcademyService academyService;

    public GetObjectsResponse getObjects(Long academyId) {
        Academy academy = academyService.getAcademyById(academyId);
        return new GetObjectsResponse(objectRepository.getObjectsByAcademy(academy)
                .orElseThrow(() -> new NotFoundException("Brak obiektów w tej akademii")));
    }

    public CreateEditObjectResponse createObject(CreateEditObjectRequest createEditObjectRequest) {
        Academy academy = academyService.getAcademyById(createEditObjectRequest.getAcademyId());
        Object object = new Object(createEditObjectRequest.getPlaceName(), createEditObjectRequest.getStreet(), createEditObjectRequest.getCity(), createEditObjectRequest.getZipCode(),academy);
        return new CreateEditObjectResponse(objectRepository.save(object));
    }

    public CreateEditObjectResponse editObject(CreateEditObjectRequest createEditObjectRequest, Long objectId) {
        Object objectToUpdate = findObjectById(objectId);
        objectToUpdate.setCity(createEditObjectRequest.getCity());
        objectToUpdate.setStreet(createEditObjectRequest.getStreet());
        objectToUpdate.setPlaceName(createEditObjectRequest.getPlaceName());
        objectToUpdate.setZipCode(createEditObjectRequest.getZipCode());
        return new CreateEditObjectResponse(objectRepository.save(objectToUpdate));
    }

    public DeleteResponse deleteObject(Long objectId) {
        objectRepository.deleteById(objectId);
        return new DeleteResponse("Pomyślnie usunięto obiekt");
    }

    public Object findObjectById(Long objectId) {
        if (objectId != null) {
            return objectRepository.findById(objectId).orElseThrow(() -> new NotFoundException("Brak obiektu o takim Id"));
        }
        return null;
    }
}

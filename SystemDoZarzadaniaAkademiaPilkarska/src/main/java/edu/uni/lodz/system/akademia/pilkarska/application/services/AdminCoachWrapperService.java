package edu.uni.lodz.system.akademia.pilkarska.application.services;

import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditEventRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditObjectRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllEventsResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditEventResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditObjectResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.DeleteResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.EventDto;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.GetObjectsResponse;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.event.EventService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.object.ObjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminCoachWrapperService {

    private final ObjectService objectService;
    private final EventService eventService;

    public GetObjectsResponse getObjects(Long academyId){
        return objectService.getObjects(academyId);
    }

    public CreateEditObjectResponse createObject(CreateEditObjectRequest createEditObjectRequest){
        return objectService.createObject(createEditObjectRequest);
    }

    public CreateEditObjectResponse editObject(CreateEditObjectRequest createEditObjectRequest,Long objectId){
        return objectService.editObject(createEditObjectRequest,objectId);
    }

    public DeleteResponse deleteObject(Long objectId){
        return objectService.deleteObject(objectId);
    }

    public CreateEditEventResponse createEvent(CreateEditEventRequest createEditEventRequest ){
        return eventService.createEvent(createEditEventRequest);
    }
    public AllEventsResponse getEvents(Long academyId){
        return eventService.getEvents(academyId);
    }

    public EventDto getEventById(Long eventId){
        return eventService.getEventDtoById(eventId);
    }

    public CreateEditEventResponse editEvent(CreateEditEventRequest createEditEventRequest,Long eventId){
        return eventService.editEvent(createEditEventRequest,eventId);
    }
    public DeleteResponse deleteEvent(Long eventId){
        return eventService.deleteEvent(eventId);
    }
}

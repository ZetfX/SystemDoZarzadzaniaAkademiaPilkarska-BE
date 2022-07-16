package edu.uni.lodz.system.akademia.pilkarska.domain.model.event;

import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.EventTimeException;
import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.NotFoundException;
import edu.uni.lodz.system.akademia.pilkarska.Exceptions.exceptions.OrganizerException;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditEventRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.AllEventsResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.CreateEditEventResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.DeleteResponse;
import edu.uni.lodz.system.akademia.pilkarska.application.responses.EventDto;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.Academy;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.academy.AcademyService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.object.Object;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.object.ObjectService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroup;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.trainingGroup.TrainingGroupService;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.User;
import edu.uni.lodz.system.akademia.pilkarska.domain.model.user.UserService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class EventService {

    private final TrainingGroupService trainingGroupService;
    private final ObjectService objectService;
    private final UserService userService;
    private final AcademyService academyService;
    private final EventRepository eventRepository;


    public AllEventsResponse getEvents(Long academyId) {
        Academy academy = academyService.getAcademyById(academyId);
        Set<Event> events = eventRepository.getEventsByAcademy(academy)
                .orElseThrow(() -> new NotFoundException("Brak wydarzeń w tej akademii"));

        Set<EventDto> modifiedEvents = new HashSet<>();
        for (var event : events) {

            modifiedEvents.add(toEventDto(event));
        }

        return new AllEventsResponse(modifiedEvents);
    }

    public CreateEditEventResponse createEvent(CreateEditEventRequest createEditEventRequest) {
        if (isStartTimeLater(createEditEventRequest.getEndTime(),createEditEventRequest.getStartTime())) {
            throw new EventTimeException("Czas rozpoczęcia nie może być późniejszy niż czas zakończenia");
        }

        Time endTime = new Time(createEditEventRequest.getEndTime());
        Time startTime = new Time(createEditEventRequest.getStartTime());
        validateEventTimeAndCurrentTime(createEditEventRequest.getEventDate(), startTime);
        TrainingGroup trainingGroup = trainingGroupService.getTrainingGroupById(createEditEventRequest.getTrainingGroupId());
        Object object = objectService.findObjectById(createEditEventRequest.getObjectId());
        User user = userService.getUserById(createEditEventRequest.getOrganizerId());
        Academy academy = academyService.getAcademyById(createEditEventRequest.getAcademyId());

        return new CreateEditEventResponse(eventRepository.save(new Event(createEditEventRequest.getEventTitle(), createEditEventRequest.getEventDate(), startTime
                , endTime,
                trainingGroup, object, user, academy)));

    }

    public EventDto getEventDtoById(Long eventId){
        if (eventId != null) {
           Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Brak wydarzenia o takim id"));

           return toEventDto(event);

        }
        return null;
    }

    public Event getEventById(Long eventId){
        if (eventId != null) {
            return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Brak wydarzenia o takim id"));

        }
        return null;
    }

    private void validateEventTimeAndCurrentTime(Date eventDate, Time eventStartTime) {
        var now = LocalDateTime.now();
        if (LocalDate.ofInstant(eventDate.toInstant(), ZoneId.systemDefault()).isEqual(now.toLocalDate())) {
            if (eventStartTime.toLocalTime().isBefore(now.toLocalTime())) {
                throw new EventTimeException("Godzina rozpoczęcia wydarzenia musi być późniejsza niż aktualna");
            }
        }
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private EventDto toEventDto(Event event) {
        LocalDate eventDate = convertToLocalDateViaInstant(event.getEventDate());
        LocalTime startTime = event.getStartTime().toLocalTime();
        LocalTime endTime = event.getEndTime().toLocalTime();

        LocalDateTime eventStartDateTime = LocalDateTime.of(eventDate, startTime);
        LocalDateTime eventEndDateTime = LocalDateTime.of(eventDate, endTime);


        return new EventDto(event.getId(), event.getEventTitle(),event.getEventDate(), eventStartDateTime, eventEndDateTime,
                event.getTrainingGroup(), event.getObject(), event.getOrganizer(), event.getAcademy());
    }

    public CreateEditEventResponse editEvent(CreateEditEventRequest createEditEventRequest,Long eventId){

        Event eventToUpdate = getEventById(eventId);

       if(isOrganizer(eventToUpdate,createEditEventRequest.getOrganizerId()))
       {
           throw new OrganizerException("Ten użytkownik nie jest organizatorem tego wydarzenia");
       }

        if (isStartTimeLater(createEditEventRequest.getEndTime(),createEditEventRequest.getStartTime())) {
            throw new EventTimeException("Czas rozpoczęcia nie może być późniejszy lub taki sam jak czas zakończenia");
        }

        Time endTime = new Time(createEditEventRequest.getEndTime());
        Time startTime = new Time(createEditEventRequest.getStartTime());
        validateEventTimeAndCurrentTime(createEditEventRequest.getEventDate(), startTime);
        TrainingGroup trainingGroup = trainingGroupService.getTrainingGroupById(createEditEventRequest.getTrainingGroupId());
        Object object = objectService.findObjectById(createEditEventRequest.getObjectId());


        eventToUpdate.setEventTitle(createEditEventRequest.getEventTitle());
        eventToUpdate.setStartTime(startTime);
        eventToUpdate.setEndTime(endTime);
        eventToUpdate.setTrainingGroup(trainingGroup);
        eventToUpdate.setObject(object);
       return new CreateEditEventResponse(eventRepository.save(eventToUpdate));
    }



    private boolean isOrganizer(Event event,  Long organizerId){
       return event.getOrganizer().getId().equals(organizerId);
    }
    private boolean isStartTimeLater(Long endTime, Long startTime)
    {
       return endTime <= startTime;
    }


    public DeleteResponse deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
        return new DeleteResponse("Pomyślnie usunięto wydarzenie");
    }
}

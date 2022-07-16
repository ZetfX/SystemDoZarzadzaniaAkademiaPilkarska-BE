package edu.uni.lodz.system.akademia.pilkarska.application.controllers;

import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditEventRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.CreateEditObjectRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.services.AdminCoachWrapperService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-coach")
@AllArgsConstructor
public class AdminCoachWrapperController {

    private final AdminCoachWrapperService adminCoachWrapperService;

    @GetMapping("/allObjects/{academyId}")
    public ResponseEntity<?> getObjects(@Valid @PathVariable("academyId") Long academyId) {
        return ResponseEntity.ok(adminCoachWrapperService.getObjects(academyId));
    }

    @PostMapping("/createObject")
    public ResponseEntity<?> createObject(@Valid @RequestBody CreateEditObjectRequest createEditObjectRequest) {
        return ResponseEntity.ok(adminCoachWrapperService.createObject(createEditObjectRequest));
    }

    @PutMapping("/editObject/{objectId}")
    public ResponseEntity<?> editObject(@Valid @RequestBody CreateEditObjectRequest createEditObjectRequest, @PathVariable("objectId") Long objectId) {
        return ResponseEntity.ok(adminCoachWrapperService.editObject(createEditObjectRequest, objectId));
    }

    @DeleteMapping("/deleteObject/{objectId}")
    public ResponseEntity<?> deleteObject(@PathVariable("objectId") Long objectId) {
        return ResponseEntity.ok(adminCoachWrapperService.deleteObject(objectId));
    }


    @PostMapping("/createEvent")
    public ResponseEntity <?> createEvent(@Valid @RequestBody CreateEditEventRequest createEditEventRequest){
        return ResponseEntity.ok(adminCoachWrapperService.createEvent(createEditEventRequest));
    }

    @GetMapping("/allEvents/{academyId}")
    public ResponseEntity<?> getEvents(@Valid @PathVariable("academyId") Long academyId) {
        return ResponseEntity.ok(adminCoachWrapperService.getEvents(academyId));
    }

    @GetMapping("/getEventById/{eventId}")
        public ResponseEntity<?> getEventById(@Valid @PathVariable("eventId") Long eventId){
            return ResponseEntity.ok(adminCoachWrapperService.getEventById(eventId));
    }

    @PutMapping("/editEvent/{eventId}")
    public ResponseEntity<?> editEvent(@Valid @RequestBody CreateEditEventRequest createEditEventRequest, @PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(adminCoachWrapperService.editEvent(createEditEventRequest, eventId));
    }

    @DeleteMapping("/deleteEvent/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(adminCoachWrapperService.deleteEvent(eventId));
    }

}

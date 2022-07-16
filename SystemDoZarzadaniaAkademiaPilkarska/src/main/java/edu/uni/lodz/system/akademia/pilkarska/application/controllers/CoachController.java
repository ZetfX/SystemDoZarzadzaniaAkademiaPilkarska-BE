package edu.uni.lodz.system.akademia.pilkarska.application.controllers;

import edu.uni.lodz.system.akademia.pilkarska.application.services.CoachMainService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/coach")
@AllArgsConstructor
public class CoachController {
    private final CoachMainService coachMainService;

    @GetMapping("/myPlayers/{userId}")
    public ResponseEntity<?> getCoachPlayers(@Valid @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(coachMainService.getCoachPlayers(userId));
    }

    @GetMapping("/myTrainingGroup/{userId}")
    public ResponseEntity<?> getCoachTrainingGroup(@Valid @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(coachMainService.getCoachTrainingGroup(userId));
    }

}

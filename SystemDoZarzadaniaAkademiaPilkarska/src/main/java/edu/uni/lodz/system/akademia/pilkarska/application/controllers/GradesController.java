package edu.uni.lodz.system.akademia.pilkarska.application.controllers;

import edu.uni.lodz.system.akademia.pilkarska.application.requests.AddCoachGradeRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.requests.AddPlayerGradeRequest;
import edu.uni.lodz.system.akademia.pilkarska.application.services.GradesMainService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/grades")
@AllArgsConstructor
public class GradesController {


    private final GradesMainService gradesMainService;

    @PostMapping("/addPlayerGrade")
            public ResponseEntity<?>addPlayerGrade(@RequestBody AddPlayerGradeRequest addPlayerGradeRequest) {
        return ResponseEntity.ok(gradesMainService.addPlayerGrade(addPlayerGradeRequest));
    }

    @GetMapping("/getNotGradedPlayers/{userId}")
    public ResponseEntity<?>getNotGradedPlayers(@Valid @PathVariable("userId") Long userId ) {
        return ResponseEntity.ok(gradesMainService.getNotGradedPlayers(userId));
    }


    @GetMapping ("getNotGradedCoaches/{userId}")
    public ResponseEntity<?>getNotGradedCoaches(@Valid @PathVariable("userId") Long userId ) {
        return ResponseEntity.ok(gradesMainService.getNotGradedCoaches(userId));
    }


    @PostMapping("/addCoachGrade")
    public ResponseEntity<?>addPlayerGrade(@RequestBody AddCoachGradeRequest addCoachGradeRequest) {
        return ResponseEntity.ok(gradesMainService.addCoachGrade(addCoachGradeRequest));
    }

    @GetMapping ("getPlayerGrades/{userId}")
    public ResponseEntity<?>getPlayerGrades(@Valid @PathVariable("userId") Long userId ) {
        return ResponseEntity.ok(gradesMainService.getPlayerGrades(userId));
    }

    @GetMapping ("getAllCoachesGrades/{academyId}")
    public ResponseEntity<?> getAllCoachesGrades(@Valid @PathVariable("academyId") Long academyId ) {
        return ResponseEntity.ok(gradesMainService.getAllCoachesGrades(academyId));
    }


}

package edu.uni.lodz.system.akademia.pilkarska.application.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@AllArgsConstructor
@Getter
@Setter
public class AllEventsResponse {
    private Set<EventDto> events;
}

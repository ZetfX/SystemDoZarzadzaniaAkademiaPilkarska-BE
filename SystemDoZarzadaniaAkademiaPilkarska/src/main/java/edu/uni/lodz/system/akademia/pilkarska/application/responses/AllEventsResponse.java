package edu.uni.lodz.system.akademia.pilkarska.application.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AllEventsResponse {
    private Set<EventDto> events;
}

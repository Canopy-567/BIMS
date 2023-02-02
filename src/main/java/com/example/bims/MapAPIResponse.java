package com.example.bims;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.List;

@Getter
@Setter
public class MapAPIResponse {
    private String code;
    private List<Integer> distances;
    private List<Destinations> destinations;
    private List<List<Integer>> durations;
    private List<Destinations> sources;
}

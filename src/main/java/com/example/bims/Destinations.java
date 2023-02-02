package com.example.bims;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Destinations {
    private String hint;
    private Double distance;
    private String name;
    private List<Double> location;
}

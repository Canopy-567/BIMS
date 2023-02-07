package com.example.bims;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name="office")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Office {

    private static final long serialVersionUID = -321234690742300L;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Office_Name", nullable = false)
    private String Office_Name;

    private String Office_City;
    private String Office_State;
    private String Office_Street;
    private Long Office_ZIP;
    private Double Latitude;
    private Double Longitude;
    private String Office_Code;
    private Long Capacity;
}

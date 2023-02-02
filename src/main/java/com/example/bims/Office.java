package com.example.bims;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name="office")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Office {

    private static final long serialVersionUID = -321234690742300L;

    private String Office_City;
    private String Office_State;
    private String Office_Street;
    private Long Office_ZIP;
    private Double Latitude;
    private Double Longitude;
    @Id
    private String Office_Name;
    private String Office_Code;
    private Long Capacity;
}

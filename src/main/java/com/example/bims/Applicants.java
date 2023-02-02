package com.example.bims;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Date;

@Entity
@Table(name="applicants")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Applicants {
    private static final long serialVersionUID = -321234690742300L;

    @Id
    private String passport;
    private Date priority_date;
    private Date DOB;
    private String Title;
    private String Given_Name;
    private String Surname;
    private String Gender;
    private String Citizenship;
    private String Country_Of_Birth;
    private String Street_Address;
    private String City_Of_Residence;
    private String State;
    private String ZIP;
    private String Latitude;
    private String Longitude;
}

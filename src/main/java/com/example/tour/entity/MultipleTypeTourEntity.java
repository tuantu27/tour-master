package com.example.tour.entity;


import com.example.tour.model.dto.MultipleTypeTourDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="multipleTypeTour")
public class MultipleTypeTourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long multipleTypeTourId;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tourId")
    private ToursEntity tour;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "typeTourId")
    private TypeTourEntity typeTour;

    public static MultipleTypeTourDTO toDto(MultipleTypeTourEntity mul){
        MultipleTypeTourDTO mulDto = new MultipleTypeTourDTO();
        mulDto.setMultipleTypeTourId(mul.getMultipleTypeTourId());
        return mulDto;
    }

}

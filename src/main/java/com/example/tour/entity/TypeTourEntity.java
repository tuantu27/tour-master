package com.example.tour.entity;
import javax.persistence.*;

import com.example.tour.model.dto.MultipleTypeTourDTO;
import com.example.tour.model.dto.TypeTourDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="typeTour")
public class TypeTourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeTourId;

    private String nameTypeTour;
    private String region; // 1 Mien Bac , 2 Mien Trung ,3 Mien Nam
    private int status;
    @OneToMany(mappedBy = "typeTour")
    private List<MultipleTypeTourEntity> lstMuTypeTour;

    public static TypeTourDTO toDto(TypeTourEntity typeTour){
        TypeTourDTO typeTourDTO = new TypeTourDTO();
        typeTourDTO.setTypeTourId(typeTour.getTypeTourId());
        typeTourDTO.setNameTypeTour(typeTour.getNameTypeTour());
        typeTourDTO.setRegion(typeTour.getRegion());
        typeTourDTO.setStatus(typeTour.getStatus());
        List<MultipleTypeTourDTO> multipleTypeTourDTOS = new ArrayList<>();
        typeTour.getLstMuTypeTour().stream().forEach(e ->{
            MultipleTypeTourDTO multipleTypeTourDTO = new MultipleTypeTourDTO();
            multipleTypeTourDTO.setMultipleTypeTourId(e.getMultipleTypeTourId());;
            multipleTypeTourDTOS.add(multipleTypeTourDTO);
        });
        typeTourDTO.setMultipleTypeTourDTOS(multipleTypeTourDTOS);
        return typeTourDTO;

    }

}

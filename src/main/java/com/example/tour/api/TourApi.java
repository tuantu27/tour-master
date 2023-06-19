package com.example.tour.api;


import com.example.tour.model.dto.DateTourDTO;
import com.example.tour.service.IDateTourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TourApi {

    @Autowired
    IDateTourService iDateTourService;

    @GetMapping(value = "/viewQuantity/{id}/{date}")
    public ResponseEntity<DateTourDTO> viewQuantity(@PathVariable("id")Long tourId, @PathVariable("date")String date){
        String[] words = date.split("-");
        String dateValue = words[2]+'/'+words[1]+'/'+words[0];
        DateTourDTO dateTourDTO = iDateTourService.getByTourIdAndDate(tourId,dateValue);
        return new ResponseEntity<DateTourDTO>(dateTourDTO, HttpStatus.OK);
    }

}

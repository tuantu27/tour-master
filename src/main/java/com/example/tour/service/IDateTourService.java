package com.example.tour.service;

import com.example.tour.model.dto.DateTourDTO;

public interface IDateTourService {
    DateTourDTO getByTourIdAndDate(Long tourId,String startDate);
    void updateDateTour(Long dateTourId,Long numberRest);
}

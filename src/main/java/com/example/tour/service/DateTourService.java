package com.example.tour.service;

import com.example.tour.entity.DateTourEntity;
import com.example.tour.entity.ToursEntity;
import com.example.tour.model.dto.DateTourDTO;
import com.example.tour.model.dto.ToursDTO;
import com.example.tour.repository.DateTourRepository;
import com.example.tour.repository.ITourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DateTourService implements  IDateTourService{
    @Autowired
    DateTourRepository dateTourRepository;
    @Autowired
    ITourRepository iTourRepository;


    @Override
    public DateTourDTO getByTourIdAndDate(Long tourId, String startDate) {
        DateTourDTO dateTourDTO = new DateTourDTO();
        Optional<DateTourEntity> dateTourEntity = Optional.ofNullable(dateTourRepository.getByTour_TourIdAndStartDate(tourId, startDate));
        if(dateTourEntity.isEmpty()){
            Optional<ToursEntity> toursEntity = iTourRepository.findById(tourId);
            DateTourEntity dateTourEntity1 = new DateTourEntity();
            dateTourEntity1.setStartDate(startDate);
            dateTourEntity1.setQuantity(toursEntity.get().getQuantity());
            dateTourEntity1.setTour(iTourRepository.findById(tourId).get());
            DateTourEntity dateTourEntity2 = dateTourRepository.save(dateTourEntity1);
            dateTourDTO.setQuantity(dateTourEntity2.getQuantity());
            dateTourDTO.setDateTourId(dateTourEntity2.getDateTourId());
            dateTourDTO.setStartDate(dateTourEntity2.getStartDate());
        }else{
            dateTourDTO.setQuantity(dateTourEntity.get().getQuantity());
            dateTourDTO.setDateTourId(dateTourEntity.get().getDateTourId());
            dateTourDTO.setStartDate(dateTourEntity.get().getStartDate());
        }
        return dateTourDTO;
    }

    @Override
    public void updateDateTour(Long dateTourId, Long numberRest) {
        DateTourEntity dateTourEntity = dateTourRepository.findById(dateTourId).get();
        dateTourEntity.setQuantity(numberRest);
        dateTourRepository.save(dateTourEntity);

    }
}

package com.example.tour.service;

import com.example.tour.model.dto.MultipleTypeTourDTO;

public interface IMultipleTypeTourService {
    void saveMultipleTT(MultipleTypeTourDTO multipleTypeTourDTO);
    void updateMultipleTT(MultipleTypeTourDTO multipleTypeTourDTO);

    void deleteMultipleTT(Long tourId, Long typeTourId );

}

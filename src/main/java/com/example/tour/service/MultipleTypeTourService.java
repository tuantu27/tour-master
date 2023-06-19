package com.example.tour.service;

import com.example.tour.entity.MultipleTypeTourEntity;
import com.example.tour.model.dto.MultipleTypeTourDTO;
import com.example.tour.repository.IMultipleTypeTourRepository;
import com.example.tour.repository.ITourRepository;
import com.example.tour.repository.ITypeTourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultipleTypeTourService implements IMultipleTypeTourService {

    @Autowired
    IMultipleTypeTourRepository iMultipleTypeTourRepository;

    @Autowired
    ITourRepository iTourRepository;

    @Autowired
    ITypeTourRepository iTypeTourRepository;


    @Override
    public void saveMultipleTT(MultipleTypeTourDTO multipleTypeTourDTO) {
        MultipleTypeTourEntity multipleTypeTourEntity = new MultipleTypeTourEntity();
        multipleTypeTourEntity.setTour(iTourRepository.findById(multipleTypeTourDTO.getToursDTO().getTourId()).get());
        multipleTypeTourEntity.setTypeTour(iTypeTourRepository.findById(multipleTypeTourDTO.getTypeTourDTO().getTypeTourId()).get());
        iMultipleTypeTourRepository.save(multipleTypeTourEntity);
    }

    @Override
    public void updateMultipleTT(MultipleTypeTourDTO multipleTypeTourDTO) {
        MultipleTypeTourEntity multipleTypeTourEntity = iMultipleTypeTourRepository.findById(multipleTypeTourDTO.getMultipleTypeTourId()).get();
        multipleTypeTourEntity.setTour(iTourRepository.findById(multipleTypeTourDTO.getToursDTO().getTourId()).get());
        multipleTypeTourEntity.setTypeTour(iTypeTourRepository.findById(multipleTypeTourDTO.getTypeTourDTO().getTypeTourId()).get());
        iMultipleTypeTourRepository.save(multipleTypeTourEntity);
    }

    @Override
    public void deleteMultipleTT(Long tourId, Long typeTourId) {
        iMultipleTypeTourRepository.deleteMultipleTT(tourId,typeTourId);
    }
}

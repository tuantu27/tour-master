package com.example.tour.service;

import com.example.tour.entity.ReviewsEntity;
import com.example.tour.model.dto.ReviewsDTO;
import com.example.tour.repository.IReviewRepository;
import com.example.tour.repository.ITourRepository;
import com.example.tour.repository.ITypeTourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    IReviewRepository iReviewRepository;
    @Autowired
    ITourRepository tourRepository;

    @Override
    public List<ReviewsDTO> getReviewByTourId(Long tourId) {
        List<ReviewsEntity> reviewsEntities = iReviewRepository.getReviewByTourId(tourId);
        List<ReviewsDTO> reviewsDTOS = new ArrayList<>();
        for(ReviewsEntity entity : reviewsEntities){
            ReviewsDTO reviewsDTO = new ReviewsDTO();
            reviewsDTO.setTourId(entity.getTour().getTourId());
            reviewsDTO.setContent(entity.getContent());
            reviewsDTO.setReviewId(entity.getReviewId());
            reviewsDTO.setFullName(entity.getFullName());
            reviewsDTO.setCreateAt(entity.getCreateAt());
            reviewsDTOS.add(reviewsDTO);
        }
        return reviewsDTOS;
    }

    @Override
    public Long saveReview(ReviewsDTO reviewsDTO) {
        ReviewsEntity reviewsEntity = new ReviewsEntity();
        reviewsEntity.setFullName(reviewsDTO.getFullName());
        reviewsEntity.setContent(reviewsDTO.getContent());
        reviewsEntity.setCreateAt(reviewsDTO.getCreateAt());
        reviewsEntity.setTour(tourRepository.findById(reviewsDTO.getTourId()).get());
        ReviewsEntity reviewsEntity1 = iReviewRepository.save(reviewsEntity);
        return reviewsEntity1.getReviewId();
    }
}

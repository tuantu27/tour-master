package com.example.tour.service;

import com.example.tour.model.dto.ReviewsDTO;

import java.util.List;

public interface IReviewService  {

    List<ReviewsDTO> getReviewByTourId(Long tourId);
    Long saveReview(ReviewsDTO reviewsDTO);

}

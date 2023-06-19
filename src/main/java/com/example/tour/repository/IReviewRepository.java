package com.example.tour.repository;

import com.example.tour.entity.ReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IReviewRepository extends JpaRepository<ReviewsEntity, Long> {

    @Query(value = "select t from ReviewsEntity t  where t.tour.tourId=:tourId")
    List<ReviewsEntity> getReviewByTourId(@Param("tourId") Long tourId);
}

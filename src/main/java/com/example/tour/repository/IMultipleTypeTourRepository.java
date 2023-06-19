package com.example.tour.repository;

import com.example.tour.entity.MultipleTypeTourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface IMultipleTypeTourRepository extends JpaRepository<MultipleTypeTourEntity,Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE from multiple_type_tour a where a.tour_id=:tourId and a.type_tour_id=:typeTourId",nativeQuery = true)
    void deleteMultipleTT(@Param("tourId") Long tourId,@Param("typeTourId") Long typeTourId);


}

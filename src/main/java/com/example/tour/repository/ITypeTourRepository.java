package com.example.tour.repository;

import com.example.tour.entity.TypeTourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITypeTourRepository  extends JpaRepository<TypeTourEntity,Long> {

    @Query(value = "select a.name_type_tour ,a.type_tour_id,a.region,a.status from type_tour a , multiple_type_tour b where a.type_tour_id = b.type_tour_id and b.tour_id=?1",nativeQuery = true)
    List<TypeTourEntity> getTypeTourByTourId(Long tourId);

    List<TypeTourEntity> getTypeTourEntitiesByStatus(int status);
}

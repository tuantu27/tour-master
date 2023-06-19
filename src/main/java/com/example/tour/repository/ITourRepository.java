package com.example.tour.repository;

import com.example.tour.entity.ToursEntity;
import com.example.tour.model.dto.ToursDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITourRepository extends JpaRepository<ToursEntity,Long> {
    List<ToursEntity> getToursEntitiesByAccount_AccountIdAndStatus(Long accountId,int status);

    @Query(value = "select t from ToursEntity t JOIN t.lstMulTypeTour mt where mt.typeTour.typeTourId=:typeTourId")
    List<ToursEntity> getTourByTypeTourId(@Param("typeTourId") Long typeTourId);

    @Query(value = "select t from ToursEntity t where t.tourName like %:name% and t.startDate =:date and t.status=:status")
    List<ToursEntity> getTourByNameAndDate(@Param("name") String name, @Param("date") String date, @Param("status") int status);

    @Query(value = "select t from ToursEntity t where t.tourName like %:name% and  t.status=:status")
    List<ToursEntity> getTourByName(@Param("name") String name , @Param("status") int status);

    List<ToursEntity> getToursEntitiesByStatus(int status);

}

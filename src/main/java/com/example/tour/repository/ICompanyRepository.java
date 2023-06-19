package com.example.tour.repository;

import com.example.tour.entity.CompanysEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompanyRepository extends JpaRepository<CompanysEntity,Long> {

    CompanysEntity getCompanysEntitiesByAccountsEntity_AccountId(Long id);

    List<CompanysEntity> getCompanysEntitiesByStatus(int status);
    @Query(value = "select c from CompanysEntity c where c.companyName like %:content%")
    List<CompanysEntity>getCompanyByName(@Param("content")String content);

}

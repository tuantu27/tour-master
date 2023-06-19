package com.example.tour.repository;

import com.example.tour.entity.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<CustomersEntity,Long> {
}

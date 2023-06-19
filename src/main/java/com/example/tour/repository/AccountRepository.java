package com.example.tour.repository;

import com.example.tour.entity.AccountsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface AccountRepository extends JpaRepository<AccountsEntity,Long> {
    AccountsEntity searchByAccountId(Long id);
    @Query("SELECT a FROM AccountsEntity a WHERE a.username LIKE :x ")
    Page<AccountsEntity> searchByUsername(@Param("x") String s, Pageable pageable);
    AccountsEntity findByUsername(String username);

    AccountsEntity getAccountsEntitiesByCompany_CompanyId(Long companyId);
}

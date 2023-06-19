package com.example.tour.service;

import com.example.tour.model.dto.AccountsDTO;
import com.example.tour.model.dto.CompanysDTO;

import java.util.List;

public interface IAccountService {
    AccountsDTO getById(Long id);
    AccountsDTO getByCompanyId(Long companyId);

    void updateAccount(AccountsDTO accountsDTO);

}

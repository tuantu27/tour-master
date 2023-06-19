package com.example.tour.service;

import com.example.tour.entity.AccountsEntity;
import com.example.tour.model.dto.AccountsDTO;
import com.example.tour.model.dto.CompanysDTO;
import com.example.tour.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public AccountsDTO getById(Long id) {
        AccountsEntity accountsEntity = accountRepository.findById(id).get();
        AccountsDTO accountsDTO = new AccountsDTO();
        accountsDTO.setAccountId(accountsEntity.getAccountId());
        accountsDTO.setUserName(accountsEntity.getUsername());
        return accountsDTO;
    }

    @Override
    public AccountsDTO getByCompanyId(Long companyId) {
        AccountsEntity accountsEntity = accountRepository.getAccountsEntitiesByCompany_CompanyId(companyId);
        AccountsDTO accountsDTO = new AccountsDTO();
        accountsDTO.setAccountId(accountsEntity.getAccountId());
        accountsDTO.setUserName(accountsEntity.getUsername());
        accountsDTO.setStatus(accountsEntity.getStatus());
        return accountsDTO;
    }

    @Override
    public void updateAccount(AccountsDTO accountsDTO) {
        AccountsEntity accountsEntity = accountRepository.findById(accountsDTO.getAccountId()).get();
        accountsEntity.setAccountId(accountsDTO.getAccountId());
        accountsEntity.setStatus(accountsDTO.getStatus());
        accountsEntity.setPassword(accountsDTO.getPassWord());
    }


}

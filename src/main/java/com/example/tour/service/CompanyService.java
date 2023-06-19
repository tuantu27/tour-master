package com.example.tour.service;

import com.example.tour.entity.AccountsEntity;
import com.example.tour.entity.CompanysEntity;
import com.example.tour.model.dto.CompanysDTO;
import com.example.tour.repository.AccountRepository;
import com.example.tour.repository.ICompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService implements ICompanyService{
    @Autowired
    ICompanyRepository iCompanyRepository;
    @Autowired
    AccountRepository accountRepository;
    @Override
    public CompanysDTO getByAccountId(Long accountId) {
        CompanysEntity companysEntity = iCompanyRepository.getCompanysEntitiesByAccountsEntity_AccountId(accountId);
        CompanysDTO companysDTO = new CompanysDTO();
        companysDTO.setCompanyId(companysEntity.getCompanyId());
        companysDTO.setCompanyName(companysEntity.getCompanyName());
        companysDTO.setAddress(companysEntity.getAddress());
        companysDTO.setCompanyCode(companysEntity.getCompanyCode());
        companysDTO.setEmail(companysEntity.getEmail());
        return companysDTO;
    }

    @Override
    public void updateProfile(CompanysDTO companysDTO) {
        CompanysEntity companysEntity = iCompanyRepository.findById(companysDTO.getCompanyId()).get();
        companysEntity.setCompanyName(companysDTO.getCompanyName());
        companysEntity.setAddress(companysDTO.getAddress());
        companysEntity.setCompanyCode(companysDTO.getCompanyCode());
        companysEntity.setEmail(companysDTO.getEmail());
        companysEntity.setStatus(companysDTO.getStatus());
        iCompanyRepository.save(companysEntity);

    }

    @Override
    public List<CompanysDTO> getAll() {
        List<CompanysEntity> companysEntities = iCompanyRepository.findAll();
        List<CompanysDTO>companysDTOS = new ArrayList<>();
        for(CompanysEntity companysEntity : companysEntities){
            CompanysDTO companysDTO = new CompanysDTO();

            Optional<AccountsEntity> accountsEntity  = Optional.ofNullable(accountRepository.getAccountsEntitiesByCompany_CompanyId(companysEntity.getCompanyId()));
            if(!accountsEntity.isEmpty()){
                companysDTO.setAccountId(accountsEntity.get().getAccountId());
            }else {
                companysDTO.setAccountId((long) -1);
            }
            companysDTO.setCompanyId(companysEntity.getCompanyId());
            companysDTO.setCompanyName(companysEntity.getCompanyName());
            companysDTO.setAddress(companysEntity.getAddress());
            companysDTO.setCompanyCode(companysEntity.getCompanyCode());
            companysDTO.setEmail(companysEntity.getEmail());
            companysDTO.setStatus(companysEntity.getStatus());
            companysDTOS.add(companysDTO);
        }
        return companysDTOS;
    }

    @Override
    public CompanysDTO getById(Long id) {
        CompanysEntity companysEntity = iCompanyRepository.findById(id).get();
        CompanysDTO companysDTO = new CompanysDTO();
        companysDTO.setCompanyId(companysEntity.getCompanyId());
        companysDTO.setCompanyName(companysEntity.getCompanyName());
        companysDTO.setStatus(companysEntity.getStatus());
        companysDTO.setAddress(companysEntity.getAddress());
        companysDTO.setCompanyCode(companysEntity.getCompanyCode());
        companysDTO.setEmail(companysEntity.getEmail());
        return companysDTO;
    }

    @Override
    public Long saveCompany(CompanysDTO companysDTO) {
        CompanysEntity companysEntity = new CompanysEntity();
        companysEntity.setCompanyName(companysDTO.getCompanyName());
        companysEntity.setCompanyCode(companysDTO.getCompanyCode());
        companysEntity.setAddress(companysDTO.getAddress());
        companysEntity.setEmail(companysDTO.getEmail());
        companysEntity.setStatus(3);
        CompanysEntity entity = iCompanyRepository.save(companysEntity);
        return entity.getCompanyId();
    }

    @Override
    public List<CompanysDTO> getLstByStatus(int status) {
        List<CompanysEntity> companysEntities = iCompanyRepository.getCompanysEntitiesByStatus(status);
        List<CompanysDTO>companysDTOS = new ArrayList<>();
        for(CompanysEntity companysEntity : companysEntities){
            CompanysDTO companysDTO = new CompanysDTO();
            companysDTO.setCompanyId(companysEntity.getCompanyId());
            companysDTO.setCompanyName(companysEntity.getCompanyName());
            companysDTO.setAddress(companysEntity.getAddress());
            companysDTO.setCompanyCode(companysEntity.getCompanyCode());
            companysDTO.setEmail(companysEntity.getEmail());
            companysDTO.setStatus(companysEntity.getStatus());
            companysDTOS.add(companysDTO);
        }
        return companysDTOS;
    }

    @Override
    public List<CompanysDTO> searchByName(String content) {
        List<CompanysEntity> companysEntities = iCompanyRepository.getCompanyByName(content);
        List<CompanysDTO>companysDTOS = new ArrayList<>();
        for(CompanysEntity companysEntity : companysEntities){
            CompanysDTO companysDTO = new CompanysDTO();

            Optional<AccountsEntity> accountsEntity  = Optional.ofNullable(accountRepository.getAccountsEntitiesByCompany_CompanyId(companysEntity.getCompanyId()));
            if(!accountsEntity.isEmpty()){
                companysDTO.setAccountId(accountsEntity.get().getAccountId());
            }else {
                companysDTO.setAccountId((long) -1);
            }
            companysDTO.setCompanyId(companysEntity.getCompanyId());
            companysDTO.setCompanyName(companysEntity.getCompanyName());
            companysDTO.setAddress(companysEntity.getAddress());
            companysDTO.setCompanyCode(companysEntity.getCompanyCode());
            companysDTO.setEmail(companysEntity.getEmail());
            companysDTO.setStatus(companysEntity.getStatus());
            companysDTOS.add(companysDTO);
        }
        return companysDTOS;

    }
}

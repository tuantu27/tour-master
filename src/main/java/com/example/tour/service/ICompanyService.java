package com.example.tour.service;

import com.example.tour.model.dto.CompanysDTO;

import java.util.List;

public interface ICompanyService {
    CompanysDTO getByAccountId(Long accountId);
    void updateProfile(CompanysDTO companysDTO);
    List<CompanysDTO> getAll();
    CompanysDTO getById(Long id);

    Long saveCompany(CompanysDTO companysDTO);
    List<CompanysDTO> getLstByStatus(int status);
    List<CompanysDTO> searchByName(String content);




}

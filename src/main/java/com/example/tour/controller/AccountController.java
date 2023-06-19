package com.example.tour.controller;

import com.example.tour.entity.AccountsEntity;
import com.example.tour.entity.CompanysEntity;
import com.example.tour.entity.RoleEntity;
import com.example.tour.model.dto.CompanysDTO;
import com.example.tour.repository.AccountRepository;
import com.example.tour.repository.ICompanyRepository;
import com.example.tour.repository.RoleRepository;
import com.example.tour.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ICompanyService iCompanyService;
    @Autowired
    ICompanyRepository iCompanyRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/create")
    public String create(Model model) {

        List<CompanysDTO> companysDTOS = iCompanyService.getLstByStatus(3);
        model.addAttribute("lstCompany",companysDTOS);
        return "account/register.html";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute @Valid AccountsEntity accounts , @RequestParam("role") String roles,@RequestParam("companyId") Long companyId) throws IOException {
        accounts.setPassword(new BCryptPasswordEncoder().encode(accounts.getPassword()));
        accounts.setStatus(1);
        CompanysEntity companysEntity = iCompanyRepository.findById(companyId).get();
        accounts.setCompany(companysEntity);
        companysEntity.setStatus(1);
        iCompanyRepository.save(companysEntity);
        AccountsEntity user1 = accountRepository.save(accounts);
        if (user1 != null){
            RoleEntity role = new RoleEntity();
            role.setUser(user1);
            if("SubAdmin".equals(roles)){

                role.setRole("SubAdmin");
            }else if("Admin".equals(roles)){
                role.setRole("Admin");
            }

            roleRepository.save(role);
        }
        return "redirect:/admin/company";
    }
}
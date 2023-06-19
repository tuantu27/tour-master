package com.example.tour.controller;

import com.example.tour.model.dto.AccountsDTO;
import com.example.tour.model.dto.CompanysDTO;
import com.example.tour.model.dto.TypeTourDTO;
import com.example.tour.service.IAccountService;
import com.example.tour.service.ICompanyService;
import com.example.tour.service.ITypeTourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ICompanyService iCompanyService;
    @Autowired
    IAccountService iAccountService;

    @Autowired
    ITypeTourService iTypeTourService;


    @GetMapping("/company")
    public String view_company(Model model, @RequestParam(value="success",required = false) String message) {
        List<CompanysDTO> lstCompany = iCompanyService.getAll();
        model.addAttribute("lstCompany",lstCompany);
        if(message != null || message !=""){
            model.addAttribute("success", message);
        }else{
        }
        return "admin";
    }
    @GetMapping(value="/change-status/{valueStt}/{id}")
    public String change_status(Model model, @PathVariable("valueStt") int status, @PathVariable("id")Long id,RedirectAttributes redirectAttributes) {
        CompanysDTO companysDTO = iCompanyService.getById(id);
        AccountsDTO accountsDTO = iAccountService.getByCompanyId(id);
        companysDTO.setStatus(status);
        accountsDTO.setStatus(status);
        iCompanyService.updateProfile(companysDTO);
        iAccountService.updateAccount(accountsDTO);
        redirectAttributes.addAttribute("success","Thay đổi trạng thái thành công!");
        return "redirect:/admin/company";
    }

    @GetMapping("/new-company")
    public String view_add_company(Model model) {
        CompanysDTO companysDTO = new CompanysDTO();
        model.addAttribute("companyDTO",companysDTO);
        return "newCompany";
    }

    @PostMapping(value = "/addCompany")
    public String saveCompany(Model model, @ModelAttribute("companyDTO")CompanysDTO companysDTO,RedirectAttributes redirectAttributes){
        iCompanyService.saveCompany(companysDTO);
        redirectAttributes.addAttribute("success","Thêm công ty thành công!");
        return "redirect:/admin/company";

    }

    @GetMapping("/search/{content}")
    public String search_company(Model model,@PathVariable("content") String content) {
        List<CompanysDTO> lstCompany = new ArrayList<>();
        if(!content.equals("all")){
            lstCompany= iCompanyService.searchByName(content);
        }else{
            lstCompany = iCompanyService.getAll();
        }
        model.addAttribute("lstCompany",lstCompany);
        return "admin";
    }

    @GetMapping("/destination")
    public String viewDestination(Model model,@RequestParam(value="success",required = false) String message) {
        List<TypeTourDTO> lstTypeTour = iTypeTourService.getAll();
        model.addAttribute("lstTypeTour", lstTypeTour);
        if(message != null || message !=""){
            model.addAttribute("success", message);
        }else{
        }
        return "destination";
    }
    @GetMapping("/update_destination/{id}/{name}/{value}")
    public String updateTypeTour(Model model,@PathVariable("id")Long id,@PathVariable("name")String name,@PathVariable("value")String region,RedirectAttributes redirectAttributes) {
        TypeTourDTO typeTourDTO = iTypeTourService.getById(id);
        typeTourDTO.setNameTypeTour(name);
        typeTourDTO.setRegion(region);
        typeTourDTO.setStatus(1);
        iTypeTourService.updateTypeTour(typeTourDTO);
        redirectAttributes.addAttribute("success","Cập nhật địa điểm thành công!");
        return "redirect:/admin/destination";
    }
    @GetMapping("/delete_des/{id}")
    public String deleteTypeTour(Model model,@PathVariable("id")Long id,RedirectAttributes redirectAttributes) {
        TypeTourDTO typeTourDTO = iTypeTourService.getById(id);
        typeTourDTO.setStatus(0);
        iTypeTourService.updateTypeTour(typeTourDTO);
        redirectAttributes.addAttribute("success","Xóa địa điểm thành công!");
        return "redirect:/admin/destination";
    }

}

package com.example.tour.controller;

import com.example.tour.config.MvcConfig;
import com.example.tour.entity.AccountsEntity;
import com.example.tour.exception.CustomException;
import com.example.tour.model.dto.*;
import com.example.tour.service.*;
import com.example.tour.utils.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/subAdmin")
public class SubAdminController {
    @Autowired
    ITypeTourService iTypeTourService;

    @Autowired
    IMultipleTypeTourService iMultipleTypeTourService;
    @Autowired
    ITourService iTourService;

    @Autowired
    MvcConfig mvcConfig;

    @Autowired
    IAccountService iAccountService;

    @Autowired
    ICompanyService iCompanyService;

    @Autowired
    IBookingService iBookingService;

    @Autowired
    ICustomerService iCustomerService;


    @GetMapping("/home")
    public String viewHome() {

        return "subadmin";
    }
    @GetMapping("/tour")
    public String viewTour(HttpSession session, Model model, @RequestParam(value="success",required = false) String message) {
        AccountsEntity accountsEntity = (AccountsEntity) session.getAttribute("user");
        List<ToursDTO> toursDTOS = iTourService.getToursByAccountId(accountsEntity.getAccountId());
//      String message = (String) model.getAttribute("success");
        if(message != null || message !=""){
            model.addAttribute("success", message);
        }else{

        }

        model.addAttribute("lstTour", toursDTOS);
        return "tour_subAdmin";
    }

    @GetMapping("/new-Tour")
    public String viewNewTour(Model model) {
        ToursDTO toursDTO = new ToursDTO();
        TypeTourDTO typeTourDTO = new TypeTourDTO();
        List<TypeTourDTO> lstTypeTour = iTypeTourService.getAll();
        model.addAttribute("tourDTO",toursDTO);
        model.addAttribute("lstTypeTour", lstTypeTour);
        model.addAttribute("typeTourDTO",typeTourDTO);

        return "newTour";
    }
    @PostMapping("/saveTour")
    public String saveTour(@ModelAttribute("tourDTO")ToursDTO toursDTO,@RequestParam("newImage") MultipartFile file
            ,@RequestParam("destination")Long[] lstTypeTourId,HttpSession session,RedirectAttributes redirectAttributes){
        AccountsEntity accountsEntity = (AccountsEntity) session.getAttribute("user");
        String nameFile = mvcConfig.uploadImages(file);
        toursDTO.setImgName(nameFile);
        toursDTO.setAccountId(accountsEntity.getAccountId());
        Long tourId = iTourService.saveTour(toursDTO);
        ToursDTO toursDTO1 = iTourService.getById(tourId);
        Long[] arrTypeTourId = lstTypeTourId;
        for (int i = 0; i<arrTypeTourId.length;i++){
            MultipleTypeTourDTO temp = new MultipleTypeTourDTO();

            temp.setToursDTO(toursDTO1);
            temp.setTypeTourDTO(iTypeTourService.getById(arrTypeTourId[i]));
            iMultipleTypeTourService.saveMultipleTT(temp);
        }
        redirectAttributes.addAttribute("success","Thêm chuyến đi thành công!");
        return "redirect:/subAdmin/tour";
    }
    @PostMapping("/saveTypeTour")
    public String saveTypeTour(@ModelAttribute("typeTourDTO")TypeTourDTO typeTourDTO,RedirectAttributes redirectAttributes){
        iTypeTourService.saveTypeTour(typeTourDTO);
        redirectAttributes.addAttribute("success","Thêm địa điểm thành công!");
        return "redirect:/subAdmin/tour";
    }

    @GetMapping(value = "/edit-Tour/{id}")
    public String editProduct(Model model, @PathVariable("id")Long id){
        ToursDTO toursDTO = iTourService.getById(id);
        List<TypeTourDTO> lstTypeTour = iTypeTourService.getAll();
        List<TypeTourDTO> lstCurTypeTour = iTypeTourService.getLstByTourId(id);
        TypeTourDTO typeTourDTO = new TypeTourDTO();
        model.addAttribute("tourDTO",toursDTO);
        model.addAttribute("lstTypeTour", lstTypeTour);
        model.addAttribute("lstCurTypeTour", lstCurTypeTour);
        model.addAttribute("typeTourDTO",typeTourDTO);
        return "editTour";

    }
    @PostMapping("/updateTour")
    public String updateTour(@ModelAttribute("tourDTO")ToursDTO toursDTO,@RequestParam("newImage") MultipartFile file
            ,@RequestParam(value = "destination",required = false)Long[] lstTypeTourId,HttpSession session,RedirectAttributes redirectAttributes){
        AccountsEntity accountsEntity = (AccountsEntity) session.getAttribute("user");
        toursDTO.setAccountId(accountsEntity.getAccountId());
        String nameFile = mvcConfig.uploadImages(file);
        toursDTO.setImgName(nameFile);
        iTourService.updateTour(toursDTO);
        Long[] arrTypeTourId = lstTypeTourId;
        if(arrTypeTourId.length > 0){
            List arr = Arrays.asList(arrTypeTourId);
            List<TypeTourDTO> lstCurTypeTour = iTypeTourService.getLstByTourId(toursDTO.getTourId());
            List currArrId = new ArrayList<>();
            //xoa cac destination khong duoc select
            for(int i =0 ; i<lstCurTypeTour.size();i++){
                if(arr.contains(lstCurTypeTour.get(i).getTypeTourId())){
                    currArrId.add(lstCurTypeTour.get(i).getTypeTourId());
                    continue;
                }else if(!arr.contains(lstCurTypeTour.get(i).getTypeTourId())) {

                    Long currTT =lstCurTypeTour.get(i).getTypeTourId();
                    iMultipleTypeTourService.deleteMultipleTT(toursDTO.getTourId(),currTT);
                }
            }
            //them cac destination moi
            for(int j = 0 ;j<arr.size()-1;j++){
                if(!currArrId.contains(arr.get(j+1))){
                    MultipleTypeTourDTO temp = new MultipleTypeTourDTO();
                    temp.setToursDTO(toursDTO);
                    temp.setTypeTourDTO(iTypeTourService.getById((Long)arr.get(j+1)));
                    iMultipleTypeTourService.saveMultipleTT(temp);
                }

            }

        }
        redirectAttributes.addAttribute("success","Cập nhật thành công!");

        return "redirect:/subAdmin/tour";
    }

    @GetMapping(value = "/delete-Tour/{id}")
    public String deleteProduct(Model model, @PathVariable("id")Long id,RedirectAttributes redirectAttributes){
        iTourService.deleteTour(id);
        redirectAttributes.addAttribute("success","Xóa chuyến đi thành công!");
        return "redirect:/subAdmin/tour";

    }

    //company
    @GetMapping(value = "/edit-Profile/{id}")
    public String editProfile(Model model, @PathVariable("id")Long id,HttpSession session){
        AccountsEntity accountsEntity = (AccountsEntity) session.getAttribute("user");
        CompanysDTO companysDTO = iCompanyService.getByAccountId(accountsEntity.getAccountId());
        model.addAttribute("company",companysDTO);
        return "editProfile";

    }
    @PostMapping(value = "/updateProfile")
    public String updateCompany(Model model,@ModelAttribute("company")CompanysDTO companysDTO,RedirectAttributes redirectAttributes){
        iCompanyService.updateProfile(companysDTO);
        redirectAttributes.addAttribute("success","Cập nhật thành công!");
        return "redirect:/subAdmin/tour";

    }

    @GetMapping(value = "/review-Tour/{id}")
    public String reviewTour(Model model,@PathVariable("id") Long id){
        ToursDTO toursDTO = iTourService.getById(id);
        model.addAttribute("toursDTO",toursDTO);
        return "reviewTour";

    }
    @GetMapping(value = "/customer/{id}")
    public String viewCustomer(Model model,@PathVariable("id") Long accountId){
        List<BookingDTO> bookingDTOS = iBookingService.getAllByAccountIdAndStatus(accountId,1);
        model.addAttribute("bookingDTOS",bookingDTOS);
        return "customer";

    }
    @GetMapping(value = "/delete-Booking/{id}")
    public String deleteBooking(Model model, @PathVariable("id")Long id,RedirectAttributes redirectAttributes){
        iBookingService.deleteBooking(id);
        redirectAttributes.addAttribute("success","Xóa chuyến đi thành công!");
        return "redirect:/subAdmin/tour";

    }

    @GetMapping(value = "/view-Email/{booking_id}/{cus_id}")
    public String view_mail(Model model,@PathVariable("booking_id") Long booking_id,@PathVariable("cus_id")Long cus_id) throws CustomException {
        Formatter formatter = new Formatter();
        BookingDTO bookingDTO = iBookingService.getById(booking_id);
        CustomersDTO customersDTO = iCustomerService.getById(cus_id);
        ToursDTO toursDTO = iTourService.getById(bookingDTO.getToursDTO().getTourId());
        String formatAllPrice =  formatter.formatCurrency(String.valueOf(bookingDTO.getTotalPrice()),"VND");
        bookingDTO.setFormatAllPrice(formatAllPrice);
        model.addAttribute("bookingDTO",bookingDTO);
        model.addAttribute("customersDTO",customersDTO);
        model.addAttribute("toursDTO",toursDTO);
        return "view_mail";
    }
    @GetMapping(value = "/statics/{id}")
    public String view_statics(Model model , @PathVariable("id") Long accountId) throws CustomException {
        List<DataDTO> dataDTOList = iBookingService.getTotal_Month(accountId);
        List<DataDTO> dataDTOPeople = iBookingService.getTotal_People(accountId);
        List<DataDTO> lstDTO = new ArrayList<>();
        List<DataDTO> lstDTO1 = new ArrayList<>();
        for(int i = 1 ;i<=12;i++){
            DataDTO dto = new DataDTO();
            dto.setMonth(i);
            dto.setTotal(0L);
            dto.setTotalPeople(0L);
            lstDTO.add(dto);
            lstDTO1.add(dto);
        }
        for(int i = 0 ; i<lstDTO.size();i++){
            for (int j = 0 ; j<dataDTOList.size();j++){
                if(lstDTO.get(i).getMonth() == dataDTOList.get(j).getMonth()){
                    lstDTO.get(i).setTotal(dataDTOList.get(j).getTotal());
                }
            }
        }
        for(int i = 0 ; i<lstDTO1.size();i++){
            for (int j = 0 ; j<dataDTOPeople.size();j++){
                if(lstDTO1.get(i).getMonth() == dataDTOPeople.get(j).getMonth()){
                    lstDTO1.get(i).setTotalPeople(dataDTOPeople.get(j).getTotalPeople());
                }
            }
        }
        Map<String, Integer> graphData = new TreeMap<>();
        for (DataDTO dataDTO: lstDTO) {
            graphData.put(""+dataDTO.getMonth(), Math.toIntExact(dataDTO.getTotal()));
        }
        Map<String, Integer> graphDataPeople = new TreeMap<>();
        for (DataDTO dataDTO: lstDTO1) {
            graphDataPeople.put(""+dataDTO.getMonth(), Math.toIntExact(dataDTO.getTotalPeople()));
        }

        model.addAttribute("chartData", graphData);
        model.addAttribute("graphDataPeople", graphDataPeople);
        return "statics";
    }



}

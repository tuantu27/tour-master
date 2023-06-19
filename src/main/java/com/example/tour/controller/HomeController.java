package com.example.tour.controller;

import com.example.tour.exception.CustomException;
import com.example.tour.model.dto.DataDTO;
import com.example.tour.model.dto.ToursDTO;
import com.example.tour.model.dto.TypeTourDTO;
import com.example.tour.service.IBookingService;
import com.example.tour.service.ITourService;
import com.example.tour.service.ITypeTourService;
import com.example.tour.utils.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class HomeController {

    @Autowired
    ITourService iTourService;
    @Autowired
    ITypeTourService iTypeTourService;

    @Autowired
    IBookingService iBookingService;


    @GetMapping(value = "/home")
    public String showHomePage(Model model) throws CustomException {
        List<ToursDTO> lstTour =iTourService.getAll();
        Formatter formatter = new Formatter();
        for (ToursDTO tour : lstTour){
           String x =  formatter.formatCurrency(String.valueOf(tour.getPriceAdult()),"VND");
            tour.setFormatPrice(x);
        }
        List<TypeTourDTO> lstTypeTour = iTypeTourService.getAll();

        model.addAttribute("lstTour", lstTour);
        model.addAttribute("lstTypeTour", lstTypeTour);
        return "home";
    }
    @GetMapping(value = "/filterByType/{id}")
    public String filterByType(Model model, @PathVariable("id") Long typeTourId) throws CustomException {
        List<ToursDTO> lstTour =iTourService.getLstByTypeTourId(typeTourId);
        Formatter formatter = new Formatter();
        for (ToursDTO tour : lstTour){
            String x =  formatter.formatCurrency(String.valueOf(tour.getPriceAdult()),"VND");
            tour.setFormatPrice(x);
        }
        List<TypeTourDTO> lstTypeTour = iTypeTourService.getAll();

        model.addAttribute("lstTour", lstTour);
        model.addAttribute("lstTypeTour", lstTypeTour);
        return "home";
    }
    @GetMapping(value = "/search/{des}/{date}")
    public String filterTour(Model model, @PathVariable("des") String des,@PathVariable("date")String date) throws CustomException {
        List<ToursDTO> lstTour;
        if(des.equals("null")&& !date.equals("null")){
            String[] words = date.split("-");
            String dateValue = words[2]+'/'+words[1]+'/'+words[0];
            lstTour = iTourService.getTourByNameAndDate("",dateValue);
        }else if(!des.equals("null")&& date.equals("null")){
            lstTour =iTourService.getTourByName(des);

        }else{
            String[] words = date.split("-");
            String dateValue = words[2]+'/'+words[1]+'/'+words[0];
            lstTour =iTourService.getTourByNameAndDate(des,dateValue);
        }

        Formatter formatter = new Formatter();
        for (ToursDTO tour : lstTour){
            String x =  formatter.formatCurrency(String.valueOf(tour.getPriceAdult()),"VND");
            tour.setFormatPrice(x);
        }
        List<TypeTourDTO> lstTypeTour = iTypeTourService.getAll();
        model.addAttribute("lstTour", lstTour);
        model.addAttribute("lstTypeTour", lstTypeTour);
        return "home";
    }



}

package com.example.tour.controller;

import com.example.tour.exception.CustomException;
import com.example.tour.model.dto.BookingDTO;
import com.example.tour.model.dto.CustomersDTO;
//import com.example.tour.model.dto.Mail;
import com.example.tour.model.dto.Mail;
import com.example.tour.model.dto.ToursDTO;
//import com.example.tour.service.EmailSenderService;
import com.example.tour.service.EmailSenderService;
import com.example.tour.service.IBookingService;
import com.example.tour.service.ICustomerService;
import com.example.tour.service.ITourService;
import com.example.tour.utils.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContext;
import org.thymeleaf.spring5.context.webmvc.SpringWebMvcThymeleafRequestContext;

import javax.mail.MessagingException;
import org.thymeleaf.context.Context;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MailController {
    @Autowired
    private EmailSenderService emailService;
    @Autowired
    ITourService iTourService;

    @Autowired
    IBookingService iBookingService;

    @Autowired
    ICustomerService iCustomerService;



    @PostMapping("/send_mail")
    public String send_Email(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("customersDTO") CustomersDTO customersDTO, @ModelAttribute("toursDTO") ToursDTO toursDTO,
                             @ModelAttribute("bookingDTO") BookingDTO bookingDTO,RedirectAttributes redirectAttributes) throws MessagingException, IOException {
        Mail mail = new Mail();
        mail.setFrom("dinhkhactu2001@gmail.com");//replace with your desired email
        mail.setMailTo("tuantu27092001@gmail.com");//replace with your desired email
        mail.setSubject("Thông tin chuyến đi đã đặt");
        BindingAwareModelMap bindingMap = new BindingAwareModelMap();
        bindingMap.addAttribute("customersDTO", customersDTO);
        bindingMap.addAttribute("toursDTO", toursDTO);
        bindingMap.addAttribute("bookingDTO", bookingDTO);
//        model.put("name", "Developer!");
//        model.put("location", "United States");
//        model.put("sign", "Java Developer");
        mail.setProps(bindingMap);
        emailService.sendEmail(request,response,mail,bindingMap);
        redirectAttributes.addAttribute("success","Gửi mail thành công!");
        return "redirect:/subAdmin/tour";
    }
}

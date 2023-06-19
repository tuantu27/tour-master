package com.example.tour.controller;

import com.example.tour.entity.AccountsEntity;
import com.example.tour.entity.RoleEntity;
import com.example.tour.model.dto.RolesDTO;
import com.example.tour.repository.AccountRepository;
import com.example.tour.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    AccountRepository accountRepository;
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("data");
        return "login.html";
    }
    @PostMapping("/login")
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request,HttpSession session) throws IOException {
        List<AccountsEntity> users = accountRepository.findAll();
        String redirectURL = request.getContextPath();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        for (int i =0 ; i< users.size();i++) {
            if (users.get(i).getUsername().equals(username) && encoder.matches(password,users.get(i).getPassword())
                    && 1==(users.get(i).getStatus()) ) {
                for (RoleEntity role : users.get(i).getRoles()) {
                    if (role.getRole().equals("SubAdmin")) {
                        redirectURL = "/subAdmin/statics/" + users.get(i).getAccountId();
                    } else if (role.getRole().equals("Admin")) {
                        redirectURL = "/admin/company";
                    }
                }
                session.setAttribute("user",users.get(i));
                return "redirect:" + redirectURL;
            }

        }
        return "redirect:/login"; // views
    }
}





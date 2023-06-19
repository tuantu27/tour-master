package com.example.tour.service;

import com.example.tour.entity.AccountsEntity;
import com.example.tour.entity.RoleEntity;
import com.example.tour.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;



    //use loginsuccesshander
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountsEntity st = accountRepository.findByUsername(username);

        if (st == null) {
            throw new UsernameNotFoundException("not found");
        }

        List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();

        for (RoleEntity role : st.getRoles()) {
            list.add(new SimpleGrantedAuthority(role.getRole()));
        }

        // tao user cua security
        // user dang nhap hiện tại
        org.springframework.security.core.userdetails.User currentUser = new org.springframework.security.core.userdetails.User(
                username, st.getPassword(), list);
        return currentUser;

    }
}
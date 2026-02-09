package com.example.jwtdemo.Security;


import com.example.jwtdemo.repository.UserRepository;
import com.example.jwtdemo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomeUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repo;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUserName(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }


        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));


        return new  org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities

        );
    }
}

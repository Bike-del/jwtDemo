package com.example.jwtdemo.Controller;

import com.example.jwtdemo.Dto.loginDto;
import com.example.jwtdemo.Utils.JwtUtils;
import com.example.jwtdemo.service.UserService;
import com.example.jwtdemo.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class authController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;





    @Autowired
    private UserService userService;

    @GetMapping("test")
    public String test(){
        return "Testing.....";
    }

    // signup
    @PostMapping("/signup")
    public void singup(@RequestBody User user){
        userService.saved(user);
    }


    //login

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody loginDto request){

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
            String jwt =  jwtUtils.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(jwt);

        } catch (AuthenticationException e) {


            log.error("Exception Occurs while creating jwt token"+e);
            return new ResponseEntity<>("Incorrect Username and password",HttpStatus.UNAUTHORIZED);
        }



    }

}

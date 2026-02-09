package com.example.jwtdemo.Filter;

import com.example.jwtdemo.Utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class jwtFilter  extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService; // bring the impl of CustomeUserDetailsService class

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


//        System.out.println("DB USERNAME = " + user.getUserName());

          String authHeader = request.getHeader("Authorization");
          String jwtToken = null;
          String username = null;

          if(authHeader != null && authHeader.startsWith("Bearer")) {

              jwtToken = authHeader.substring(7);
              username = jwtUtils.extractUsername(jwtToken).trim();
          }


          //add username in security context holder

          if(username!=null){

              UserDetails userDetails = userDetailsService.loadUserByUsername(username);
              if(jwtUtils.isTokenValid(jwtToken,userDetails)){
                  UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                  auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                  SecurityContextHolder.getContext().setAuthentication(auth);
              }

          }

        filterChain.doFilter(request,response);
//        System.out.println("LOGIN USERNAME = " + request.getUserPrincipal().getName());



    }
}

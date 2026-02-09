package com.example.jwtdemo.Controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller()
public class pageController {

    //login page
    @GetMapping("/sign-in")
    public ModelAndView signIN(){
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    //registration page
    @GetMapping("/sign-up")
    public ModelAndView signUp(){
        ModelAndView mv = new ModelAndView("signup");
        return mv;
    }

    @GetMapping("/Dashboard")
    public ModelAndView dashboard(){
        ModelAndView mv = new ModelAndView("Dashboard");
        return mv;
    }

    //product page
    @GetMapping("/Product")
    public ModelAndView product(){
        ModelAndView mv = new ModelAndView("Product");
        return mv;
    }


}

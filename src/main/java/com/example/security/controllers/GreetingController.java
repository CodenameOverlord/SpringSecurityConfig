package com.example.security.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {


    @GetMapping("/loginPage")
    public String login(){
        return "Welcome to basic webpage";
    }

    //preAuthorize authorizes on the basis of role
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/userPage")
    public String userLogin(){
        return "This page is accessible to role USER";
    }

    //preAuthorize authorizes on the basis of role
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminPage")
    public String adminLogin(){
        return "This page is accessible to role admin";
    }

    /*
    @PreAuthorize("hasRole('PRIVILEGED')")
    @GetMapping("/privilegedPage")
    public String privilegedLogin(){
        return "This page is accessible to privileged role";
    }
    */

}

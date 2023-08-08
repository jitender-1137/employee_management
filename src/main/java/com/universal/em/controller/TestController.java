package com.universal.em.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/employee")
public class TestController {

    @GetMapping("/new")
    public String getPage(Model model){
        return "employee";
    }

    @GetMapping("/salary")
    public String getSalary(Model model){
        return "employeeSalary";
    }

    @GetMapping("/company")
    public String getCompany(Model model){
        return "companyDetail";
    }

    @GetMapping("/slip")
    public String getSlip(Model model){
        return "generateSlip";
    }

    @GetMapping("/reset")
    public @ResponseBody String getSlip(Model model, HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().invalidate();
        return "Success";
    }
}

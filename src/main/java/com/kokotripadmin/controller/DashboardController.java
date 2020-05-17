package com.kokotripadmin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {


    @RequestMapping("/")
    public String redirectToDashboard() {
        return "redirect:/dashboard";
    }

    @RequestMapping("/dashboard")
    public String showDashboard() {
        return "dashboard/dashboard";
    }

}

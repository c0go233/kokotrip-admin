package com.kokotripadmin.controller;


import com.kokotripadmin.viewmodel.login.LoginVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginController extends BaseController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login/login-form";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "login/access-denied";
    }

}

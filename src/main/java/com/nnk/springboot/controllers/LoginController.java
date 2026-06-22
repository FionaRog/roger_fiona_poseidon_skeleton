package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Handles authentication-related pages.
 *
 * <p>This controller exposes the custom login page and the access-denied page
 * used when an authenticated user tries to access a protected resource without
 * the required authority.</p>
 */
@Controller
public class LoginController {

    /**
     * Displays the login page used by Spring Security.
     *
     * @return the model and view for the login template
     */
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }


    /**
     * Displays the access-denied page for authenticated users without the
     * required role.
     *
     * @param principal the currently authenticated user
     * @return the model and view for the 403 template
     */
    @GetMapping("/access-denied")
    public ModelAndView accessDenied(Principal principal) {
        ModelAndView mav = new ModelAndView();

        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.addObject("username", principal.getName());

        mav.setViewName("403");

        return mav;
    }
}

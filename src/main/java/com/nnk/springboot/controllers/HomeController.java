package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * Handles the authenticated home pages of the application.
 */
@Controller
public class HomeController {

    /**
     * Displays the standard authenticated home page and exposes the connected
     * username to the template.
     *
     * @param model the model used to pass attributes to the view
     * @param principal the currently authenticated user
     * @return the home template name
     */
    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());

        return "/home";
    }

    /**
     * Redirects administrators from the legacy admin home route to the main
     * business listing page.
     *
     * @param model the model provided by Spring MVC
     * @return a redirect to the bid list page
     */
    @GetMapping("/admin/home")
    public String adminHome(Model model) {

        return "redirect:/bidList/list";
    }


}

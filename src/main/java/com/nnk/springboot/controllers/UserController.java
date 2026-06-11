package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.request.UserRequestDto;
import com.nnk.springboot.dtos.view.UserViewDto;
import com.nnk.springboot.services.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Handles MVC routes for displaying and managing User records.
 */
@Controller
public class UserController {

   private final IUserService userService;

   public UserController(IUserService userService) {
       this.userService = userService;
   }

    /**
     * Displays all User records.
     *
     * @param model the MVC model used to expose data to the view
     * @return the User list view
     */
    @GetMapping("/user/list")
    public String home(Model model)
    {
        List<UserViewDto> result = userService.getAllUsers();

        model.addAttribute("users", result);

        return "user/list";
    }

    /**
     * Displays the form used to create a new User.
     *
     * @param model the MVC model used to expose the form object
     * @return the User creation view
     */
    @GetMapping("/user/add")
    public String addUser(Model model) {

       model.addAttribute("user", new UserRequestDto());

        return "user/add";
    }

    /**
     * Validates and creates a new User.
     *
     * @param userRequestDto the submitted form data
     * @param result validation result for the submitted form
     * @param redirectAttributes flash attributes used after successful creation
     * @return the add view when validation fails, otherwise redirects to the list
     */
    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("user") UserRequestDto userRequestDto, BindingResult result, RedirectAttributes redirectAttributes) {

       if (result.hasErrors()) {
            return "user/add";
        }
       userService.addUser(userRequestDto);
       redirectAttributes.addFlashAttribute("successMessage", "User successfully added");

       return "redirect:/user/list";
    }

    /**
     * Displays the form used to update an existing User.
     *
     * @param id the technical identifier of the User to update
     * @param model the MVC model used to expose the form object
     * @return the User update view
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

       UserViewDto userViewDto = userService.getUserById(id);

       UserRequestDto form = new UserRequestDto();
       form.setUsername(userViewDto.getUsername());
       form.setFullname(userViewDto.getFullname());
       form.setRole(userViewDto.getRole());

        model.addAttribute("user", form);
        model.addAttribute("id", id);

        return "user/update";
    }

    /**
     * Validates and updates an existing User.
     *
     * @param id the technical identifier of the User to update
     * @param userRequestDto the submitted form data
     * @param result validation result for the submitted form
     * @param model the MVC model used when validation fails
     * @param redirectAttributes flash attributes used after successful update
     * @return the update view when validation fails, otherwise redirects to the list
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute("user")UserRequestDto userRequestDto ,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "user/update";
        }

        userService.updateUser(id, userRequestDto);
        redirectAttributes.addFlashAttribute("successMessage", "User successfully updated");

        return "redirect:/user/list";
    }

    /**
     * Deletes an existing User.
     *
     * @param id the technical identifier of the User to delete
     * @param redirectAttributes flash attributes used after successful deletion
     * @return redirects to the User list
     */
    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("successMessage", "User successfully deleted");

        return "redirect:/user/list";
    }
}

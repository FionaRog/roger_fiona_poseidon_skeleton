package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.request.RatingRequestDto;
import com.nnk.springboot.dtos.view.RatingViewDto;
import com.nnk.springboot.services.IRatingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;


/**
 * Handles MVC routes for displaying and managing Rating records.
 */
@Controller
public class RatingController {

    private final IRatingService ratingService;

    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Displays all Rating records.
     *
     * @param model     the MVC model used to expose data to the view
     * @param principal the currently authenticated user
     * @return the Rating list view
     */
    @GetMapping("/rating/list")
    public String home(Model model, Principal principal) {

        List<RatingViewDto> result = ratingService.getAllRatings();

        model.addAttribute("ratings", result);
        model.addAttribute("username", principal.getName());

        return "rating/list";
    }

    /**
     * Displays the form used to create a new Rating.
     *
     * @param model the MVC model used to expose the form object
     * @return the Rating creation view
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {

        model.addAttribute("rating", new RatingRequestDto());

        return "rating/add";
    }

    /**
     * Validates and creates a new Rating.
     *
     * @param ratingRequestDto   the submitted form data
     * @param result             validation result for the submitted form
     * @param redirectAttributes flash attributes used after successful creation
     * @return the add view when validation fails, otherwise redirects to the list
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("rating") RatingRequestDto ratingRequestDto, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "rating/add";
        }

        ratingService.addRating(ratingRequestDto);
        redirectAttributes.addFlashAttribute("successMessage", "Rating successfully added");

        return "redirect:/rating/list";
    }

    /**
     * Displays the form used to update an existing Rating.
     *
     * @param id    the technical identifier of the Rating to update
     * @param model the MVC model used to expose the form object
     * @return the Rating update view
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RatingViewDto ratingViewDto = ratingService.getRatingById(id);

        RatingRequestDto form = new RatingRequestDto();
        form.setFitchRating(ratingViewDto.getFitchRating());
        form.setMoodysRating(ratingViewDto.getMoodysRating());
        form.setSandPRating(ratingViewDto.getSandPRating());
        form.setOrderNumber(ratingViewDto.getOrderNumber());

        model.addAttribute("rating", form);
        model.addAttribute("id", id);

        return "rating/update";
    }

    /**
     * Validates and updates an existing Rating.
     *
     * @param id                 the technical identifier of the Rating to update
     * @param ratingRequestDto   the submitted form data
     * @param result             validation result for the submitted form
     * @param model              the MVC model used when validation fails
     * @param redirectAttributes flash attributes used after successful update
     * @return the update view when validation fails, otherwise redirects to the list
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid @ModelAttribute("rating") RatingRequestDto ratingRequestDto,
                               BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "rating/update";
        }

        ratingService.updateRating(id, ratingRequestDto);
        redirectAttributes.addFlashAttribute("successMessage", "Rating successfully updated");

        return "redirect:/rating/list";
    }

    /**
     * Deletes an existing Rating.
     *
     * @param id                 the technical identifier of the Rating to delete
     * @param redirectAttributes flash attributes used after successful deletion
     * @return redirects to the Rating list
     */
    @PostMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        ratingService.deleteRating(id);

        redirectAttributes.addFlashAttribute("successMessage", "Rating successfully deleted");

        return "redirect:/rating/list";
    }
}

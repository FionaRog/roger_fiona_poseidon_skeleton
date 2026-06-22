package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.request.BidListRequestDto;
import com.nnk.springboot.dtos.view.BidListViewDto;
import com.nnk.springboot.services.IBidListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;


/**
 * Handles web pages and form submissions for BidList management.
 *
 * <p>The controller connects BidList service operations to Thymeleaf templates
 * for listing, creating, updating and deleting bid list entries.</p>
 */
@Controller
public class BidListController {

    private IBidListService bidListService;

    /**
     * Creates the controller with the service used to manage BidList data.
     *
     * @param bidListService service exposing BidList CRUD operations
     */
    public BidListController(IBidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * Displays all bid list entries for the authenticated user.
     *
     * @param model the model used to pass bid list data to the view
     * @param principal the currently authenticated user
     * @return the bid list page
     */
    @GetMapping("/bidList/list")
    public String home(Model model, Principal principal) {

        List<BidListViewDto> bidList = bidListService.getBidList();

        model.addAttribute("bidLists", bidList);
        model.addAttribute("username", principal.getName());

        return "bidList/list";
    }

    /**
     * Displays the form used to create a new bid list entry.
     *
     * @param model the model used to expose an empty form object
     * @return the bid list creation page
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {

        model.addAttribute("bidListRequestDto", new BidListRequestDto());

        return "bidList/add";
    }

    /**
     * Validates and creates a new bid list entry from the submitted form.
     *
     * @param bidListRequestDto form data submitted by the user
     * @param result validation result for the submitted form
     * @param redirectAttributes flash attributes used after successful creation
     * @return the creation page when validation fails, otherwise redirects to the list page
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListRequestDto bidListRequestDto, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.addBidList(bidListRequestDto);

        redirectAttributes.addFlashAttribute("successMessage", "BidList has been successfully added");

        return "redirect:/bidList/list";
    }

    /**
     * Displays the update form for an existing bid list entry.
     *
     * @param id identifier of the bid list entry to update
     * @param model the model used to expose the current bid list values
     * @return the bid list update page
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidListViewDto bidView = bidListService.getBidListById(id);

        BidListRequestDto form = new BidListRequestDto();
        form.setAccount(bidView.getAccount());
        form.setBidQuantity(bidView.getBidQuantity());
        form.setType(bidView.getType());

        model.addAttribute("bidListRequestDto", form);
        model.addAttribute("id", id);
        return "bidList/update";
    }

    /**
     * Validates and updates an existing bid list entry.
     *
     * @param id identifier of the bid list entry to update
     * @param bidListRequestDto form data submitted by the user
     * @param result validation result for the submitted form
     * @param model the model used to restore the update page after validation errors
     * @param redirectAttributes flash attributes used after successful update
     * @return the update page when validation fails, otherwise redirects to the list page
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListRequestDto bidListRequestDto,
                            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "bidList/update";
        }

        bidListService.updateBidList(id, bidListRequestDto);

        redirectAttributes.addFlashAttribute("successMessage", "BidList has been updated");

        return "redirect:/bidList/list";
    }

    /**
     * Deletes an existing bid list entry.
     *
     * @param id identifier of the bid list entry to delete
     * @param redirectAttributes flash attributes used after successful deletion
     * @return a redirect to the bid list page
     */
    @PostMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        bidListService.deleteBidList(id);

        redirectAttributes.addFlashAttribute("successMessage", "BidList has been deleted");

        return "redirect:/bidList/list";
    }
}

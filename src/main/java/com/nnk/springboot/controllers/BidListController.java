package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.request.BidListRequestDto;
import com.nnk.springboot.dtos.view.BidListViewDto;
import com.nnk.springboot.services.IBidListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;


@Controller
public class BidListController {
    // TODO: Inject Bid service
    private IBidListService bidListService;

    public BidListController(IBidListService bidListService) {
        this.bidListService = bidListService;
    }

    @GetMapping("/bidList/list")
    public String home(Model model, Principal principal)    {
        // TODO: call service find all bids to show to the view

        List<BidListViewDto> bidList = bidListService.getBidList();

        model.addAttribute("bidLists",bidList);
        model.addAttribute("username",principal.getName());

        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {

        model.addAttribute("bidListRequestDto", new BidListRequestDto());

        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListRequestDto bidListRequestDto, BindingResult result, RedirectAttributes redirectAttributes) {
        // TODO: check data valid and save to db, after saving return bid list

        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.addBidList(bidListRequestDto);

        redirectAttributes.addFlashAttribute("successMessage", "BidList has been successfully added");

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidListViewDto bidView =  bidListService.getBidListById(id);

        BidListRequestDto form = new BidListRequestDto();
        form.setAccount(bidView.getAccount());
        form.setBidQuantity(bidView.getBidQuantity());
        form.setType(bidView.getType());

        model.addAttribute("bidListRequestDto", form);
        model.addAttribute("id",id);
        return "bidList/update";
    }

    // Changer @Post en @Put
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListRequestDto bidListRequestDto,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "bidList/update";
        }

        bidListService.updateBidList(id, bidListRequestDto);

        redirectAttributes.addFlashAttribute("successMessage", "BidList has been updated");

        return "redirect:/bidList/list";
    }

    @PostMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBidList(id);

        redirectAttributes.addFlashAttribute("successMessage", "BidList has been deleted");

        return "redirect:/bidList/list";
    }
}

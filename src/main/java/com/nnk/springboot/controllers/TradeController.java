package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.request.TradeRequestDto;
import com.nnk.springboot.dtos.view.TradeViewDto;
import com.nnk.springboot.services.ITradeService;
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
 * Handles MVC routes for displaying and managing Trade records.
 */
@Controller
public class TradeController {

    private final ITradeService tradeService;

    public TradeController(ITradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Displays all Trade records.
     *
     * @param model     the MVC model used to expose data to the view
     * @param principal the currently authenticated user
     * @return the Trade list view
     */
    @GetMapping("/trade/list")
    public String home(Model model, Principal principal) {
        List<TradeViewDto> results = tradeService.getAllTrades();

        model.addAttribute("trades", results);
        model.addAttribute("username", principal.getName());

        return "trade/list";
    }

    /**
     * Displays the form used to create a new Trade.
     *
     * @param model the MVC model used to expose the form object
     * @return the Trade creation view
     */
    @GetMapping("/trade/add")
    public String addUser(Model model) {

        model.addAttribute("trade", new TradeRequestDto());

        return "trade/add";
    }

    /**
     * Validates and creates a new Trade.
     *
     * @param tradeRequestDto    the submitted form data
     * @param result             validation result for the submitted form
     * @param redirectAttributes flash attributes used after successful creation
     * @return the add view when validation fails, otherwise redirects to the list
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute("trade") TradeRequestDto tradeRequestDto, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "trade/add";
        }

        tradeService.addTrade(tradeRequestDto);

        redirectAttributes.addFlashAttribute("successMessage", "Trade added");

        return "redirect:/trade/list";
    }

    /**
     * Displays the form used to update an existing Trade.
     *
     * @param id    the technical identifier of the Trade to update
     * @param model the MVC model used to expose the form object
     * @return the Trade update view
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        TradeViewDto tradeView = tradeService.getTradeById(id);

        TradeRequestDto form = new TradeRequestDto();
        form.setAccount(tradeView.getAccount());
        form.setType(tradeView.getType());
        form.setBuyQuantity(tradeView.getBuyQuantity());

        model.addAttribute("trade", form);
        model.addAttribute("id", id);

        return "trade/update";
    }

    /**
     * Validates and updates an existing Trade.
     *
     * @param id                 the technical identifier of the Trade to update
     * @param tradeRequestDto    the submitted form data
     * @param result             validation result for the submitted form
     * @param model              the MVC model used when validation fails
     * @param redirectAttributes flash attributes used after successful update
     * @return the update view when validation fails, otherwise redirects to the list
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid @ModelAttribute("trade") TradeRequestDto tradeRequestDto,
                              BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "trade/update";
        }

        tradeService.updateTrade(id, tradeRequestDto);

        redirectAttributes.addFlashAttribute("successMessage", "Trade updated");

        return "redirect:/trade/list";
    }

    /**
     * Deletes an existing Trade.
     *
     * @param id                 the technical identifier of the Trade to delete
     * @param redirectAttributes flash attributes used after successful deletion
     * @return redirects to the Trade list
     */
    @PostMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        tradeService.deleteTrade(id);

        redirectAttributes.addFlashAttribute("successMessage", "Trade deleted");

        return "redirect:/trade/list";
    }
}

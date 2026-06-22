package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.request.RuleNameRequestDto;
import com.nnk.springboot.dtos.view.RuleNameViewDto;
import com.nnk.springboot.services.IRuleNameService;
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
 * Handles MVC routes for displaying and managing RuleName records.
 */
@Controller
public class RuleNameController {

    private final IRuleNameService ruleNameService;

    public RuleNameController(IRuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    /**
     * Displays all RuleName records.
     *
     * @param model     the MVC model used to expose data to the view
     * @param principal the currently authenticated user
     * @return the RuleName list view
     */
    @GetMapping("/ruleName/list")
    public String home(Model model, Principal principal) {
        List<RuleNameViewDto> results = ruleNameService.getAllRuleNames();

        model.addAttribute("ruleNames", results);
        model.addAttribute("username", principal.getName());

        return "ruleName/list";
    }

    /**
     * Displays the form used to create a new RuleName.
     *
     * @param model the MVC model used to expose the form object
     * @return the RuleName creation view
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {

        model.addAttribute("ruleName", new RuleNameRequestDto());

        return "ruleName/add";
    }

    /**
     * Validates and creates a new RuleName.
     *
     * @param ruleName           the submitted form data
     * @param result             validation result for the submitted form
     * @param redirectAttributes flash attributes used after successful creation
     * @return the add view when validation fails, otherwise redirects after creation
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleName") RuleNameRequestDto ruleName, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.addRuleName(ruleName);

        redirectAttributes.addFlashAttribute("successMessage", "RuleName Added Successfully");

        return "redirect:/ruleName/list";
    }

    /**
     * Displays the form used to update an existing RuleName.
     *
     * @param id    the technical identifier of the RuleName to update
     * @param model the MVC model used to expose the form object
     * @return the RuleName update view
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        RuleNameViewDto ruleNameViewDto = ruleNameService.getRuleNamesById(id);

        RuleNameRequestDto form = new RuleNameRequestDto();
        form.setName(ruleNameViewDto.getName());
        form.setDescription(ruleNameViewDto.getDescription());
        form.setTemplate(ruleNameViewDto.getTemplate());
        form.setJson(ruleNameViewDto.getJson());
        form.setSqlStr(ruleNameViewDto.getSqlStr());
        form.setSqlPart(ruleNameViewDto.getSqlPart());

        model.addAttribute("ruleName", form);
        model.addAttribute("id", id);

        return "ruleName/update";
    }

    /**
     * Validates and updates an existing RuleName.
     *
     * @param id                 the technical identifier of the RuleName to update
     * @param ruleNameRequestDto the submitted form data
     * @param result             validation result for the submitted form
     * @param model              the MVC model used when validation fails
     * @param redirectAttributes flash attributes used after successful update
     * @return the update view when validation fails, otherwise redirects to the list
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid @ModelAttribute("ruleName") RuleNameRequestDto ruleNameRequestDto,
                                 BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "ruleName/update";
        }

        ruleNameService.updateRuleName(id, ruleNameRequestDto);
        redirectAttributes.addFlashAttribute("successMessage", "RuleName Updated Successfully");

        return "redirect:/ruleName/list";
    }

    /**
     * Deletes an existing RuleName.
     *
     * @param id                 the technical identifier of the RuleName to delete
     * @param redirectAttributes flash attributes used after successful deletion
     * @return redirects to the RuleName list
     */
    @PostMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        ruleNameService.deleteRuleName(id);

        redirectAttributes.addFlashAttribute("successMessage", "RuleName Deleted Successfully");

        return "redirect:/ruleName/list";
    }
}

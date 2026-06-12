package com.nnk.springboot.controllers;

import com.nnk.springboot.dtos.request.CurveRequestDto;
import com.nnk.springboot.dtos.view.CurveViewDto;
import com.nnk.springboot.services.ICurveService;
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
 * Handles MVC routes for displaying and managing CurvePoint records.
 */
@Controller
public class CurveController {

    private ICurveService curvePointService;

    public CurveController(ICurveService curvePointService){
              this.curvePointService=curvePointService;
    }


    /**
     * Displays all CurvePoint records.
     *
     * @param model the MVC model used to expose data to the view
     * @param principal the currently authenticated user
     * @return the CurvePoint list view
     */
    @GetMapping("/curvePoint/list")
    public String home(Model model, Principal principal)    {

        List<CurveViewDto> curveView = curvePointService.getAllCurve();

        model.addAttribute("curvePoints",curveView);
        model.addAttribute("username",principal.getName());

        return "curvePoint/list";
    }

    /**
     * Displays the form used to create a new CurvePoint.
     *
     * @param model the MVC model used to expose the form object
     * @return the CurvePoint creation view
     */
    @GetMapping("/curvePoint/add")
    public String addCurveForm(Model model) {

        model.addAttribute("curvePoint",new CurveRequestDto());

        return "curvePoint/add";
    }

    /**
     * Validates and creates a new CurvePoint.
     *
     * @param curvePoint the submitted form data
     * @param result validation result for the submitted form
     * @param redirectAttributes flash attributes used after successful creation
     * @return the add view when validation fails, otherwise redirects to the list
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid  @ModelAttribute("curvePoint") CurveRequestDto curvePoint, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.addCurve(curvePoint);

        redirectAttributes.addFlashAttribute("successMessage", "Curve has been added");
        return "redirect:/curvePoint/list";
    }

    /**
     * Displays the form used to update an existing CurvePoint.
     *
     * @param id the technical identifier of the CurvePoint to update
     * @param model the MVC model used to expose the form object
     * @return the CurvePoint update view
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        CurveViewDto curveView = curvePointService.getCurveById(id);

        CurveRequestDto form = new CurveRequestDto();
        form.setCurveId(curveView.getCurveId());
        form.setTerm(curveView.getTerm());
        form.setValue(curveView.getValue());

        model.addAttribute("curvePoint",form);
        model.addAttribute("id", id);

        return "curvePoint/update";
    }

    /**
     * Validates and updates an existing CurvePoint.
     *
     * @param id the technical identifier of the CurvePoint to update
     * @param curveRequestDto the submitted form data
     * @param result validation result for the submitted form
     * @param model the MVC model used when validation fails
     * @param redirectAttributes flash attributes used after successful update
     * @return the update view when validation fails, otherwise redirects to the list
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid @ModelAttribute("curvePoint") CurveRequestDto curveRequestDto,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "curvePoint/update";
        }

        curvePointService.updateCurve(id, curveRequestDto);

        redirectAttributes.addFlashAttribute("successMessage", "Curve has been updated");

        return "redirect:/curvePoint/list";
    }

    /**
     * Deletes an existing CurvePoint.
     *
     * @param id the technical identifier of the CurvePoint to delete
     * @param redirectAttributes flash attributes used after successful deletion
     * @return redirects to the CurvePoint list
     */
    @PostMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        curvePointService.deleteCurve(id);

        redirectAttributes.addFlashAttribute("successMessage", "Curve has been deleted");

        return "redirect:/curvePoint/list";
    }
}

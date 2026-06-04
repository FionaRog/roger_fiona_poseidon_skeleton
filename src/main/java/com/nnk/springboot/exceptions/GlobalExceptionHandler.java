package com.nnk.springboot.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BidListNotFoundException.class)
    public String handleBidListNotFound(BidListNotFoundException exception, RedirectAttributes redirectAttributes) {
        log.warn("BidList error: {}", exception.getMessage());

        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());

        return "redirect:/bidList/list";
    }

    @ExceptionHandler(CurvePointNotFoundException.class)
    public String handleCurvePointNotFound(CurvePointNotFoundException exception, RedirectAttributes redirectAttributes) {
        log.warn("CurvePoint error: {}", exception.getMessage());

        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());

        return "redirect:/curvePoint/list";
    }

    @ExceptionHandler(RatingNotFoundException.class)
    public String handleRatingNotFound(RatingNotFoundException exception, RedirectAttributes redirectAttributes) {
        log.warn("Rating error: {}", exception.getMessage());

        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());

        return "redirect:/rating/list";
    }

    @ExceptionHandler(RuleNameNotFoundException.class)
    public String handleRuleNameNotFound(RuleNameNotFoundException exception, RedirectAttributes redirectAttributes) {
        log.warn("RuleName error: {}", exception.getMessage());

        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());

        return "redirect:/ruleName/list";
    }
}

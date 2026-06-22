package com.nnk.springboot.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles application exceptions raised by MVC controllers and services.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles missing BidList entries by redirecting the user to the BidList
     * page with an error message.
     *
     * @param exception the exception raised when the BidList entry is missing
     * @param redirectAttributes flash attributes used to expose the error message
     * @return a redirect to the BidList listing page
     */
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

    @ExceptionHandler(TradeNotFoundException.class)
    public String handleTradeNotFound(TradeNotFoundException exception, RedirectAttributes redirectAttributes) {
        log.warn("Trade error: {}", exception.getMessage());

        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());

        return "redirect:/trade/list";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException exception, RedirectAttributes redirectAttributes) {
        log.warn("User error: {}", exception.getMessage());

        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());

        return "redirect:/user/list";
    }
}

package com.nnk.springboot.services;

import com.nnk.springboot.dtos.request.RuleNameRequestDto;
import com.nnk.springboot.dtos.view.RuleNameViewDto;

import java.util.List;

/**
 * Defines business operations for managing RuleName records.
 */
public interface IRuleNameService {

    /**
     * Retrieves all RuleName records.
     *
     * @return RuleName records formatted for display
     */
    List<RuleNameViewDto> getAllRuleNames();

    /**
     * Retrieves a RuleName by its technical identifier.
     *
     * @param id the technical identifier of the RuleName
     * @return the matching RuleName formatted for display
     */
    RuleNameViewDto getRuleNamesById(Integer id);

    /**
     * Creates a RuleName from submitted form data.
     *
     * @param ruleNameRequestDto the form data used to create the RuleName
     * @return the created RuleName formatted for display
     */
    RuleNameViewDto addRuleName(RuleNameRequestDto ruleNameRequestDto);

    /**
     * Updates an existing RuleName.
     *
     * @param id                 the technical identifier of the RuleName to update
     * @param ruleNameRequestDto the form data containing updated values
     * @return the updated RuleName formatted for display
     */
    RuleNameViewDto updateRuleName(Integer id, RuleNameRequestDto ruleNameRequestDto);

    /**
     * Deletes an existing RuleName.
     *
     * @param id the technical identifier of the RuleName to delete
     */
    void deleteRuleName(Integer id);
}

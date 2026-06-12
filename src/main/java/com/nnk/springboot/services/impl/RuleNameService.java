package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dtos.request.RuleNameRequestDto;
import com.nnk.springboot.dtos.view.RuleNameViewDto;
import com.nnk.springboot.exceptions.RuleNameNotFoundException;
import com.nnk.springboot.mappers.RuleNameMapper;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.IRuleNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for RuleName business operations.
 */
@Slf4j
@Service
public class RuleNameService implements IRuleNameService {

    private final RuleNameRepository ruleNameRepository;

    private final RuleNameMapper ruleNameMapper;

    public RuleNameService(RuleNameRepository ruleNameRepository, RuleNameMapper ruleNameMapper) {
        this.ruleNameRepository = ruleNameRepository;
        this.ruleNameMapper = ruleNameMapper;
    }

    /**
     * Retrieves all RuleName records and maps them to display DTOs.
     *
     * @return all RuleName records formatted for display
     */
    public List<RuleNameViewDto> getAllRuleNames() {
        log.info("getting all RuleNames");

        List<RuleName> result = ruleNameRepository.findAll();
        log.info("Found {} RuleNames",  result.size());

        return result.stream()
                .map(ruleNameMapper::toDto)
                .toList();
    }

    /**
     * Retrieves one RuleName by id.
     *
     * @param id the technical identifier of the RuleName
     * @return the matching RuleName formatted for display
     * @throws RuleNameNotFoundException when no RuleName exists for the given id
     */
    public RuleNameViewDto getRuleNamesById(Integer id) {
        log.info("getting RuleName with ID {}", id);

        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> {
                        log.warn("RuleName with ID {} not found", id);
                        return new RuleNameNotFoundException(id); });

        return ruleNameMapper.toDto(ruleName);
    }

    /**
     * Creates and persists a new RuleName.
     *
     * @param ruleNameRequestDto the submitted form data
     * @return the created RuleName formatted for display
     */
    @Transactional
    public RuleNameViewDto addRuleName(RuleNameRequestDto ruleNameRequestDto) {
        log.info("Adding RuleName");

        RuleName ruleName = ruleNameMapper.toEntity(ruleNameRequestDto);

        RuleName savedRuleName = ruleNameRepository.save(ruleName);
        log.info("RuleName added with id{}", savedRuleName.getId());

        return ruleNameMapper.toDto(savedRuleName);
    }

    /**
     * Updates an existing RuleName.
     *
     * @param id the technical identifier of the RuleName to update
     * @param ruleNameRequestDto the submitted form data containing updated values
     * @return the updated RuleName formatted for display
     * @throws RuleNameNotFoundException when no RuleName exists for the given id
     */
    @Transactional
    public RuleNameViewDto updateRuleName(Integer id, RuleNameRequestDto ruleNameRequestDto) {
        log.info("updating RuleName with ID {}", id);

        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("RuleName with ID {} not found", id);
                    return new RuleNameNotFoundException(id); });

        ruleName.setName(ruleNameRequestDto.getName());
        ruleName.setDescription(ruleNameRequestDto.getDescription());
        ruleName.setJson(ruleNameRequestDto.getJson());
        ruleName.setTemplate(ruleNameRequestDto.getTemplate());
        ruleName.setSqlStr(ruleNameRequestDto.getSqlStr());
        ruleName.setSqlPart(ruleNameRequestDto.getSqlPart());

        RuleName savedRuleName = ruleNameRepository.save(ruleName);
        log.info("RuleName updated with id{}", savedRuleName.getId());

       return ruleNameMapper.toDto(savedRuleName);
    }

    /**
     * Deletes an existing RuleName.
     *
     * @param id the technical identifier of the RuleName to delete
     * @throws RuleNameNotFoundException when no RuleName exists for the given id
     */
    @Transactional
    public void deleteRuleName(Integer id) {
        log.info("deleting RuleName with ID {}", id);

        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("RuleName with ID {} not found", id);
                    return new RuleNameNotFoundException(id);
                });

        ruleNameRepository.deleteById(id);
        log.info("deleted RuleName with ID {}", id);
    }
}

package com.nnk.springboot.services;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dtos.request.RuleNameRequestDto;
import com.nnk.springboot.dtos.view.RuleNameViewDto;
import com.nnk.springboot.exceptions.RuleNameNotFoundException;
import com.nnk.springboot.mappers.RuleNameMapper;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.impl.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @Mock
    private RuleNameMapper ruleNameMapper;

    private IRuleNameService ruleNameService;

    @BeforeEach
    public void setUp() {
        ruleNameService = new RuleNameService(ruleNameRepository, ruleNameMapper);
    }

    @Test
    @DisplayName("Should get all RuleNames")
    public void getAllRuleNames() {
        RuleName ruleName = new RuleName();
        ruleName.setName("RuleName");
        ruleName.setId(1);
        ruleName.setDescription("RuleDescription");
        ruleName.setTemplate("RuleTemplate");
        ruleName.setJson("RuleJson");
        ruleName.setSqlStr("RuleSqlStr");
        ruleName.setSqlPart("RuleSqlPart");

        RuleNameViewDto ruleNameViewDto = new RuleNameViewDto();
        ruleNameViewDto.setName("RuleName");
        ruleNameViewDto.setId(1);
        ruleNameViewDto.setDescription("RuleDescription");
        ruleNameViewDto.setTemplate("RuleTemplate");
        ruleNameViewDto.setJson("RuleJson");
        ruleNameViewDto.setSqlStr("RuleSqlStr");
        ruleNameViewDto.setSqlPart("RuleSqlPart");

        when(ruleNameRepository.findAll()).thenReturn(List.of(ruleName));
        when(ruleNameMapper.toDto(ruleName)).thenReturn(ruleNameViewDto);

        List<RuleNameViewDto> result = ruleNameService.getAllRuleNames();

        assertEquals(1, result.size());
        assertEquals(ruleNameViewDto.getName(), result.get(0).getName());
        verify(ruleNameRepository).findAll();
        verify(ruleNameMapper).toDto(ruleName);
    }

    @Test
    @DisplayName("Should get RuleName by ID")
    public void getRuleNameById() {
        RuleName ruleName = new RuleName();
        ruleName.setName("RuleName");
        ruleName.setId(1);

        RuleNameViewDto ruleNameViewDto = new RuleNameViewDto();
        ruleNameViewDto.setName("RuleName");
        ruleNameViewDto.setId(1);

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameMapper.toDto(ruleName)).thenReturn(ruleNameViewDto);

        RuleNameViewDto result = ruleNameService.getRuleNamesById(1);

        assertEquals(ruleNameViewDto.getName(), result.getName());
        verify(ruleNameRepository).findById(1);
        verify(ruleNameMapper).toDto(ruleName);
    }

    @Test
    @DisplayName("Should throw exception when RuleName is not found")
    public void getRuleNameNotFoundTest() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuleNameNotFoundException.class, () -> ruleNameService.getRuleNamesById(1) );

        verify(ruleNameRepository).findById(1);
        verifyNoInteractions(ruleNameMapper);
    }

    @Test
    @DisplayName("Should add RuleName")
    public void addRuleName() {
        RuleNameRequestDto requestDto = new RuleNameRequestDto();
        requestDto.setName("RuleName");
        requestDto.setDescription("RuleDescription");
        requestDto.setTemplate("RuleTemplate");
        requestDto.setJson("RuleJson");
        requestDto.setSqlStr("RuleSqlStr");
        requestDto.setSqlPart("RuleSqlPart");

        RuleName ruleName = new RuleName();
        ruleName.setName("RuleName");
        ruleName.setId(1);
        ruleName.setDescription("RuleDescription");
        ruleName.setTemplate("RuleTemplate");
        ruleName.setJson("RuleJson");
        ruleName.setSqlStr("RuleSqlStr");
        ruleName.setSqlPart("RuleSqlPart");

        RuleName savedRuleName = new RuleName();
        savedRuleName.setName("RuleName");
        savedRuleName.setId(1);
        savedRuleName.setDescription("RuleDescription");
        savedRuleName.setTemplate("RuleTemplate");
        savedRuleName.setJson("RuleJson");
        savedRuleName.setSqlStr("RuleSqlStr");
        savedRuleName.setSqlPart("RuleSqlPart");

        RuleNameViewDto ruleNameViewDto = new RuleNameViewDto();
        ruleNameViewDto.setName("RuleName");
        ruleNameViewDto.setId(1);
        ruleNameViewDto.setDescription("RuleDescription");
        ruleNameViewDto.setTemplate("RuleTemplate");
        ruleNameViewDto.setJson("RuleJson");
        ruleNameViewDto.setSqlStr("RuleSqlStr");
        ruleNameViewDto.setSqlPart("RuleSqlPart");

        when(ruleNameMapper.toEntity(requestDto)).thenReturn(ruleName);
        when(ruleNameRepository.save(ruleName)).thenReturn(savedRuleName);
        when(ruleNameMapper.toDto(savedRuleName)).thenReturn(ruleNameViewDto);

        RuleNameViewDto result = ruleNameService.addRuleName(requestDto);

        assertEquals(ruleNameViewDto.getTemplate(), result.getTemplate());
        assertEquals(ruleNameViewDto.getDescription(), result.getDescription());
        verify(ruleNameMapper).toEntity(requestDto);
        verify(ruleNameRepository).save(ruleName);
        verify(ruleNameMapper).toDto(savedRuleName);
    }

    @Test
    @DisplayName("Should update RuleName")
    public void updateRuleName() {
        RuleNameRequestDto requestDto = new RuleNameRequestDto();
        requestDto.setName("RuleName");
        requestDto.setDescription("RuleDescription");
        requestDto.setTemplate("RuleTemplate");
        requestDto.setJson("RuleJson");
        requestDto.setSqlStr("RuleSqlStr");
        requestDto.setSqlPart("RuleSqlPart");

        RuleName existingRuleName = new RuleName();
        existingRuleName.setName("Old RuleName");
        existingRuleName.setId(1);
        existingRuleName.setDescription("Old RuleDescription");
        existingRuleName.setTemplate("Old RuleTemplate");
        existingRuleName.setJson("Old RuleJson");
        existingRuleName.setSqlStr("Old RuleSqlStr");
        existingRuleName.setSqlPart("Old RuleSqlPart");

        RuleName savedRuleName = new RuleName();
        savedRuleName.setName("RuleName");
        savedRuleName.setId(1);
        savedRuleName.setDescription("RuleDescription");
        savedRuleName.setTemplate("RuleTemplate");
        savedRuleName.setJson("RuleJson");
        savedRuleName.setSqlStr("RuleSqlStr");
        savedRuleName.setSqlPart("RuleSqlPart");

        RuleNameViewDto ruleNameViewDto = new RuleNameViewDto();
        ruleNameViewDto.setName("RuleName");
        ruleNameViewDto.setId(1);
        ruleNameViewDto.setDescription("RuleDescription");
        ruleNameViewDto.setTemplate("RuleTemplate");
        ruleNameViewDto.setJson("RuleJson");
        ruleNameViewDto.setSqlStr("RuleSqlStr");
        ruleNameViewDto.setSqlPart("RuleSqlPart");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(existingRuleName));
        when(ruleNameRepository.save(existingRuleName)).thenReturn(savedRuleName);
        when(ruleNameMapper.toDto(savedRuleName)).thenReturn(ruleNameViewDto);

        RuleNameViewDto result = ruleNameService.updateRuleName(1, requestDto);

        assertEquals(ruleNameViewDto.getJson(), result.getJson());
        assertEquals(ruleNameViewDto.getSqlPart(), result.getSqlPart());
        verify(ruleNameRepository).findById(1);
        verify(ruleNameRepository).save(existingRuleName);
        verify(ruleNameMapper).toDto(savedRuleName);
    }
    @Test
    @DisplayName("Should throw exception when updating an unknown RuleName")
    public void updateRuleNameNotFoundTest() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuleNameNotFoundException.class, () -> ruleNameService.updateRuleName(1, new RuleNameRequestDto()));

        verify(ruleNameRepository).findById(1);
        verify(ruleNameRepository, never()).save(any(RuleName.class));
    }

    @Test
    @DisplayName("Should delete RuleName")
    public void deleteRuleName() {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        ruleNameService.deleteRuleName(1);

        verify(ruleNameRepository).findById(1);
        verify(ruleNameRepository).deleteById(1);
    }

}

package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dtos.request.CurveRequestDto;
import com.nnk.springboot.dtos.view.CurveViewDto;
import com.nnk.springboot.exceptions.CurvePointNotFoundException;
import com.nnk.springboot.mappers.CurvePointMapper;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.impl.CurveService;
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
public class CurveServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @Mock
    private CurvePointMapper curvePointMapper;

    private ICurveService curveService;

    @BeforeEach
    public void setUp() {
        curveService = new CurveService(curvePointRepository, curvePointMapper);
    }

    @Test
    @DisplayName("Should return all CurvePoints")
    public void getAllCurveTest() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(1.1);
        curvePoint.setValue(10.0);

        CurveViewDto viewDto = new CurveViewDto();
        viewDto.setId(1);
        viewDto.setCurveId(10);
        viewDto.setTerm(1.1);
        viewDto.setValue(10.0);

        when(curvePointRepository.findAll()).thenReturn(List.of(curvePoint));
        when(curvePointMapper.toDto(curvePoint)).thenReturn(viewDto);

        List<CurveViewDto> result = curveService.getAllCurve();

        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getCurveId());
        verify(curvePointRepository).findAll();
        verify(curvePointMapper).toDto(curvePoint);
    }

    @Test
    @DisplayName("Should return CurvePoint by id")
    public void getCurveByIdTest() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);

        CurveViewDto viewDto = new CurveViewDto();
        viewDto.setId(1);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        when(curvePointMapper.toDto(curvePoint)).thenReturn(viewDto);

        CurveViewDto result = curveService.getCurveById(1);

        assertEquals(1, result.getId());
        verify(curvePointRepository).findById(1);
        verify(curvePointMapper).toDto(curvePoint);
    }

    @Test
    @DisplayName("Should throw exception when CurvePoint is not found")
    public void getCurveByIdNotFoundTest() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CurvePointNotFoundException.class, () -> curveService.getCurveById(1));

        verify(curvePointRepository).findById(1);
        verifyNoInteractions(curvePointMapper);
    }

    @Test
    @DisplayName("Should add CurvePoint")
    public void addCurveTest() {
        CurveRequestDto requestDto = new CurveRequestDto();
        requestDto.setCurveId(10);
        requestDto.setTerm(1.1);
        requestDto.setValue(10.0);

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(10);
        curvePoint.setTerm(1.1);
        curvePoint.setValue(10.0);

        CurveViewDto viewDto = new CurveViewDto();
        viewDto.setId(1);
        viewDto.setCurveId(10);
        viewDto.setTerm(1.1);
        viewDto.setValue(10.0);

        when(curvePointMapper.toEntity(requestDto)).thenReturn(curvePoint);
        when(curvePointMapper.toDto(curvePoint)).thenReturn(viewDto);

        CurveViewDto result = curveService.addCurve(requestDto);

        assertEquals(1, result.getId());
        assertEquals(10, result.getCurveId());
        verify(curvePointMapper).toEntity(requestDto);
        verify(curvePointRepository).save(curvePoint);
        verify(curvePointMapper).toDto(curvePoint);
    }

    @Test
    @DisplayName("Should update CurvePoint")
    public void updateCurveTest() {
        CurveRequestDto requestDto = new CurveRequestDto();
        requestDto.setCurveId(20);
        requestDto.setTerm(2.2);
        requestDto.setValue(20.0);

        CurvePoint existingCurve = new CurvePoint();
        existingCurve.setId(1);
        existingCurve.setCurveId(10);
        existingCurve.setTerm(1.1);
        existingCurve.setValue(10.0);

        CurveViewDto viewDto = new CurveViewDto();
        viewDto.setId(1);
        viewDto.setCurveId(20);
        viewDto.setTerm(2.2);
        viewDto.setValue(20.0);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(existingCurve));
        when(curvePointMapper.toDto(existingCurve)).thenReturn(viewDto);

        CurveViewDto result = curveService.updateCurve(1, requestDto);

        assertEquals(20, result.getCurveId());
        assertEquals(20.0, result.getValue());
        assertEquals(20, existingCurve.getCurveId());
        assertEquals(2.2, existingCurve.getTerm());
        assertEquals(20.0, existingCurve.getValue());

        verify(curvePointRepository).findById(1);
        verify(curvePointRepository).save(existingCurve);
        verify(curvePointMapper).toDto(existingCurve);
    }

    @Test
    @DisplayName("Should throw exception when updating an unknown CurvePoint")
    public void updateCurveNotFoundTest() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CurvePointNotFoundException.class, () -> curveService.updateCurve(1, new CurveRequestDto()));

        verify(curvePointRepository).findById(1);
        verify(curvePointRepository, never()).save(any(CurvePoint.class));
    }

    @Test
    @DisplayName("Should delete CurvePoint")
    public void deleteCurveTest() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        curveService.deleteCurve(1);

        verify(curvePointRepository).findById(1);
        verify(curvePointRepository).delete(curvePoint);
    }
}

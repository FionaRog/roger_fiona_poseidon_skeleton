package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dtos.request.CurveRequestDto;
import com.nnk.springboot.dtos.view.CurveViewDto;
import com.nnk.springboot.exceptions.CurvePointNotFoundException;
import com.nnk.springboot.mappers.CurvePointMapper;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.ICurveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for CurvePoint business operations.
 */
@Slf4j
@Service
public class CurveService implements ICurveService {

    private final CurvePointRepository curvePointRepository;

    private final CurvePointMapper curvePointMapper;

    public CurveService(CurvePointRepository curvePointRepository, CurvePointMapper curvePointMapper) {
        this.curvePointRepository = curvePointRepository;
        this.curvePointMapper = curvePointMapper;
    }


    /**
     * Retrieves all CurvePoint records from the repository.
     *
     * @return CurvePoint records formatted for display
     */
    public List<CurveViewDto> getAllCurve() {
        log.info("Getting all curve");

        List<CurvePoint> result = curvePointRepository.findAll();
        log.info("Found {} CurvePoints",result.size());

        return result.stream()
                .map(curvePointMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a CurvePoint by id.
     *
     * @param id the technical identifier of the CurvePoint
     * @return the matching CurvePoint formatted for display
     * @throws CurvePointNotFoundException when no CurvePoint exists for the id
     */
    public CurveViewDto getCurveById(Integer id) {
        log.info("Getting CurvePoint with id {}",id);

        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Curve not found with id {}", id);
                    return new CurvePointNotFoundException(id);
                });

        return curvePointMapper.toDto(curvePoint);
    }

    /**
     * Creates a new CurvePoint from form data.
     *
     * @param curveRequestDto the form data used to create the CurvePoint
     * @return the created CurvePoint formatted for display
     */
    @Transactional
    public CurveViewDto addCurve(CurveRequestDto curveRequestDto) {
        log.info("Adding curve with curveId ={}, term ={}, value ={}",
                curveRequestDto.getCurveId(), curveRequestDto.getTerm(), curveRequestDto.getValue());

        CurvePoint curvePoint = curvePointMapper.toEntity(curveRequestDto);

        curvePointRepository.save(curvePoint);
        log.info("Created Curve with id {}",curvePoint.getId());

        return curvePointMapper.toDto(curvePoint);
    }

    /**
     * Updates an existing CurvePoint with submitted form data.
     *
     * @param id the technical identifier of the CurvePoint to update
     * @param curveRequestDto the form data containing updated values
     * @return the updated CurvePoint formatted for display
     * @throws CurvePointNotFoundException when no CurvePoint exists for the id
     */
    @Transactional
    public CurveViewDto updateCurve(Integer id, CurveRequestDto curveRequestDto) {
        log.info("Updating curve with id {}",id);

        CurvePoint curvePoint = curvePointRepository.findById(id)
               .orElseThrow(() -> {
                   log.warn("Curve not found with id {}", id);
                   return new CurvePointNotFoundException(id);
               });

       curvePoint.setCurveId(curveRequestDto.getCurveId());
       curvePoint.setTerm(curveRequestDto.getTerm());
       curvePoint.setValue(curveRequestDto.getValue());

       curvePointRepository.save(curvePoint);
       log.info("Updated Curve with id {}",curvePoint.getId());

       return curvePointMapper.toDto(curvePoint);
    }

    /**
     * Deletes an existing CurvePoint.
     *
     * @param id the technical identifier of the CurvePoint to delete
     * @throws CurvePointNotFoundException when no CurvePoint exists for the id
     */
    @Transactional
    public void deleteCurve(Integer id) {
       log.info("Deleting curve with id {}",id);

       CurvePoint curvePoint = curvePointRepository.findById(id)
               .orElseThrow(() -> {
                   log.warn("Curve not found with id {}", id);
                   return new CurvePointNotFoundException(id);
               });

       curvePointRepository.delete(curvePoint);

       log.info("Deleted curve with id {}",id);
    }


}

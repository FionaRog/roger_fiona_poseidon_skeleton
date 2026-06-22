package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dtos.request.RatingRequestDto;
import com.nnk.springboot.dtos.view.RatingViewDto;
import com.nnk.springboot.exceptions.RatingNotFoundException;
import com.nnk.springboot.mappers.RatingMapper;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.IRatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for Rating business operations.
 */
@Slf4j
@Service
public class RatingService implements IRatingService {

    private final RatingRepository ratingRepository;

    private final RatingMapper ratingMapper;

    public RatingService(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    /**
     * Retrieves all Rating records from the repository.
     *
     * @return Rating records formatted for display
     */
    public List<RatingViewDto> getAllRatings() {
        log.info("Getting all ratings");

        List<Rating> results = ratingRepository.findAll();
        log.info("Found {} ratings", results.size());

        return results.stream()
                .map(ratingMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a Rating by id.
     *
     * @param id the technical identifier of the Rating
     * @return the matching Rating formatted for display
     * @throws RatingNotFoundException when no Rating exists for the id
     */
    public RatingViewDto getRatingById(Integer id) {
        log.info("Getting rating by ID {}", id);

        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rating not found with id {}", id);
                    return new RatingNotFoundException(id);
                });

        return ratingMapper.toDto(rating);
    }

    /**
     * Creates a new Rating from form data.
     *
     * @param ratingRequestDto the form data used to create the Rating
     * @return the created Rating formatted for display
     */
    @Transactional
    public RatingViewDto addRating(RatingRequestDto ratingRequestDto) {
        log.info("Adding rating ");

        Rating rating = ratingMapper.toEntity(ratingRequestDto);

        ratingRepository.save(rating);
        log.info("Rating added with id {}", rating.getId());

        return ratingMapper.toDto(rating);
    }

    /**
     * Updates an existing Rating with submitted form data.
     *
     * @param id               the technical identifier of the Rating to update
     * @param ratingRequestDto the form data containing updated values
     * @return the updated Rating formatted for display
     * @throws RatingNotFoundException when no Rating exists for the id
     */
    @Transactional
    public RatingViewDto updateRating(Integer id, RatingRequestDto ratingRequestDto) {
        log.info("Updating rating with id {} ", id);

        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rating not found with id {}", id);
                    return new RatingNotFoundException(id);
                });

        rating.setFitchRating(ratingRequestDto.getFitchRating());
        rating.setMoodysRating(ratingRequestDto.getMoodysRating());
        rating.setSandPRating(ratingRequestDto.getSandPRating());
        rating.setOrderNumber(ratingRequestDto.getOrderNumber());

        ratingRepository.save(rating);

        log.info("Rating updated with id {}", id);
        return ratingMapper.toDto(rating);
    }

    /**
     * Deletes an existing Rating.
     *
     * @param id the technical identifier of the Rating to delete
     * @throws RatingNotFoundException when no Rating exists for the id
     */
    @Transactional
    public void deleteRating(Integer id) {
        log.info("Deleting rating with id {}", id);

        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rating not found with id {}", id);
                    return new RatingNotFoundException(id);
                });

        ratingRepository.delete(rating);
        log.info("Rating Deleted with id {}", id);
    }


}

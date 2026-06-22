package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dtos.request.RatingRequestDto;
import com.nnk.springboot.dtos.view.RatingViewDto;
import com.nnk.springboot.exceptions.RatingNotFoundException;
import com.nnk.springboot.mappers.RatingMapper;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.impl.RatingService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RatingMapper ratingMapper;

    private IRatingService ratingService;

    @BeforeEach
    public void init() {
        ratingService = new RatingService(ratingRepository, ratingMapper);
    }

    @Test
    @DisplayName("Should return all Ratings")
    public void getRatingTest() {
        Rating rating = new Rating();
        rating.setMoodysRating("MoodysRating");
        rating.setSandPRating("SandPrating");
        rating.setFitchRating("FitchRating");
        rating.setOrderNumber(1);

        RatingViewDto viewDto = new RatingViewDto();
        viewDto.setFitchRating("FitchRating");
        viewDto.setMoodysRating("MoodysRating");
        viewDto.setSandPRating("SandPrating");
        viewDto.setOrderNumber(1);

        when(ratingRepository.findAll()).thenReturn(List.of(rating));
        when(ratingMapper.toDto(rating)).thenReturn(viewDto);

        List<RatingViewDto> result = ratingService.getAllRatings();

        assertEquals(1, result.size());
        assertEquals("MoodysRating", result.get(0).getMoodysRating());
        verify(ratingRepository).findAll();
        verify(ratingMapper).toDto(rating);
    }

    @Test
    @DisplayName("Should return Rating by id")
    public void getRatingByIdTest() {
        Rating rating = new Rating();
        rating.setId(1);

        RatingViewDto viewDto = new RatingViewDto();
        viewDto.setId(1);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        when(ratingMapper.toDto(rating)).thenReturn(viewDto);

        RatingViewDto result = ratingService.getRatingById(1);

        assertEquals(1, result.getId());
        verify(ratingRepository).findById(1);
        verify(ratingMapper).toDto(rating);
    }

    @Test
    @DisplayName("Should throw exception when Rating is not found")
    public void getRatingNotFoundTest() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RatingNotFoundException.class, () -> ratingService.getRatingById(1));

        verify(ratingRepository).findById(1);
        verifyNoInteractions(ratingMapper);
    }

    @Test
    @DisplayName("Should add Rating")
    public void addRatingTest() {
        RatingRequestDto requestDto = new RatingRequestDto();
        requestDto.setMoodysRating("MoodysRating");
        requestDto.setSandPRating("SandPrating");
        requestDto.setFitchRating("FitchRating");
        requestDto.setOrderNumber(1);

        Rating rating = new Rating();
        rating.setMoodysRating("MoodysRating");
        rating.setSandPRating("SandPrating");
        rating.setFitchRating("FitchRating");
        rating.setOrderNumber(1);
        rating.setId(1);

        Rating savedRating = new Rating();
        savedRating.setMoodysRating("MoodysRating");
        savedRating.setSandPRating("SandPrating");
        savedRating.setFitchRating("FitchRating");
        savedRating.setOrderNumber(1);
        savedRating.setId(1);

        RatingViewDto viewDto = new RatingViewDto();
        viewDto.setId(1);
        viewDto.setFitchRating("FitchRating");
        viewDto.setMoodysRating("MoodysRating");
        viewDto.setSandPRating("SandPrating");
        viewDto.setOrderNumber(1);

        when(ratingMapper.toEntity(requestDto)).thenReturn(rating);
        when(ratingRepository.save(rating)).thenReturn(savedRating);
        when(ratingMapper.toDto(rating)).thenReturn(viewDto);

        RatingViewDto result = ratingService.addRating(requestDto);

        assertEquals(1, result.getId());
        assertEquals("FitchRating", result.getFitchRating());
        verify(ratingMapper).toEntity(requestDto);
        verify(ratingRepository).save(rating);
        verify(ratingMapper).toDto(rating);
    }

    @Test
    @DisplayName("Should update Rating")
    public void updateRatingTest() {
        RatingRequestDto requestDto = new RatingRequestDto();
        requestDto.setMoodysRating("New MoodysRating");
        requestDto.setSandPRating("New SandPrating");
        requestDto.setFitchRating("New FitchRating");
        requestDto.setOrderNumber(2);

        Rating oldRating = new Rating();
        oldRating.setMoodysRating("Old MoodysRating");
        oldRating.setSandPRating("Old SandPrating");
        oldRating.setFitchRating("Old FitchRating");
        oldRating.setOrderNumber(1);
        oldRating.setId(1);

        RatingViewDto viewDto = new RatingViewDto();
        viewDto.setId(1);
        viewDto.setSandPRating("New SandPrating");
        viewDto.setFitchRating("New FitchRating");
        viewDto.setMoodysRating("New MoodysRating");
        viewDto.setOrderNumber(2);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(oldRating));
        when(ratingMapper.toDto(oldRating)).thenReturn(viewDto);

        RatingViewDto result = ratingService.updateRating(1, requestDto);

        assertEquals(2, result.getOrderNumber());
        assertEquals("New SandPrating", result.getSandPRating());

        verify(ratingRepository).findById(1);
        verify(ratingRepository).save(oldRating);
        verify(ratingMapper).toDto(oldRating);
    }

    @Test
    @DisplayName("Should throw exception when updating an unknown Rating")
    public void updateRatingNotFoundTest() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RatingNotFoundException.class, () -> ratingService.updateRating(1, new RatingRequestDto()));

        verify(ratingRepository).findById(1);
        verify(ratingRepository, never()).save(any(Rating.class));
    }

    @Test
    @DisplayName("Should delete Rating")
    public void deleteRatingTest() {
        Rating rating = new Rating();
        rating.setId(1);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        ratingService.deleteRating(1);

        verify(ratingRepository).findById(1);
        verify(ratingRepository).delete(rating);
    }
}

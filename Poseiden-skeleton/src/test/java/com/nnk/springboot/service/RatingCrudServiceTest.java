package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.Impl.RatingCrudServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class RatingCrudServiceTest {
    @Mock
    RatingRepository ratingRepository;

    @InjectMocks
    RatingCrudServiceImpl ratingCrudService;

    @Test
    @DisplayName("ratingService calls ratingRepository create method")
    public void given_whenCreateRating_thenReturnRating() {
        //GIVEN
        Rating rating = new Rating("moodyRating","sandPRating","fitchRating",1);
        //WHEN
        ratingCrudService.create(rating);
        //THEN
        ArgumentCaptor<Rating> ratingArgumentCaptor = ArgumentCaptor.forClass(Rating.class);
        verify(ratingRepository, times(1)).save(ratingArgumentCaptor.capture());

        assertThat(ratingArgumentCaptor.getValue())
                .isNotNull()
                .satisfies(arg -> {
                    assertThat(arg.getId()).isEqualTo(rating.getId());
                });

    }
}

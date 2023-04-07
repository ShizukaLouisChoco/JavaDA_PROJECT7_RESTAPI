package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Qualifier("RatingCrudServiceImpl")
public class RatingCrudServiceImpl extends AbstractCrudService<Rating> {

    public RatingCrudServiceImpl(RatingRepository repository){
        super(repository);
    }

    @Override
    public Rating create(Rating entity){
        Rating newRating = new Rating(entity);
        return super.create(newRating);
    }

}

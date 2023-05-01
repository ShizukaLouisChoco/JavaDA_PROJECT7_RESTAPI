package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.NoResourceException;
import com.nnk.springboot.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
public class RatingController {

    private final CrudService<Rating> ratingCrudService;

    public RatingController(@Qualifier("RatingCrudServiceImpl") CrudService<Rating> ratingCrudService) {
        this.ratingCrudService = ratingCrudService;
    }


    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratingList", ratingCrudService.getAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating,Model model) {
        model.addAttribute("ratingForm", new Rating());
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "rating/add";
        }
            ratingCrudService.create(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("rating", Optional.of(ratingCrudService.getById(id)).orElseThrow(()->new NoResourceException(id)));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        model.addAttribute("rating",rating);
        if(result.hasErrors()){
            return "rating/update";
        }
        try{
            ratingCrudService.update(rating);
        }catch(Exception exception){
            model.addAttribute("rating",rating);
            log.error(String.valueOf(exception));
            model.addAttribute("errorMsg" , exception.getMessage());
            return "rating/update";

        }
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingCrudService.delete(id);
        return "redirect:/rating/list";
    }
}

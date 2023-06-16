package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
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
public class BidListController {


    private final CrudService<BidList> bidListCrudService;

    public BidListController(@Qualifier("bidListCrudService") CrudService<BidList> bidListCrudService ) {
        this.bidListCrudService = bidListCrudService;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        log.info("heading to /bidList/list");
        model.addAttribute("bidList", bidListCrudService.getAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm( Model model) {
        log.info("heading to /bidList/add");
        model.addAttribute("bidList", new BidList( ));
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result,Model model) {
        log.info("heading to /bidList/validate");
        model.addAttribute("bidList",bidList);
         if (result.hasErrors()) {
        log.info("validation error !");
            return "bidList/add";
        }
        bidListCrudService.create(bidList);
        log.info("new bid list is created !");
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("heading to /bidList/update/{id}");
        model.addAttribute("bidList", Optional.of(bidListCrudService.getById(id)).orElseThrow(()->new NoResourceException(id)));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        log.info("heading to /bidList/update/{id} postmapping");
            model.addAttribute("bidList",bidList);
        if(result.hasErrors()){
        log.info("validation error !");
            return "bidList/update";
        }
            bidListCrudService.update(bidList);
        log.info("bid list is updated");
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListCrudService.delete(id);
        log.info("bid list is deleted");
        return "redirect:/bidList/list";
    }
}

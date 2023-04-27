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

    //Bid 2.3
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view
        //Id, Account, Type, Bid Quantity, Action(Edit/Delete), Add New, Logout
        model.addAttribute("bidlist", crudService.getAll());
        return "bidList/list";
    }

    //Bid 2.1
    @GetMapping("/bidList/add")
    public String addBidForm( Model model) {
        model.addAttribute("bidList", new BidList( ));
        return "bidList/add";
    }

    //Bid 2.2
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result,Model model) {
        model.addAttribute("bidList",bidList);
         if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListCrudService.create(bidList);
        return "redirect:/bidList/list";
    }

    //Bid 2.4
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        //Account, Type, Bid quantity,cancel, updateBidList
        // TODO: get Bid by Id and to model then show to the form
        model.addAttribute("bidList", Optional.of(crudService.getById(id)).orElseThrow(()->new NoResourceException(id)));
        return "bidList/update";
    }

    //Bid 2.4
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
            model.addAttribute("bidListForm",bidList);
        if(result.hasErrors()){
            return "/bidList/update/{id}";
        }
        try{
            crudService.update(bidList);
        }catch(Exception ex){
            model.addAttribute("bidListForm",bidList);
            return "bidList/update/{id}";

        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        crudService.delete(id);
        return "redirect:/bidList/list";
    }
}

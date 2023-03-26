package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidFormDto;
import com.nnk.springboot.exception.BidListNotFoundException;
import com.nnk.springboot.service.BidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Slf4j
@Controller
public class BidListController {
    private final BidListService bidListService;

    public BidListController(BidListService bitListService) {
        this.bidListService = bitListService;
    }

    //Bid 2.3
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view
        //Id, Account, Type, Bid Quantity, Action(Edit/Delete), Add New, Logout
        model.addAttribute("bidlist", bidListService.findList());
        return "bidList/list";
    }

    //Bid 2.1
    @GetMapping("/bidList/add")
    public String addBidForm(BidFormDto bid,Model model) {
        model.addAttribute("bidListForm",new BidList(bid.getAccount(),bid.getType(),bid.getBidQuantity()));
        return "bidList/add";
    }

    //Bid 2.2
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidFormDto bid, BindingResult result, Model model) {
        //Account, Type, Bidquantity
        // TODO: check data valid and save to db, after saving return bid list
        if(result.hasErrors()){
        return "bidList/add";
        }
        try{
            bidListService.createBid(bid);
        }catch(Exception ex){
            model.addAttribute("bidListForm",bid);
        return "bidList/add";
        }
        return "redirect:/bidList/list";
    }

    //Bid 2.4
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        //Account, Type, Bid quantity,cancel, updateBidList
        // TODO: get Bid by Id and to model then show to the form
        model.addAttribute("bidList", bidListService.findBidById(id).orElseThrow(()->new BidListNotFoundException("There is no bid with this id : " + id)));
        return "bidList/update";
    }

    //Bid 2.4
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidFormDto bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
            model.addAttribute("bidListForm",bidList);
        if(result.hasErrors()){
            return "/bidList/update/{id}";
        }
        try{
            bidListService.updateBid(bidList,id);
        }catch(Exception ex){
            model.addAttribute("bidListForm",bidList);
            return "bidList/update/{id}";

        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBid(id);
        return "redirect:/bidList/list";
    }
}

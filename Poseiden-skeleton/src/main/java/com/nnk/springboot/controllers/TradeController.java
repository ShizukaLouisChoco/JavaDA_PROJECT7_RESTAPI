package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
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
public class TradeController {


    private final CrudService<Trade> tradeCrudService;

    public TradeController(@Qualifier("TradeCrudServiceImpl") CrudService<Trade> tradeCrudService) {
        this.tradeCrudService = tradeCrudService;
    }
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        // TODO: find all Trade, add to model
        model.addAttribute("tradeList", crudService.getAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade( Model model) {
        model.addAttribute("trade", new Trade( ));
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        model.addAttribute("trade",trade);
        if(result.hasErrors()){
            return "trade/add";
        }
        tradeCrudService.create(trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form
        model.addAttribute("trade", Optional.of(crudService.getById(id)).orElseThrow(()->new NoResourceException(id)));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list
        model.addAttribute("trade",trade);
        if(result.hasErrors()){
            return "/trade/update/{id}";
        }
        try{
            crudService.update(trade);
        }catch(Exception ex){
            model.addAttribute("trade",trade);
            return "trade/update/{id}";

        }
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        crudService.delete(id);
        return "redirect:/trade/list";
    }
}

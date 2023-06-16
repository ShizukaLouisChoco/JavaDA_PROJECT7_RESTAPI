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
        log.info("heading to /trade/list");
        model.addAttribute("tradeList", tradeCrudService.getAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade( Model model) {
        log.info("heading to /trade/add");
        model.addAttribute("trade", new Trade( ));
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        log.info("heading to /trade/validate");
        model.addAttribute("trade",trade);
        if(result.hasErrors()){
        log.info("validation error !");
            return "trade/add";
        }
        tradeCrudService.create(trade);
        log.info("new trade is created !");
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("heading to /trade/update/{id}");
        model.addAttribute("trade", Optional.of(tradeCrudService.getById(id)).orElseThrow(()->new NoResourceException(id)));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        log.info("heading to /trade/update/{id} postmapping");
        model.addAttribute("trade",trade);
        if(result.hasErrors()){
        log.info("validation error !");
            return "trade/update";
        }
        try{
        log.info("updating trade...");
            tradeCrudService.update(trade);
        }catch(Exception exception){
            model.addAttribute("trade",trade);
            log.error(String.valueOf(exception));
            model.addAttribute("errorMsg" , exception.getMessage());
            return "trade/update";

        }
        log.info("trade is updated");
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        tradeCrudService.delete(id);
        log.info("trade is deleted");
        return "redirect:/trade/list";
    }
}

package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
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
public class RuleNameController {

    private final CrudService<RuleName> ruleNameCrudService;

    public RuleNameController(@Qualifier("RuleNameCrudServiceImpl") CrudService<RuleName> ruleNameCrudService) {
        this.ruleNameCrudService = ruleNameCrudService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        log.info("heading to /ruleName/list");
        model.addAttribute("ruleNameList", ruleNameCrudService.getAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(  Model model) {
        log.info("heading to /ruleName/add");
        model.addAttribute("ruleName",new RuleName());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        log.info("heading to /ruleName/validate");
        model.addAttribute("ruleName",ruleName);
        if(result.hasErrors()){
        log.info("validation error !");
            return "ruleName/add";
        }
        ruleNameCrudService.create(ruleName);
        log.info("new ruleName is created !");
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("heading to /ruleName/update/{id}");
        model.addAttribute("ruleName", Optional.of(ruleNameCrudService.getById(id)).orElseThrow(()->new NoResourceException( id)));
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        log.info("heading to /ruleName/update/{id} postmapping");
        model.addAttribute("ruleName",ruleName);
        if(result.hasErrors()){
        log.info("validation error !");
            return "ruleName/update";
        }
        try{
            ruleNameCrudService.update(ruleName);
        log.info("updating ruleName");
        }catch(Exception exception){
            model.addAttribute("ruleName",ruleName);
            log.error(String.valueOf(exception));
            model.addAttribute("errorMsg" , exception.getMessage());
            return "ruleName/update";

        }
        log.info("ruleName is updated");
        return "redirect:/ruleName/list";
    }
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        ruleNameCrudService.delete(id);
        log.info("ruleName is deleted");
        return "redirect:/ruleName/list";
    }
}

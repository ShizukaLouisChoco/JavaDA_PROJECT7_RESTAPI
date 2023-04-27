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
        model.addAttribute("ruleNameList", ruleNameCrudService.getAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(  Model model) {
        model.addAttribute("ruleName",new RuleName());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        model.addAttribute("ruleName",ruleName);
        if(result.hasErrors()){
            return "ruleName/add";
        }
        ruleNameCrudService.create(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        model.addAttribute("ruleName", Optional.of(crudService.getById(id)).orElseThrow(()->new NoResourceException( id)));
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        model.addAttribute("ruleName",ruleName);
        if(result.hasErrors()){
            return "/ruleName/update/{id}";
        }
        try{
            crudService.update(ruleName);
        }catch(Exception ex){
            model.addAttribute("ruleName",ruleName);
            return "ruleName/update/{id}";

        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        crudService.delete(id);
        return "redirect:/ruleName/list";
    }
}

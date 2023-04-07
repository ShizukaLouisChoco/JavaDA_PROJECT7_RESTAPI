package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
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
public class UserController {

    private final CrudService crudService;

    public UserController(@Qualifier("UserCrudServiceImpl") CrudService crudService) {
        this.crudService = crudService;
    }


    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("userList", crudService.getAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user,Model model) {
        model.addAttribute("user",new User());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        //validation error
        if(result.hasErrors()){
            return "user/add";
        }
        //exception handling
        try {
            crudService.create(user);
        }catch(Exception exception){
            log.error(String.valueOf(exception));
            model.addAttribute("errorMsg" , exception.getMessage());
            return "user/add";
        }

        return "redirect:/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", Optional.of(crudService.getById(id)).orElseThrow(() -> new NoResourceException(id)));
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        model.addAttribute("user",user);
        if (result.hasErrors()) {
            return "user/update/{id}";
        }try {
            crudService.update(user);
        }catch(Exception exception){
            model.addAttribute("user",user);
            log.error(String.valueOf(exception));
            model.addAttribute("errorMsg" , exception.getMessage());
            return "user/update/{id}";
        }
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        crudService.delete(id);
        // model.addAttribute("users", crudService.getAll());
        return "redirect:/user/list";
    }
}

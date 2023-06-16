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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class UserController {


    private final CrudService<User> UserCrudService;

    public UserController(@Qualifier("UserCrudServiceImpl") CrudService<User> UserCrudService) {
        this.UserCrudService = UserCrudService;
    }


    @RequestMapping("/user/list")
    public String home(Model model)
    {
        log.info("heading to /user/list");
        model.addAttribute("userList", UserCrudService.getAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser( Model model) {
        log.info("heading to /user/add");
        model.addAttribute("user",new User( ));
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult bindingResult,Model model) {
        log.info("heading to /user/validate");
        model.addAttribute("user",user);
        if(bindingResult.hasErrors()){
        log.info("validation error !");
            List<String> result = new ArrayList<>(Arrays.asList(bindingResult.toString().split("\\s*,\\s*")));
            model.addAttribute("result",result);
            return "user/add";
        }try{
        log.info("creating new user");
            UserCrudService.create(user);
        }catch(Exception exception){
            model.addAttribute("user",user);
            log.error(String.valueOf(exception));
            model.addAttribute("errorMsg" , exception.getMessage());
            return "user/add";
        }
        log.info("new user is created !");
        return "redirect:/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("heading to /user/update/{id}");
        model.addAttribute("user", Optional.of(UserCrudService.getById(id)).orElseThrow(() -> new NoResourceException(id)));
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        log.info("heading to /user/update/{id} postmapping");
        model.addAttribute("user",user);
        if (result.hasErrors()) {
        log.info("validation error !");
            return "user/update";
        }try {
        log.info("updating user");
            UserCrudService.update(user);
        }catch(Exception exception){
            model.addAttribute("user",user);
            log.error(String.valueOf(exception));
            model.addAttribute("errorMsg" , exception.getMessage());
            return "user/update";
        }
        log.info("user is updated");
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        UserCrudService.delete(id);
        log.info("user is deleted");
        return "redirect:/user/list";
    }
}

package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
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
public class CurveController {

    private final CrudService<CurvePoint> curveCrudService;

    public CurveController(@Qualifier("CurveCrudServiceImpl") CrudService<CurvePoint> curveCrudService) {
        this.curveCrudService = curveCrudService;
    }

    //Curve 3.3
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePointList", curveCrudService.getAll());
        return "curvePoint/list";
    }

    //Curve 3.1
    @GetMapping("/curvePoint/add")
    public String addCurveForm(  Model model) {
        model.addAttribute("curvePoint",new CurvePoint( ));
        return "curvePoint/add";
    }

    //Curve 3.2
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curve, BindingResult result,Model model) {
        model.addAttribute("curvePoint",curve);
        if(result.hasErrors()){
            return "curvePoint/add";
        }
        curveCrudService.create(curve);
        return "redirect:/curvePoint/list";
    }

    //Curve 3.4
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("curve", Optional.of(curveCrudService.getById(id)).orElseThrow(()->new NoResourceException(id)));
        return "curvePoint/update";
    }

    //Curve 3.4
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curve,
                             BindingResult result, Model model) {
        model.addAttribute("curve",curve);
        if(result.hasErrors()){
            return "curvePoint/update" ;
        }
        try{
            curveCrudService.update(curve);
        }catch(Exception exception){
            model.addAttribute("curve",curve);
            log.error(String.valueOf(exception));
            model.addAttribute("errorMsg" , exception.getMessage());
            return "curvePoint/update";

        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        crudService.delete(id);
        return "redirect:/curvePoint/list";
    }
}

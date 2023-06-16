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

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        log.info("heading to /curvePoint/list");
        model.addAttribute("curveList", curveCrudService.getAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(  Model model) {
        log.info("heading to /curvePoint/add");
        model.addAttribute("curvePoint",new CurvePoint( ));
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curve, BindingResult result,Model model) {
        log.info("heading to /curvePoint/validate");
        model.addAttribute("curvePoint",curve);
        if(result.hasErrors()){
            log.info("validation error !");
            return "curvePoint/add";
        }
        curveCrudService.create(curve);
        log.info("new curve point is created !");

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("heading to /curvePoint/update/{id}");
        model.addAttribute("curveList", Optional.of(curveCrudService.getById(id)).orElseThrow(()->new NoResourceException(id)));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curve,
                             BindingResult result, Model model) {
        log.info("heading to /curvePoint/update/{id} postmapping");
        model.addAttribute("curveList",curve);
        if(result.hasErrors()){
            log.info("validation error !");
            return "curvePoint/update";
        }
            curveCrudService.update(curve);
            log.info("curve point is updated");

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {
        curveCrudService.delete(id);
        log.info("curve point is deleted");
        return "redirect:/curvePoint/list";
    }
}

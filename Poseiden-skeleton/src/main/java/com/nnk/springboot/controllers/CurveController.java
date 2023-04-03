package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurveFormDto;
import com.nnk.springboot.exception.BidListNotFoundException;
import com.nnk.springboot.exception.CurvePointNotFoundException;
import com.nnk.springboot.service.CurveService;
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
public class CurveController {
    private final CurveService curveService;

    public CurveController(CurveService curveService) {
        this.curveService = curveService;
    }

    //Curve 3.3
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        // TODO: find all Curve Point, add to model
        model.addAttribute("curvelist", curveService.findList());
        return "curvePoint/list";
    }

    //Curve 3.1
    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurveFormDto curve, Model model) {
        //curveId, Term, Value
        model.addAttribute("curveListForm",new CurveFormDto(curve.getCurveId(),curve.getTerm(),curve.getValue()));
        return "curvePoint/add";
    }

    //Curve 3.2
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurveFormDto curveFormDto, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        if(result.hasErrors()){
            return "curvePoint/add";
        }
        try{
            curveService.createCurve(curveFormDto);
        }catch(Exception ex){
            model.addAttribute("curveListForm",curveFormDto);
            return "curvePoint/add";
        }
        return "redirect:/curvePoint/list";
    }

    //Curve 3.4
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        model.addAttribute("curveList", curveService.findCurveById(id).orElseThrow(()->new CurvePointNotFoundException("There is no curve point with this id : " + id)));
        return "curvePoint/update";
    }

    //Curve 3.4
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurveFormDto curveFormDto,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        model.addAttribute("curveList",curveFormDto);
        if(result.hasErrors()){
            return "/curvePoint/update/{id}";
        }
        try{
            curveService.updateCurve(curveFormDto,id);
        }catch(Exception ex){
            model.addAttribute("curveList",curveFormDto);
            return "curvePoint/update/{id}";

        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        curveService.deleteCurve(id);
        return "redirect:/curvePoint/list";
    }
}

package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.BidFormDto;
import com.nnk.springboot.dto.CurveFormDto;

import java.util.List;
import java.util.Optional;

public interface CurveService {
    List<CurvePoint> findList();

    CurvePoint createCurve(CurveFormDto curve);

    Optional<CurvePoint> findCurveById(Integer id);

    CurvePoint updateCurve(CurveFormDto curveList, Integer id);

    void deleteCurve(Integer id);
}

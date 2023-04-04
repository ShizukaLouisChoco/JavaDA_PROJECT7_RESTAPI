package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurveFormDto;
import com.nnk.springboot.exception.CurvePointAlreadyExistException;
import com.nnk.springboot.exception.CurvePointNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.ConnectedUserDetailsService;
import com.nnk.springboot.service.CurveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurveServiceImpl implements CurveService {

    private final ConnectedUserDetailsService connectedUserDetailsService;
    private final CurvePointRepository curvePointRepository;

    @Override
    public List<CurvePoint> findList() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint createCurve(CurveFormDto curve) {
        //verification if the curvePointId is correct
        Optional<CurvePoint> curvePointExists  = curvePointRepository.findByCurveId(curve.getCurveId());
        if(curvePointExists.isPresent()){
            throw new CurvePointAlreadyExistException("CurvePoint id already exist");
        }
        //verification
        Timestamp creationDate = new Timestamp(System.currentTimeMillis());
        CurvePoint newCurvePoint = new CurvePoint(curve.getCurveId(),curve.getTerm(), curve.getValue(),creationDate);
        curvePointRepository.save(newCurvePoint);
        return newCurvePoint;
    }

    @Override
    public Optional<CurvePoint> findCurveById(Integer id) {
        return curvePointRepository.findById(id);
    }

    @Override
    public CurvePoint updateCurve(CurveFormDto curveList, Integer id) {
        CurvePoint curveToUpdate = findCurveById(id).orElseThrow(()->new CurvePointNotFoundException("Invalid CurvePoint id : "+id));
        Timestamp asOfDate = new Timestamp(System.currentTimeMillis());
        curveToUpdate.setTerm(curveList.getTerm());
        curveToUpdate.setValue(curveList.getValue());
        curveToUpdate.setAsOfDate(asOfDate);
        return curvePointRepository.save(curveToUpdate);
    }

    @Override
    public void deleteCurve(Integer id) {
        CurvePoint curvePoint = findCurveById(id).orElseThrow(()->new CurvePointNotFoundException("Invalid CurvePoint id : " +id));
        curvePointRepository.delete(curvePoint);
    }
}

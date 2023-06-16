package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("CurveCrudServiceImpl")
public class CurveCrudServiceImpl extends AbstractCrudService<CurvePoint,CurvePointRepository>{
    public CurveCrudServiceImpl(CurvePointRepository repository){
        super(repository);
    }

    @Override
    public CurvePoint create(CurvePoint entity){
        log.info("creating curve point in CurveCrudServiceImpl");
        CurvePoint newCurvePoint = new CurvePoint(entity);
        return super.create(newCurvePoint);
    }


}

package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("bidListCrudService")
public class BidListCrudServiceImpl extends AbstractCrudService<BidList,BidListRepository>  {


    @Autowired
    public BidListCrudServiceImpl(BidListRepository repository){
        super(repository);
    }


    @Override
    public BidList create(BidList entity ){
        BidList newBidList = new BidList(entity);
        return super.create(newBidList);
    }



}

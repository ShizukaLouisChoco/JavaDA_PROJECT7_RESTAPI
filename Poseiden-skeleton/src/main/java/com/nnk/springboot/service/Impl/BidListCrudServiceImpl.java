package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class BidListCrudServiceImpl extends AbstractCrudService<BidList>   {


    public BidListCrudServiceImpl(BidListRepository repository){
        super(repository);
    }


    @Override
    public BidList create(BidList entity ){
        BidList newBidList = new BidList(entity);
        return super.create(newBidList);
    }


}

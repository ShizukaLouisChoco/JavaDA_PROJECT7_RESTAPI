package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Qualifier("BidListCrudServiceImpl")
public class BidListCrudServiceImpl extends AbstractCrudService<BidList>  {


    @Autowired
    public BidListCrudServiceImpl(BidListRepository repository){
        super(repository);
    }


    @Override
    public BidList create(BidList entity ){
        BidList newBidList = new BidList(entity);
        return super.create(newBidList);
    }



    /*
    @Override
    public List<BidList> findList() {
        return null;
    }

    @Override
    public BidList createBid(BidFormDto bid) {
        return null;
    }

    @Override
    public Optional<BidList> findBidById(Integer id) {
        return Optional.empty();
    }

    @Override
    public BidList updateBid(BidFormDto bidList, Integer id) {
        return null;
    }

    @Override
    public void deleteBid(Integer id) {

    }*/
}

package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("TradeCrudServiceImpl")
public class TradeCrudServiceImpl  extends AbstractCrudService<Trade,TradeRepository> {
    public TradeCrudServiceImpl(TradeRepository repository){
        super(repository);
    }

    @Override
    public Trade create(Trade entity){
        log.info("creating trade in TradeCrudServiceImpl");
        Trade newTrade = new Trade(entity);
        return super.create(newTrade);
    }

}

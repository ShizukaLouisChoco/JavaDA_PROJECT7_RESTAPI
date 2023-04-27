package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("RuleNameCrudServiceImpl")
public class RuleNameCrudServiceImpl extends AbstractCrudService<RuleName,RuleNameRepository> {

    public RuleNameCrudServiceImpl(RuleNameRepository repository){
        super(repository);
    }

    @Override
    public RuleName create(RuleName entity){
        RuleName newRuleName = new RuleName(entity);
        return super.create(newRuleName);
    }

}

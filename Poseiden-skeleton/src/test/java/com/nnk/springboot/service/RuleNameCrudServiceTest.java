package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.Impl.RuleNameCrudServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class RuleNameCrudServiceTest {
    @Mock
    RuleNameRepository ruleNameRepository;

    @InjectMocks
    RuleNameCrudServiceImpl ruleNameCrudService;

    @Test
    @DisplayName("ruleNameService calls ruleNameRepository create method")
    public void given_whenCreateRuleName_thenReturnRuleName() {
        //GIVEN
        RuleName ruleName = new RuleName("name","description","json","template","sqlStr","sqlPart");
        //WHEN
        ruleNameCrudService.create(ruleName);
        //THEN
        ArgumentCaptor<RuleName> ruleNameArgumentCaptor = ArgumentCaptor.forClass(RuleName.class);
        verify(ruleNameRepository, times(1)).save(ruleNameArgumentCaptor.capture());

        assertThat(ruleNameArgumentCaptor.getValue())
                .isNotNull()
                .satisfies(arg -> {
                    assertThat(arg.getId()).isEqualTo(ruleName.getId());
                });

    }
}

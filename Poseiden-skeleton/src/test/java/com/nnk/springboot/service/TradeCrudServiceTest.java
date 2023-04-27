package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.Impl.TradeCrudServiceImpl;
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
public class TradeCrudServiceTest {
    @Mock
    TradeRepository tradeRepository;

    @InjectMocks
    TradeCrudServiceImpl tradeCrudService;

    @Test
    @DisplayName("tradeCrudService calls tradeRepository create method")
    public void given_whenCreateTrade_thenReturnTrade() {
        //GIVEN
        Trade trade = new Trade("account","type",1.);
        //WHEN
        tradeCrudService.create(trade);
        //THEN
        ArgumentCaptor<Trade> tradeArgumentCaptor = ArgumentCaptor.forClass(Trade.class);
        verify(tradeRepository, times(1)).save(tradeArgumentCaptor.capture());

        assertThat(tradeArgumentCaptor.getValue())
                .isNotNull()
                .satisfies(arg -> {
                    assertThat(arg.getId()).isEqualTo(trade.getId());
                });

    }
}

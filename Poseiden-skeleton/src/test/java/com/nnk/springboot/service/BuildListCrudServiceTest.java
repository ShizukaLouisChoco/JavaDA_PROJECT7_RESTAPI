package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.Impl.BidListCrudServiceImpl;
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
public class BuildListCrudServiceTest {

    @Mock
    BidListRepository bidListRepository;

    @InjectMocks
    BidListCrudServiceImpl bidListCrudService;

    @Test
    @DisplayName("bidListService calls bidListRepository create method")
    public void given_whenCreateBidList_thenReturnBidList() {
        //GIVEN
        BidList bidList = new BidList("account","type",1.);
        //WHEN
        bidListCrudService.create(bidList);
        //THEN
        ArgumentCaptor<BidList> bidListArgumentCaptor = ArgumentCaptor.forClass(BidList.class);
        verify(bidListRepository, times(1)).save(bidListArgumentCaptor.capture());

        assertThat(bidListArgumentCaptor.getValue())
                .isNotNull()
                .satisfies(arg -> {
                    assertThat(arg.getId()).isEqualTo(bidList.getId());
                });

    }
}
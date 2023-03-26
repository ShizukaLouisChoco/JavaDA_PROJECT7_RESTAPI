package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidFormDto;

import java.util.List;
import java.util.Optional;

public interface BidListService {
    List<BidList> findList();

    BidList createBid(BidFormDto bid);

    Optional<BidList> findBidById(Integer id);

    BidList updateBid(BidFormDto bidList,Integer id);

    void deleteBid(Integer id);
}

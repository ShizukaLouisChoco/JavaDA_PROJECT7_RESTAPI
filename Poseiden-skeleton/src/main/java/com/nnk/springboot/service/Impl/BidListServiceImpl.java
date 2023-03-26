package com.nnk.springboot.service.Impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.BidFormDto;
import com.nnk.springboot.exception.BidListNotFoundException;
import com.nnk.springboot.exception.UserNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.service.ConnectedUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class BidListServiceImpl implements BidListService {

    private final ConnectedUserDetailsService connectedUserDetailsService;
    private final BidListRepository bidListRepository;

    private final UserRepository userRepository;

    @Override
    public List<BidList> findList() {
        User connectedUser = connectedUserDetailsService.getConnectedUser();
        return bidListRepository.findAllByAccount(connectedUser.getUsername());
    }

    @Transactional
    @Override
    public BidList createBid(BidFormDto bid) {
        //verification if the account id is correct
        Optional<User> accountExists  = userRepository.findByUsername(bid.getAccount());
        if(!accountExists.isPresent()){
            throw new UserNotFoundException("Account doesn't exist");
        }
        //verication
        Timestamp creationDate = new Timestamp(System.currentTimeMillis());
        Timestamp nullTimeStamp = null;
        Timestamp nullTimeStamp2 = null;
        BidList newBid = new BidList(null,bid.getAccount(),bid.getType(),bid.getBidQuantity(),null,null,null,null,nullTimeStamp,null,null,null,null,null,null,creationDate,null,nullTimeStamp2,null,null,null,null);
        bidListRepository.save(newBid);
        return newBid;
    }



    @Override
    public Optional<BidList> findBidById(Integer id) {
        return bidListRepository.findById(id);
    }

    @Transactional
    @Override
    public BidList updateBid(BidFormDto bid, Integer id) {
        BidList bidToUpdate = bidListRepository.findById(id).orElseThrow(()-> new BidListNotFoundException("Invalid BidList id :" + id));

        bidToUpdate.setAccount(bid.getAccount());
        bidToUpdate.setType(bid.getType());
        bidToUpdate.setBidQuantity(bid.getBidQuantity());
        return bidListRepository.save(bidToUpdate);
    }

    @Transactional
    @Override
    public void deleteBid(Integer id) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> new BidListNotFoundException("Invalid bid Id:" + id));
        bidListRepository.delete(bidList);
    }
}

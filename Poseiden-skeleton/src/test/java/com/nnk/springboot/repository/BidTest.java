package com.nnk.springboot.repository;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BidTest {

	@Autowired
	private BidListRepository bidListRepository;

	@Test
	public void bidListTest() {
		BidList bid = new BidList();
		bid.setAccount("Account Test");
		bid.setType("Type Test");
		bid.setBidQuantity(10d);

		// Save
		bid = bidListRepository.save(bid);
		 assertNotNull(bid.getId());
		 assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		Assert.assertFalse(bidList.isPresent());
	}
}
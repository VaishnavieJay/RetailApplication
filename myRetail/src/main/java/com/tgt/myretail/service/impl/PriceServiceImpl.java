package com.tgt.myretail.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tgt.myretail.dao.PriceDAO;
import com.tgt.myretail.model.Price;
import com.tgt.myretail.service.PriceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PriceServiceImpl implements PriceService{
	
	@Autowired
	PriceDAO priceDAO;

	@Override
	public void createPrice(List<Price> price) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePrice(Price price) {
		try {
			priceDAO.save(price);
		}catch(Exception e) {
			log.error("Error fetching product details for {}", price, e);
			//handle product not found
		}
		
	}

	@Override
	public Price findPriceByID(int id) {
		Price price = null;
		try {
			Optional<Price> optional = priceDAO.findById(id);
			if(optional.isPresent()) {
				price = optional.get();
			}
		}catch(Exception e) {
			log.error("Error fetching product details for {}", id, e);
			//handle product not found 
		}
		return price;
	}

	@Override
	public void deletePriceByID(int id) {
		// TODO Auto-generated method stub
		
	}

	
}

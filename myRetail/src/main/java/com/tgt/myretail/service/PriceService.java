package com.tgt.myretail.service;

import java.util.List;

import com.tgt.myretail.model.Price;

public interface PriceService {
	
	public void createPrice(List<Price> price);
	public void updatePrice(Price price);
	public Price findPriceByID(int id);
	public void deletePriceByID(int id);

}

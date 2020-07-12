package com.tgt.myretail.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tgt.myretail.model.Price;

public interface PriceDAO extends MongoRepository<Price, Integer>{

}

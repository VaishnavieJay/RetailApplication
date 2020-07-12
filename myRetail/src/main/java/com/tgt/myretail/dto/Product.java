package com.tgt.myretail.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {

    private int id;
	private String name;
	private CurrentPrice current_price;
		
}

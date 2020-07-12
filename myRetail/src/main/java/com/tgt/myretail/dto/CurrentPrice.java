package com.tgt.myretail.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CurrentPrice {
	
	private BigDecimal value;
	private String currency_code;
}

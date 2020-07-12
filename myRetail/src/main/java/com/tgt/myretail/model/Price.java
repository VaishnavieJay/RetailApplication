package com.tgt.myretail.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection="product_price")
public class Price {

	@Id
    private int id;
	private BigDecimal value;
	private String currency;
}

package com.tgt.myretail.orchestrator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tgt.myretail.dto.CurrentPrice;
import com.tgt.myretail.dto.Product;
import com.tgt.myretail.model.Price;
import com.tgt.myretail.service.PriceService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductOrchestrator {
	
	private final PriceService priceService;
	private final RestTemplate restTemplate;
	
	@Setter
	@Value("${product.retrieve.key.list}")
	private String retrieveKeyList;
	
	@Setter
	@Value("${product.base.url}")
	private String baseURL;
	
	@Setter
	@Value("${product.exclude.param}")
	private String excludeParam;
	
	//to be accessed from JUnit test
	@Autowired
	public ProductOrchestrator(PriceService priceService, RestTemplate restTemplate) {
		this.priceService = priceService;
		this.restTemplate=restTemplate;
	}
	
	public Product getProductDetails(int id) {
		Product product =null;
		try {
			JSONObject productJSON=restTemplate.getForObject(getURL(id), JSONObject.class);
			String[] keys = retrieveKeyList.split(",");
			Map<String, Object> objectMap = null;
			String title = null;
			for(String key:keys) {
				if(key.contains(":")){
					String[] lastKey = key.split(":");	
					title = (String)objectMap.get(lastKey[0]);
				}else {
					if(objectMap == null) {
						objectMap= (Map<String, Object>) productJSON.get(key);
					}else {
						objectMap= (Map<String, Object>) objectMap.get(key);
					}
				}
			}
			log.info("Product title from target service - {}",title);
					
			Price price = priceService.findPriceByID(id);
			
			if(price!=null) {
				product = new Product();
				product.setId(id);
				product.setName(title);
				CurrentPrice current_price = new CurrentPrice();
				current_price.setValue(price.getValue());
				current_price.setCurrency_code(price.getCurrency());
				product.setCurrent_price(current_price);
			} else {
				//handle price not found by throwing ApplicationException
			}
			
		}catch(Exception e) {
			log.error("Error fetching product details for {}", id, e);
			// rethrow it as an ApplicationException
		}
		return product;
	}
	
	private String getURL(int id) {
		return StringUtils.isNotEmpty(excludeParam)?baseURL+id+"?"+excludeParam:baseURL+id;
	}
	public void updateProductPrice(Product product,int id) {
		if(product!=null) {
			Price price = new Price();
			price.setId(id);
			price.setValue(product.getCurrent_price().getValue());
			price.setCurrency(product.getCurrent_price().getCurrency_code());
			priceService.updatePrice(price);
		}else {
			//handle exception
		}
	}
}

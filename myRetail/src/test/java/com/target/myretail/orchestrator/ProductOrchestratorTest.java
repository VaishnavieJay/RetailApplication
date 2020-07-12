package com.target.myretail.orchestrator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;

import com.tgt.myretail.dto.Product;
import com.tgt.myretail.model.Price;
import com.tgt.myretail.orchestrator.ProductOrchestrator;
import com.tgt.myretail.service.PriceService;


@RunWith(MockitoJUnitRunner.class)
public class ProductOrchestratorTest {
	
	private ProductOrchestrator productOrchestrator;
	@Mock
	private PriceService priceService;
	@Mock
	private RestTemplate restTemplate;
	
	@Before
	public void setUp() throws Exception {
		productOrchestrator = new ProductOrchestrator(priceService, restTemplate);
		productOrchestrator.setBaseURL("https://redsky.target.com/v2/pdp/tcin/");
		productOrchestrator.setExcludeParam("excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics");
		productOrchestrator.setRetrieveKeyList("product,item,product_description,title:String");
		reset(restTemplate, priceService);
	}

	@Test
	public void testException() {
		when(restTemplate.getForObject(anyString(), any())).thenThrow(new RuntimeException("Exception from unit test"));
		Product p1 = productOrchestrator.getProductDetails(8346743);
		Assert.assertNull(p1);
	}
	
	@Test
	public void testPriceNotFound() {
		mockRestTemplate();
		when(priceService.findPriceByID(anyInt())).thenReturn(null);
		Product p1 = productOrchestrator.getProductDetails(8346743);
		Assert.assertNull(p1);
	}

	@Test
	public void testProductFound() {
		mockRestTemplate();
		Price price = new Price();
		price.setCurrency("INR");
		price.setValue(BigDecimal.valueOf(15));
		when(priceService.findPriceByID(anyInt())).thenReturn(price);
		Product p1 = productOrchestrator.getProductDetails(8346743);
		Assert.assertNotNull(p1);
		Assert.assertNotNull(p1.getCurrent_price());
		MatcherAssert.assertThat(p1.getCurrent_price().getCurrency_code(), Matchers.is("INR"));
		MatcherAssert.assertThat(p1.getCurrent_price().getValue().doubleValue(), Matchers.is(15d));
	}

	private void mockRestTemplate() {
		JSONObject json = new JSONObject();
		JSONObject products = new JSONObject();
		JSONObject item = new JSONObject();
		JSONObject desc = new JSONObject();
		json.put("product", products);
		products.put("item", item);
		item.put("product_description", desc);
		desc.put("title", "product title 1");
		
		when(restTemplate.getForObject(anyString(), any())).thenReturn(json);
	}

}

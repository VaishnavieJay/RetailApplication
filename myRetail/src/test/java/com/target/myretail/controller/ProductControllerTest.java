package com.target.myretail.controller;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.tgt.myretail.controller.ProductController;
import com.tgt.myretail.dto.CurrentPrice;
import com.tgt.myretail.dto.Product;
import com.tgt.myretail.orchestrator.ProductOrchestrator;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
	
	private ProductController controller;
	@Mock
	private ProductOrchestrator productOrchestrator;
	
	@Before
	public void setup() {
		controller = new ProductController(productOrchestrator);
	}
	
	@Test
	public void testController() {
		Product p1 = new Product();
		p1.setCurrent_price(new CurrentPrice());
		p1.getCurrent_price().setCurrency_code("USD");
		Mockito.when(productOrchestrator.getProductDetails(ArgumentMatchers.anyInt())).thenReturn(p1);
		Product product = controller.getByProductId(173644);
		Assert.assertNotNull(product);
		MatcherAssert.assertThat(product.getCurrent_price().getCurrency_code(), Matchers.is("USD"));
	}
}

package com.tgt.myretail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

	//to populate dummy data from the JSON file to DB
	@Bean
	public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator() {
	    Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
	    factory.setResources(new Resource[]{new ClassPathResource("price-data.json")});
	    return factory;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

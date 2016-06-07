package io.pivotal.boot.samples.service;

import io.pivotal.boot.samples.config.DetailProperties;
import io.pivotal.boot.samples.domain.Company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vinicius Carvalho
 */
@Service
public class CompanyClient {
	private DetailProperties properties;
	private RestTemplate client;

	@Autowired
	public CompanyClient(DetailProperties properties, RestTemplate client) {
		this.properties = properties;
		this.client = client;
	}

	public Company fetchCompany(String symbol){
		ResponseEntity<Resource<Company>> resource = client
				.exchange(properties
						.getCompaniesService()+"/companies/{symbol}",
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<Resource<Company>>() {},
						symbol);
		return resource.getBody().getContent();
	}
}

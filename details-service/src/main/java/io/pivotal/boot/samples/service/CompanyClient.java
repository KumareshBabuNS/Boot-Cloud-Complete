package io.pivotal.boot.samples.service;

import java.util.HashMap;
import java.util.Map;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
	private Map<String,Company> cache = new HashMap<>();

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
		cache.put(symbol,resource.getBody().getContent());
		return resource.getBody().getContent();
	}

	private Company getFromCache(String symbol){
		return cache.get(symbol);
	}
}

package io.pivotal.boot.samples.service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.pivotal.boot.samples.config.DetailProperties;
import io.pivotal.boot.samples.domain.Company;
import io.pivotal.boot.samples.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vinicius Carvalho
 */
@Component
public class IntegrationService {


	private Logger logger = LoggerFactory.getLogger(IntegrationService.class);
	private RestTemplate client;
	private DetailProperties properties;

	@Autowired
	private LoadBalancerClient lb;

	private Map<String,Quote> quotesCache = new ConcurrentHashMap<>();
	private Map<String,Company> companiesCache = new ConcurrentHashMap();

	@Autowired
	public IntegrationService (RestTemplate client, DetailProperties properties) {
		this.client = client;
		this.properties = properties;
	}



	public Quote findQuote(String symbol){
		ServiceInstance instance = lb.choose(properties.getQuotesService());
		logger.info("Calling service at: {}", instance.getHost());
		ResponseEntity<Quote> quoteResponse = client.getForEntity("http://"+properties.getQuotesService()+"/quotes/{symbol}",Quote.class,symbol);
		quotesCache.put(symbol,quoteResponse.getBody());
		return quoteResponse.getBody();
	}


	public Company findCompany(String symbol){
		ServiceInstance instance = lb.choose(properties.getCompaniesService());
		logger.info("Calling service at: {}", instance.getHost());
		ResponseEntity<Resource<Company>> resource = client.exchange("http://"+properties.getCompaniesService()+"/companies/{symbol}", HttpMethod.GET,null,new ParameterizedTypeReference<Resource<Company>>() {},symbol);
		companiesCache.put(symbol,resource.getBody().getContent());
		return resource.getBody().getContent();
	}

	private Quote quoteFromCache(String symbol){
		return quotesCache.get(symbol);
	}

	private Company companyFromCache(String symbol){
		return companiesCache.get(symbol);
	}



}

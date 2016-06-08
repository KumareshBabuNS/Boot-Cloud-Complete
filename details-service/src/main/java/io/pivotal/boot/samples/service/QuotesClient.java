package io.pivotal.boot.samples.service;

import java.util.HashMap;
import java.util.Map;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.pivotal.boot.samples.config.DetailProperties;
import io.pivotal.boot.samples.domain.Quote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vinicius Carvalho
 */
@Service
public class QuotesClient {

	private DetailProperties properties;
	private RestTemplate client;
	private Map<String,Quote> cache = new HashMap<>();

	@Autowired
	public QuotesClient(RestTemplate client,DetailProperties properties) {
		this.client = client;
		this.properties = properties;

	}

	public Quote fetchQuote(String symbol){
		ResponseEntity<Quote> quoteResponse = client.getForEntity(properties.getQuotesService()+"/quotes/{symbol}",Quote.class,symbol);
		cache.put(symbol,quoteResponse.getBody());
		return quoteResponse.getBody();
	}

	private Quote getFromCache(String symbol){
		return cache.get(symbol);
	}
}

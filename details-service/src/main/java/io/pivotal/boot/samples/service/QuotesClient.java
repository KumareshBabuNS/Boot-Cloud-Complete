package io.pivotal.boot.samples.service;

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

	@Autowired
	public QuotesClient(RestTemplate client,DetailProperties properties) {
		this.client = client;
		this.properties = properties;

	}
	private DetailProperties properties;
	private RestTemplate client;

	public Quote fetchQuote(String symbol){
		ResponseEntity<Quote> quoteResponse = client.getForEntity(properties.getQuotesService()+"/quotes/{symbol}",Quote.class,symbol);
		return quoteResponse.getBody();
	}
}

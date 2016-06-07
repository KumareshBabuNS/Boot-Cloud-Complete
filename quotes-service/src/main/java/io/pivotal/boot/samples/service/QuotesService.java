package io.pivotal.boot.samples.service;

import io.pivotal.boot.samples.domain.Quote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vinicius Carvalho
 */
@RestController
@RequestMapping("/quotes")
public class QuotesService {

	@Value("${yahoo.endpoint}")
	private String yahooEndpoint;

	private RestTemplate client;

	@Autowired
	public QuotesService(RestTemplate client) {
		this.client = client;
	}

	@RequestMapping(value = "/{symbol}")
	public Quote fetch(@PathVariable("symbol") String symbol){
		ResponseEntity<String> response = client.getForEntity(yahooEndpoint,String.class, symbol);
		return new Quote(response.getBody());
	}
}

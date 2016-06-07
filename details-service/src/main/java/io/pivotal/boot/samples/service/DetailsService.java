package io.pivotal.boot.samples.service;

import java.util.List;

import io.pivotal.boot.samples.config.DetailProperties;
import io.pivotal.boot.samples.domain.Company;
import io.pivotal.boot.samples.domain.Details;
import io.pivotal.boot.samples.domain.Quote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vinicius Carvalho
 */
@RestController
public class DetailsService {


	private QuotesClient quotesClient;
	private CompanyClient companyClient;

	@Autowired
	public DetailsService(CompanyClient companyClient, QuotesClient quotesClient) {
		this.companyClient = companyClient;
		this.quotesClient = quotesClient;
	}


	@RequestMapping(value = "/details/{symbol}")
	public Details details(@PathVariable("symbol") String symbol){
		return new Details(companyClient.fetchCompany(symbol),quotesClient.fetchQuote(symbol));
	}

}

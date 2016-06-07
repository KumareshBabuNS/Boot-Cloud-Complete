package io.pivotal.boot.samples.repository;

import io.pivotal.boot.samples.domain.Company;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Vinicius Carvalho
 */
@RepositoryRestResource(path = "companies")
public interface CompanyRepository extends PagingAndSortingRepository<Company,String> {

	public Iterable<Company> findBySector(String sector);

}

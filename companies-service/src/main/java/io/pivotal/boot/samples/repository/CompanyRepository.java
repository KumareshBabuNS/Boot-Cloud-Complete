package io.pivotal.boot.samples.repository;

import java.util.List;

import io.pivotal.boot.samples.domain.Company;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author Vinicius Carvalho
 */
@RepositoryRestResource(path = "companies")
public interface CompanyRepository extends PagingAndSortingRepository<Company,String> {

}

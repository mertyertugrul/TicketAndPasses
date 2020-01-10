package com.mertugrul.LeisurePass.repo;

import com.mertugrul.LeisurePass.model.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}

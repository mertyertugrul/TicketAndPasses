package com.mertugrul.TicketAndPasses.repo;

import com.mertugrul.TicketAndPasses.model.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}

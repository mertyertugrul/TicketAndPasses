package com.mertugrul.TicketAndPasses.repo;

import com.mertugrul.TicketAndPasses.model.entity.Vendor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Long> {
}

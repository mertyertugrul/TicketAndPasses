package com.mertugrul.LeisurePass.repo;

import com.mertugrul.LeisurePass.model.entity.Vendor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Long> {
}

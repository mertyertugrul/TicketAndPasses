package com.mertugrul.TicketAndPasses.repo;

import com.mertugrul.TicketAndPasses.model.entity.Pass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassRepository extends CrudRepository<Pass, String > {
}

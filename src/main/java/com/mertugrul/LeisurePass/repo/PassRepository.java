package com.mertugrul.LeisurePass.repo;

import com.mertugrul.LeisurePass.model.entity.Pass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassRepository extends CrudRepository<Pass, String > {
}

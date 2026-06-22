package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository used to access BidList records.
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}

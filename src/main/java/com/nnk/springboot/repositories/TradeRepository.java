package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository used to access Trade records from the database.
 */
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}

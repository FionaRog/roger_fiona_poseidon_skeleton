package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository used to access RuleName records from the database.
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}

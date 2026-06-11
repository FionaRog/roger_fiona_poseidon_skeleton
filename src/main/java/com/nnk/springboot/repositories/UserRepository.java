package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository used to access User records from the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * Finds a User by its login username.
     *
     * @param username the login username to search for
     * @return the matching User, or null when no User exists for the given username
     */
    public User findByUsername(String username);
}

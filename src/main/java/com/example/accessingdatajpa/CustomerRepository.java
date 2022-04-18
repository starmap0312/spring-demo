package com.example.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

// create simple queries
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);
}

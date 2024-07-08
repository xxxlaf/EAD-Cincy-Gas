package com.propane_company.propane_company;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findByCustomerId(Integer customer_id);

}
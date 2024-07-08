package com.propane_company.propane_company;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    List<Customer> findByPhoneNumberAndDeliveryAddress(String phone_number, String delivery_address);
}

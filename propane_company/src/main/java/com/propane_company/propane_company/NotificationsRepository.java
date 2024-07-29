package com.propane_company.propane_company;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationsRepository extends CrudRepository<Notification, Integer> {
    List<Notification> findByPhoneNumberAndAddress(String phoneNumber, String address);
}

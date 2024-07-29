package com.propane_company.propane_company;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdministratorRepository extends CrudRepository<Administrator, Integer> {
    List<Order> findOrdersByAdminId(int adminId);
    Administrator findAdministratorByuserNameAndPassword(String userName, String password);
    Administrator findAdministratorByAdminId(int adminId);
}
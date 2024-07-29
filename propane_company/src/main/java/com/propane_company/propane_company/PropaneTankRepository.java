package com.propane_company.propane_company;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PropaneTankRepository extends CrudRepository<PropaneTank, Integer> {
    PropaneTank findByPropaneTankId(int propaneTankId);
}

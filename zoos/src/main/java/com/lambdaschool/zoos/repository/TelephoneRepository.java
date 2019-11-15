package com.lambdaschool.zoos.repository;

import com.lambdaschool.zoos.models.Telephone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TelephoneRepository extends CrudRepository<Telephone, Long> {

    List<Telephone> findAllByZoo_Zooid(long id);
}

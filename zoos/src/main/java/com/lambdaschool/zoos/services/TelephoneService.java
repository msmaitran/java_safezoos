package com.lambdaschool.zoos.services;

import com.lambdaschool.zoos.models.Telephone;

import java.util.List;

public interface TelephoneService {

    List<Telephone> findAll();

    Telephone findTelephoneById(long id);

    List<Telephone> findByZooId(long id);

    Telephone update(long phoneid, String phonenumber);

    void delete(long id);
}

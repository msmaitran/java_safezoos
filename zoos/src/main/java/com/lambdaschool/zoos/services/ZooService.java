package com.lambdaschool.zoos.services;

import com.lambdaschool.zoos.models.Zoo;
import com.lambdaschool.zoos.views.ZooCountTelephones;

import java.util.List;

public interface ZooService {

    List<Zoo> findAll();

    List<Zoo> findByNameContainingIgnoringCase(String zooname);

    Zoo findZooById(long id);

    Zoo save(Zoo zoo);

    Zoo update(Zoo zoo, long id);

    void delete(long id);

    List<ZooCountTelephones> getCountZooTelephones();

    void addZooAnimal(long zooid, long animalid);

    void deleteZooAnimal(long zooid, long animalid);

}

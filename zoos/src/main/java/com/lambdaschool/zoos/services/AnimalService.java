package com.lambdaschool.zoos.services;

import com.lambdaschool.zoos.models.Animal;
import com.lambdaschool.zoos.views.AnimalCountZoos;

import java.util.List;

public interface AnimalService {

    List<Animal> findAll();

    Animal findAnimalById(long id);

    Animal save(Animal animal);

    Animal update(long id, Animal animal);

    void delete(long id);

    List<AnimalCountZoos> getCountAnimalZoos();
}

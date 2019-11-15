package com.lambdaschool.zoos.services;

import com.lambdaschool.zoos.exceptions.ResourceFoundException;
import com.lambdaschool.zoos.exceptions.ResourceNotFoundException;
import com.lambdaschool.zoos.logging.Loggable;
import com.lambdaschool.zoos.models.Animal;
import com.lambdaschool.zoos.repository.AnimalRepository;
import com.lambdaschool.zoos.repository.ZooRepository;
import com.lambdaschool.zoos.views.AnimalCountZoos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Loggable
@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalrepos;

    @Autowired
    private ZooRepository zoorepos;

    @Override
    public List<Animal> findAll() {
        List<Animal> list = new ArrayList<>();
        animalrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Animal findAnimalById(long id) {
        return animalrepos.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Animal with id " + id + " not found!"));
    }

    @Override
    public Animal save(Animal animal) {
        Animal newAnimal = new Animal();
        newAnimal.setAnimaltype(animal.getAnimaltype());
        if (animal.getZooAnimals().size() > 0) throw new ResourceFoundException("Animal not added!");
        return animalrepos.save(animal);
    }

    @Override
    public Animal update(long id, Animal animal) {
        if (animal.getAnimaltype() == null) throw new ResourceNotFoundException("Animal type not found!");
        if (animal.getZooAnimals().size() > 0 ) throw new ResourceFoundException("Zoo Animal not found!");
        if (animalrepos.findById(id) != null) {
            animalrepos.updateAnimaltype(id, animal.getAnimaltype());
        } else throw new ResourceNotFoundException("Animal with id " + id + "does not exist");
        return findAnimalById(id);
    }

    @Override
    public void delete(long id) {
        animalrepos.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Animal with id " + id + " not found!"));
        animalrepos.deleteById(id);
    }

    @Override
    public List<AnimalCountZoos> getCountAnimalZoos() {
        return animalrepos.getListOfAnimalsZoos();
    }
}

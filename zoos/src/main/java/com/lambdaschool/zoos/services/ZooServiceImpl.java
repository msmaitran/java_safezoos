package com.lambdaschool.zoos.services;

import com.lambdaschool.zoos.exceptions.ResourceFoundException;
import com.lambdaschool.zoos.exceptions.ResourceNotFoundException;
import com.lambdaschool.zoos.logging.Loggable;
import com.lambdaschool.zoos.models.Telephone;
import com.lambdaschool.zoos.models.Zoo;
import com.lambdaschool.zoos.repository.AnimalRepository;
import com.lambdaschool.zoos.repository.ZooRepository;
import com.lambdaschool.zoos.views.ZooCountTelephones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Service(value = "zooService")
public class ZooServiceImpl implements ZooService {

    @Autowired
    private ZooRepository zoorepos;

    @Autowired
    private AnimalRepository animalrepos;



    @Override
    public List<Zoo> findAll() {
        List<Zoo> list = new ArrayList<>();
        zoorepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Zoo> findByNameContainingIgnoringCase(String zooname) {
        return zoorepos.findByZoonameContainingIgnoringCase(zooname);
    }

    @Override
    public Zoo findZooById(long id) {
        return zoorepos.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Zoo id " + id + " not found!"));
    }

    @Transactional
    @Override
    public Zoo save(Zoo zoo) {
        if (zoorepos.findByZooname(zoo.getZooname()) != null) {
            throw new ResourceFoundException(zoo.getZooname() + " is already taken!");
        }
        Zoo newZoo = new Zoo();
        newZoo.setZooname(zoo.getZooname());
        for (Telephone t : zoo.getTelephones()) {
            newZoo.getTelephones().add(new Telephone(t.getPhonetype(), t.getPhonenumber(), newZoo));
        }
        return zoorepos.save(newZoo);
    }

    @Transactional
    @Override
    public Zoo update(Zoo zoo, long id) {
        Zoo currentZoo = findZooById(id);
        if (zoo.getZooname() != null) {
            currentZoo.setZooname(zoo.getZooname());
        }
        if (zoo.getTelephones() != null) {
            for (Telephone t : zoo.getTelephones()) {
                currentZoo.getTelephones().add(new Telephone(t.getPhonetype(), t.getPhonenumber(), currentZoo));
            }
        }
        return zoorepos.save(currentZoo);
    }

    @Transactional
    @Override
    public void delete(long id) {
        zoorepos.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Zoo id " + id + "not found!"));
        zoorepos.deleteById(id);
    }

    @Override
    public List<ZooCountTelephones> getCountZooTelephones() {
        return zoorepos.getCountZooanimals();
    }

    @Override
    public void addZooAnimal(long zooid, long animalid) {
        zoorepos.findById(zooid).orElseThrow(() ->
                new ResourceNotFoundException("Zoo id " + zooid + " not found!"));
        animalrepos.findById(animalid).orElseThrow(() ->
                new ResourceNotFoundException("Animal id " + animalid + " not found!"));
        if (animalrepos.checkZooAnimalCombo(zooid, animalid).getCount() <= 0) {
            animalrepos.insertZooanimal(zooid, animalid);
        } else throw new ResourceFoundException("Zoo and Animal Combination Already Exists");
    }

    @Override
    public void deleteZooAnimal(long zooid, long animalid) {
        zoorepos.findById(zooid).orElseThrow(() ->
                new ResourceNotFoundException("Zoo id " + zooid + " not found!"));
        animalrepos.findById(animalid).orElseThrow(() ->
                new ResourceNotFoundException("Animal id " + animalid + " not found!"));
        if (animalrepos.checkZooAnimalCombo(zooid, animalid).getCount() > 0) {
            animalrepos.deleteZooAnimals(zooid, animalid);
        } else throw new ResourceFoundException("Zoo and Animal Combination Does Not Exist");
    }
}

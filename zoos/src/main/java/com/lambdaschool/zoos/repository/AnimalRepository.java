package com.lambdaschool.zoos.repository;

import com.lambdaschool.zoos.models.Animal;
import com.lambdaschool.zoos.views.AnimalCountZoos;
import com.lambdaschool.zoos.views.JustTheCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    @Query(value = "SELECT COUNT(*) AS count FROM zooanimals WHERE zooid = :zooid AND animalid = :animalid", nativeQuery = true)
    JustTheCount checkZooAnimalCombo(long zooid, long animalid);

    @Query(value = "SELECT a.animaltype, count(z.animalid) as countanimal FROM animals a JOIN zooanimals z ON z.animalid = a.animalid GROUP BY a.animalid",
            nativeQuery = true)
    List<AnimalCountZoos> getListOfAnimalsZoos();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO zooanimals(zooid, animalid) VALUES (:zooid, :animalid)", nativeQuery = true)
    void insertZooanimal(long zooid, long animalid);

    @Query(value = "UPDATE animals SET animaltype = :animaltype WHERE animalid = :animalid", nativeQuery = true)
    Animal updateAnimaltype(long animalid, String animaltype);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM zooanimals WHERE zooid = :zooid AND animalid = :animalid",
            nativeQuery = true)
    void deleteZooAnimals(long zooid, long animalid);

}

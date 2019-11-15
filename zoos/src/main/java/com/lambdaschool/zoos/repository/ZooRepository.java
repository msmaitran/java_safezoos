package com.lambdaschool.zoos.repository;

import com.lambdaschool.zoos.models.Zoo;
import com.lambdaschool.zoos.views.ZooCountTelephones;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZooRepository extends CrudRepository<Zoo, Long> {

    Zoo findByZooname(String zooname);

    List<Zoo> findByZoonameContainingIgnoringCase(String zoonmae);

    @Query(value = "SELECT z.zooname as zoonamerpt, count(t.phoneid) as countphone FROM zoos z JOIN telephones t ON z.zooid = t.zooid GROUP BY z.zooname",
            nativeQuery = true)
    List<ZooCountTelephones> getCountZooanimals();

}

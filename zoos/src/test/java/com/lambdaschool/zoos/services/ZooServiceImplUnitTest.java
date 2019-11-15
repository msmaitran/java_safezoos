package com.lambdaschool.zoos.services;

import com.lambdaschool.zoos.ZoosApplication;
import com.lambdaschool.zoos.models.Zoo;
import com.lambdaschool.zoos.models.ZooAnimals;
import com.lambdaschool.zoos.views.JustTheCount;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
@SpringBootTest(classes = ZoosApplication.class)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZooServiceImplUnitTest {

    @Autowired
    private ZooService zooService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findAll() {
        assertEquals(5, zooService.findAll().size());
    }

    @Test
    public void findByNameContainingIgnoringCase() {
        assertEquals("San Diego Zoo Test", zooService.findByNameContainingIgnoringCase("san diego").get(0).getZooname());
    }

    @Test
    public void findZooById() {
        assertEquals("San Diego Zoo Test", zooService.findZooById(3).getZooname());
    }

    @Test
    public void save() {
        ArrayList<ZooAnimals> animals = new ArrayList<>();
        String zoo6Name = "Oakland Zoo Test";
        Zoo z6 = new Zoo(zoo6Name, animals);

        Zoo addZoo = zooService.save(z6);
        assertNotNull(addZoo);
        Zoo foundZoo = zooService.findZooById(addZoo.getZooid());
        assertEquals(addZoo.getZooname(), foundZoo.getZooname());
    }

    @Test
    public void update() {
        ArrayList<ZooAnimals> animals = new ArrayList<>();
        Zoo z1 = new Zoo("Glady's Porter Zoo", animals);

        Zoo updateZoo = zooService.update(z1, 1);

        assertEquals("Glady's Porter Zoo", updateZoo.getZooname());
    }

    @Test
    public void delete() {
        zooService.delete(5);
        assertEquals(4, zooService.findAll().size());
    }

    @Test
    public void getCountZooTelephones() {
        assertEquals(3, zooService.getCountZooTelephones().get(0));
    }

    @Test
    public void addZooAnimal() {
        zooService.addZooAnimal(1, 3);
        assertEquals(3, zooService.findZooById(1).getZooanimals().size());
    }

    @Test
    public void deleteZooAnimal() {
        zooService.deleteZooAnimal(1, 1);
        assertEquals(5, zooService.findAll().size());
    }
}
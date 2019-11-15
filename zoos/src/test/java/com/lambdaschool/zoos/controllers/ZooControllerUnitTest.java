package com.lambdaschool.zoos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.zoos.models.Zoo;
import com.lambdaschool.zoos.models.ZooAnimals;
import com.lambdaschool.zoos.services.ZooService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ZooController.class)
public class ZooControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ZooService zooService;

    private List<Zoo> zooList;

    @Before
    public void setUp() throws Exception {

        zooList = new ArrayList<>();
        List<ZooAnimals> animals = new ArrayList<>();

        String zoo1Name = "Oakland Zoo Test";
        Zoo z1 = new Zoo(zoo1Name, animals);
        z1.setZooid(11);
        zooList.add(z1);

        String zoo2Name = "San Francisco Zoo Test";
        Zoo z2 = new Zoo(zoo2Name, animals);
        z2.setZooid(12);
        zooList.add(z2);

        String zoo3Name = "Los Angeles Zoo Test";
        Zoo z3 = new Zoo(zoo3Name, animals);
        z3.setZooid(13);
        zooList.add(z3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listAllZoos() throws Exception {
        String apiUrl = "/zoos/zoos";

        Mockito.when(zooService.findAll()).thenReturn(zooList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(zooList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getZooByNameLike() throws Exception {
        String apiUrl = "/zoos/zoo/namelike/oakland";

        List<Zoo> test = new ArrayList<>();
        test.add(zooList.get(0));

        Mockito.when(zooService.findByNameContainingIgnoringCase("oakland"))
                .thenReturn(test);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(test);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getZooById() throws Exception {
        String apiUrl = "/zoos/zoo/11";

        Mockito.when(zooService.findZooById(11)).thenReturn(zooList.get(1));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(zooList.get(1));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void addZoo() throws Exception {
        String apiUrl = "/zoos/zoo";

        ArrayList<ZooAnimals> animals = new ArrayList<>();
        String zoo4Name = "San Diego Zoo Test";
        Zoo z4 = new Zoo(zoo4Name, animals);
        z4.setZooid(14);

        ObjectMapper mapper = new ObjectMapper();
        String zooString = mapper.writeValueAsString(z4);

        Mockito.when(zooService.save(any(Zoo.class))).thenReturn(z4);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(zooString);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateZoo() throws Exception {
        String aipUrl = "/zoos/zoo/{zooid}";

        ArrayList<ZooAnimals> animals = new ArrayList<>();
        Zoo z1 = new Zoo("Oakland Zoo", animals);

        Mockito.when(zooService.update(z1, 11L)).thenReturn(z1);
        ObjectMapper mapper = new ObjectMapper();
        String zooString = mapper.writeValueAsString(z1);

        RequestBuilder rb = MockMvcRequestBuilders.put(aipUrl, 11L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(zooString);

        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteZooById() throws Exception {
        String apiUrl = "/zoos/zoo/{id}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "13")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void postZooAnimalById() throws Exception {
        String apiUrl = "/zoos/zoo/{zooid}/animals/{animalid}";

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl, "11", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteZooAnimalByIds() throws Exception {
        String apiUrl = "/zoos/zoo/{zooid}/animals/{animalid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "11", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}
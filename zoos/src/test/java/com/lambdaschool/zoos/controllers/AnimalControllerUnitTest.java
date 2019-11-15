package com.lambdaschool.zoos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.zoos.services.AnimalService;
import com.lambdaschool.zoos.views.JustTheCount;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AnimalController.class)
public class AnimalControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    private List<JustTheCount> countList;

    @Before
    public void setUp() throws Exception {
        countList = new ArrayList<>();

        JustTheCount c1 = new JustTheCount() {
            @Override
            public int getCount() {
                return 1;
            }
        };
        countList.add(c1);

        JustTheCount c2 = new JustTheCount() {
            @Override
            public int getCount() {
                return 2;
            }
        };
        countList.add(c2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAnimalCounts() throws Exception {
        String apiUrl = "/animals/count";

//        Mockito.when(animalService.getCountAnimalZoos()).thenReturn(countList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(countList);

        assertEquals(er, tr);
    }
}
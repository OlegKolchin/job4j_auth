package ru.job4j.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.Job4jAuthApplication;
import ru.job4j.domain.Person;

@SpringBootTest(classes = Job4jAuthApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void whenFindPerson() throws Exception {
        this.mockMvc.perform(get("/person/2"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void whenPersonNotFound() throws Exception {
        this.mockMvc.perform(get("/person/4"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenCreate() throws Exception {
        Person person = new Person();
        person.setLogin("test");
        person.setPassword("password");
        this.mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(person)))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenUpdate() throws Exception {
        Person person = new Person();
        person.setId(1);
        person.setLogin("test");
        person.setPassword("password");
        this.mockMvc.perform(put("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(person)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete() throws Exception {
        this.mockMvc.perform(delete("/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
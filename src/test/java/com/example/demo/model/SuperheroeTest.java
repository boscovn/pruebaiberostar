package com.example.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuperheroeTest {

    @Test
    void getId() {
        long id = 1L;
        Superheroe superheroe = new Superheroe(id, "");
        assertEquals(superheroe.getId(), id);
    }

    @Test
    void setId() {
        Superheroe superheroe = new Superheroe();
        long id = 1L;
        superheroe.setId(id);
        assertEquals(superheroe.getId(), id);
    }

    @Test
    void getName() {
        String name = "TestName";
        Superheroe superheroe = new Superheroe(name);
        assertEquals(superheroe.getName(), name);
    }

    @Test
    void setName() {
        Superheroe superheroe = new Superheroe();
        String name = "TestName";
        superheroe.setName("name");
        assertEquals(name, superheroe.getName());
    }
}
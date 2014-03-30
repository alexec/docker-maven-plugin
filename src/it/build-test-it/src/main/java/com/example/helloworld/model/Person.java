package com.example.helloworld.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "people")
public class Person {
    @Id
    public long id;
    public String name;
}

package com.example.helloworld;

import com.example.helloworld.model.Person;
import com.yammer.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 */
public class PeopleDAO extends AbstractDAO<Person> {
    public PeopleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Person findById(Long id) {
        return get(id);
    }
}

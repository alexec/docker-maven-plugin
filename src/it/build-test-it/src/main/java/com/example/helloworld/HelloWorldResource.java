package com.example.helloworld;

import com.example.helloworld.model.Person;
import com.google.common.base.Optional;
import com.yammer.dropwizard.hibernate.UnitOfWork;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final long defaultId;
    private final PeopleDAO peopleDAO;

    public HelloWorldResource(String template, long defaultId, PeopleDAO peopleDAO) {
        this.template = template;
        this.defaultId = defaultId;
        this.peopleDAO = peopleDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public Saying sayHello(@QueryParam("id") Optional<Long> id) {
        final Person person = peopleDAO.findById(id.or(defaultId));
        return new Saying(person.id, String.format(template, person.name));
    }
}
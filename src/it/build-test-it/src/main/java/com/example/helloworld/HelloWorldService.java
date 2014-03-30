package com.example.helloworld;

import com.example.helloworld.model.Person;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;
import com.yammer.dropwizard.migrations.MigrationsBundle;

public class HelloWorldService extends Service<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldService().run(args);
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.setName("hello-world");
        bootstrap.addBundle(new MigrationsBundle<HelloWorldConfiguration>() {

            @Override
            public DatabaseConfiguration getDatabaseConfiguration(HelloWorldConfiguration configuration) {
                return configuration.getDatabaseConfiguration();
            }
        });
        bootstrap.addBundle(hibernate);
    }

    private final HibernateBundle<HelloWorldConfiguration> hibernate = new HibernateBundle<HelloWorldConfiguration>(Person.class) {
        @Override
        public DatabaseConfiguration getDatabaseConfiguration(HelloWorldConfiguration configuration) {
            return configuration.getDatabaseConfiguration();
        }
    };

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {


        final String template = configuration.getTemplate();
        environment.addResource(new HelloWorldResource(template, configuration.getDefaultId(), new PeopleDAO(hibernate.getSessionFactory())));
        environment.addHealthCheck(new TemplateHealthCheck(template));
    }

}
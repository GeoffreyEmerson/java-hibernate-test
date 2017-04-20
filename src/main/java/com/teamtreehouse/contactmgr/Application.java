package com.teamtreehouse.contactmgr;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class Application {
//    Hold a reusable reference to a SessionFactory (since we need only one)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        // Create a StandardServiceRegistry object
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        // calling configure() loads our configuration from the default location where we have
        //  already set it up.
        // calling build() makes the actual object, which gets stored in our registry var.

        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        // no idea what all this is doing.
    }

    public static void main(String[] args) {

    }


}

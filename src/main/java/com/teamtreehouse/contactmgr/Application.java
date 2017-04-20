package com.teamtreehouse.contactmgr;

import com.teamtreehouse.contactmgr.model.Contact;
import com.teamtreehouse.contactmgr.model.Contact.ContactBuilder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

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
        Contact contact = new ContactBuilder("Geoffrey","Emerson")
                .withEmail("nope@nada.com")
                .withPhone(2125551212L)
                .build();

        save(contact);

        fetchAllContacts().stream().forEach(System.out::println);

        sessionFactory.close();
    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts() {
        Session session = sessionFactory.openSession();

        // create a Hibernate criteria object
        Criteria criteria = session.createCriteria(Contact.class);

        List<Contact> contacts = criteria.list();

        session.close();

        return contacts;
    }

    private static void save(Contact contact) {
        // open a session
        Session session = sessionFactory.openSession();

        // begin transaction
        session.beginTransaction();

        // save
        session.save(contact);

        // commit
        session.getTransaction().commit();

        // close sesh
        session.close();
    }
}

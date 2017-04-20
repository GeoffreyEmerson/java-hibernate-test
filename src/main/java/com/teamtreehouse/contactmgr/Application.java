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

        int id = save(contact);

        // display all contacts before the update
        System.out.printf("%nBefore update:%n");
        fetchAllContacts().forEach(System.out::println);

        // get persisted contact
        Contact c = findContactById(id);

        // Change something about the contact
        c.setFirstName("Buhbye");

        // Save the change back to the database
        System.out.printf("%nUpdating%n");
        update(c);
        System.out.printf("%nUpdate complete%n");

        // Display list after update
        System.out.printf("%nAfter update:%n");
        fetchAllContacts().forEach(System.out::println);

        // Delete the object
        System.out.printf("%nDeleting%n");
        delete(c);
        System.out.printf("%nDelete complete%n");

        // Display list after delete
        System.out.printf("%nAfter delete:%n");
        fetchAllContacts().forEach(System.out::println);

        // Close sessionFactory just before exiting the app. Optional, but keeps database tidy.
        sessionFactory.close();
    }

    private static Contact findContactById(int id) {
        Session session = sessionFactory.openSession();
        Contact contact = session.get(Contact.class,id);
        session.close();
        return contact;
    }

    private static void update(Contact contact) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(contact);
        session.getTransaction().commit();
        session.close();
    }

    private static void delete(Contact contact) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(contact);
        session.getTransaction().commit();
        session.close();
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

    private static int save(Contact contact) {
        // open a session
        Session session = sessionFactory.openSession();

        // begin transaction
        session.beginTransaction();

        // save
        int id = (int)session.save(contact);

        // commit
        session.getTransaction().commit();

        // close sesh
        session.close();

        return id;
    }
}

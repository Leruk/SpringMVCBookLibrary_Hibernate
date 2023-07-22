package org.leruk.spring.springmvcbooklibrary.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.leruk.spring.springmvcbooklibrary.models.Book;
import org.leruk.spring.springmvcbooklibrary.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class PersonDAO {
    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Person> index() {
        return getCurrentSession().createQuery("FROM Person", Person.class)
                .getResultList();
    }

    public Person show(int id) {
        return getCurrentSession().get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        getCurrentSession().save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Person person = getCurrentSession().get(Person.class, id);

        person.setFullName(updatedPerson.getFullName());
        person.setYearOfBirth(updatedPerson.getYearOfBirth());
    }

    @Transactional
    public void delete(int id) {
        Session session = getCurrentSession();
        session.remove(session.get(Person.class, id));
    }

    public Person getPersonByFullName(String fullName) {
        return getCurrentSession().createQuery("FROM Person WHERE fullName = :fullName", Person.class)
                .setParameter("fullName", fullName)
                .getSingleResult();
    }

    public List<Book> getBooksByPersonId(int id) {
        return getCurrentSession().createQuery("FROM Book WHERE owner.id = :id", Book.class)
                .setParameter("id", id)
                .getResultList();
    }

    protected Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }
}
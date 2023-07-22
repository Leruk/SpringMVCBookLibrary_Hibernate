package org.leruk.spring.springmvcbooklibrary.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
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
public class BookDAO {
    private final EntityManager entityManager;

    @Autowired
    public BookDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Book> index() {
        return getCurrentSession().createQuery("FROM Book", Book.class)
                .getResultList();
    }

    public Book show(int id) {
        return getCurrentSession().get(Book.class, id);
    }

    @Transactional
    public void save(Book book) {
        getCurrentSession().save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = getCurrentSession().get(Book.class, id);

        bookToBeUpdated.setTitle(updatedBook.getTitle());
        bookToBeUpdated.setAuthor(updatedBook.getAuthor());
        bookToBeUpdated.setYear(updatedBook.getYear());
    }

    @Transactional
    public void delete(int id) {
        Session session = getCurrentSession();
        session.remove(session.get(Book.class, id));
    }

    public Person getBookOwner(int id) {
        try {
            return getCurrentSession().createQuery("SELECT p FROM Book b JOIN b.owner p WHERE b.id = :id", Person.class)
                    .setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void release(int id) {
        Book book = getCurrentSession().get(Book.class, id);

        book.setOwner(null);
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Book book = getCurrentSession().get(Book.class, id);

        book.setOwner(selectedPerson);
    }

    protected Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }
}

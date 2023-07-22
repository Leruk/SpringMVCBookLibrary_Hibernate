package org.leruk.spring.springmvcbooklibrary.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Title can't be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author can't be empty")
    @Size(min = 2, max = 100, message = "Author name should be between 2 and 100 characters")
    @Column(name = "author")
    private String author;

    @Min(value=1500, message = "Year should be greater 1500")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book() {

    }

    public Book(String name, String author, int year) {
        this.title = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}

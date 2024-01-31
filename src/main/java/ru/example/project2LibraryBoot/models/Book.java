package ru.example.project2LibraryBoot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;
    @Column(name = "name")
    @NotEmpty(message = "Название книги не может быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть длиной от 2-х до 100 символов")
    private String name;
    @Column(name = "author")
    @NotEmpty(message = "Имя автора не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно быть длиной от 2-х до 100 символов")
    private String author;
    @Column(name = "publication_year")
    @Min(value = 1000, message = "Год должен быть больше 1000г")
    private int publicationYear;
    @Column(name = "passed_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passedTime;
    @Transient
    private boolean expired;

    public Book(){}
    public Book(String name, String author, int publicationYear) {
        this.name = name;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getPassedTime() {
        return passedTime;
    }

    public void setPassedTime(Date passedTime) {
        this.passedTime = passedTime;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}

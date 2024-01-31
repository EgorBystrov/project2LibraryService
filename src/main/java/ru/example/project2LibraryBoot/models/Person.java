package ru.example.project2LibraryBoot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;
    @Column(name = "full_name")
    @NotEmpty(message = "Поле ФИО не может быть пустое")
    @Size(min = 2, max = 100, message = "Длина ФИО должна быть от 2-х до 100 символов")
    private String fullName;
    @Column(name = "birth_year")
    @Min(value = 1900, message = "Год рождения должен быть больше 1900г.")
    private int birthYear;
    @OneToMany(mappedBy = "owner")
    private List<Book> bookList;

    public Person(){}

    public Person(int id, String fullName, int birthYear) {
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}

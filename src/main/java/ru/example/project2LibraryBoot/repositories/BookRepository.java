package ru.example.project2LibraryBoot.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.project2LibraryBoot.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findBooksByOwnerPersonId(int personId);
    List<Book> findByNameStartingWith(String bookName);

}
